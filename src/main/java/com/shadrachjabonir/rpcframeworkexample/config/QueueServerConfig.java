package com.shadrachjabonir.rpcframeworkexample.config;

import org.reflections.Reflections;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Component
public class QueueServerConfig implements BeanDefinitionRegistryPostProcessor {

    private String serviceNameFormater(String className) {
        return className.toLowerCase().replace("service", "");
    }

    private Map<String, AmqpInvokerServiceExporter> getListInvoker() {
        Map<String, AmqpInvokerServiceExporter> result = new HashMap<>();
        Reflections reflections = new Reflections("com.shadrachjabonir");
        Set<Class<?>> allClasses =
                reflections.getTypesAnnotatedWith(org.springframework.stereotype.Service.class);
        List<Class> listOfClass = new ArrayList<>(allClasses);
        AmqpInvokerServiceExporter exporter;
        System.out.println("queueeeeeeeeeee");
        for (Method m1 : QueueServiceListConfig.class.getDeclaredMethods()) {
            System.out.println(m1.getName());
            for (Class clazz : listOfClass) {
                System.out.println("clazz : " + clazz.getCanonicalName());
                for (Class clazzz : Arrays.asList(clazz.getInterfaces())) {
                    System.out.println("clazzz : " + clazzz.getCanonicalName());
                    System.out.println(clazzz.getName() + " ===== " + m1.getReturnType().getName());
                    if (clazzz.getName().equals(m1.getReturnType().getName())) {
                        System.out.println("this is it : " + clazz.getClass().getCanonicalName() + " who implement : " + clazzz.getName());
                        exporter = new AmqpInvokerServiceExporter();
                        try {
                            exporter.setService(clazz.newInstance());
                            exporter.setServiceInterface(clazzz);
//                            exporter.setAmqpTemplate(new RabbitTemplate());
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        result.put(serviceNameFormater(clazzz.getSimpleName()), exporter);
                    }
                }
            }
        }

        return result;
    }

    private DirectMessageListenerContainer getListener(CachingConnectionFactory connectionFactory,
                                                       AmqpInvokerServiceExporter amqpInvokerServiceExporter,
                                                       Queue queue) {
        DirectMessageListenerContainer container = new DirectMessageListenerContainer(connectionFactory);
        container.setMessageListener(amqpInvokerServiceExporter);
        container.setQueueNames(queue.getName());
        return container;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        GenericBeanDefinition beanQueue;
        GenericBeanDefinition beanListener;
        GenericBeanDefinition beanAdmin;
        GenericBeanDefinition beanFactory;

        CachingConnectionFactory connectionFactory;
        Queue queue;

        connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("10.11.17.34");
        connectionFactory.setUsername("mp2_user");
        connectionFactory.setPassword("mp2_password");
        connectionFactory.setPort(5672);
        connectionFactory.setChannelCacheSize(25);

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange("");

        beanFactory = new GenericBeanDefinition();
        beanFactory.setBeanClass(CachingConnectionFactory.class);
        beanFactory.getPropertyValues()
                .add("host", connectionFactory.getHost())
                .add("username", connectionFactory.getUsername())
                .add("password", "mp2_password")
                .add("port", connectionFactory.getPort())
                .add("channelCacheSize", connectionFactory.getChannelCacheSize());
        beanFactory.setAutowireCandidate(true);
        beanFactory.setLazyInit(false);

        beanAdmin = new GenericBeanDefinition();
        beanAdmin.setBeanClass(RabbitAdmin.class);
        beanAdmin.getConstructorArgumentValues()
                .addGenericArgumentValue(new RabbitTemplate(connectionFactory));
        registry.registerBeanDefinition("amqpAdmin", beanAdmin);

        Map<String, AmqpInvokerServiceExporter> map = getListInvoker();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            queue = new Queue("remote." + pair.getKey().toString());
            beanQueue = new GenericBeanDefinition();
            beanQueue.setBeanClass(Queue.class);
            beanQueue.getConstructorArgumentValues()
                    .addGenericArgumentValue(queue.getName(), String.class.getName());
            registry.registerBeanDefinition(queue.getName(), beanQueue);

            beanListener = new GenericBeanDefinition();
            AmqpInvokerServiceExporter exporter = (AmqpInvokerServiceExporter) pair.getValue();
            exporter.setAmqpTemplate(rabbitTemplate);

            beanListener.setBeanClass(SimpleMessageListenerContainer.class);
            beanListener.getConstructorArgumentValues()
                    .addIndexedArgumentValue(0, connectionFactory);
            beanListener.getPropertyValues()
//                    .add("connectionFactory", connectionFactory)
                    .add("messageListener", exporter)
                    .add("queueNames", queue.getName());

            registry.registerBeanDefinition(pair.getKey().toString() + "Listener", beanListener);
            it.remove();
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}
