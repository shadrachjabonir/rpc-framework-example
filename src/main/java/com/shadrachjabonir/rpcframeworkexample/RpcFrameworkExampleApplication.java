package com.shadrachjabonir.rpcframeworkexample;

import com.shadrachjabonir.rpcframeworkexample.config.FrameworkConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
//@EnableAutoConfiguration
@SpringBootApplication
public class RpcFrameworkExampleApplication {
//    @Autowired
//    FrameworkConfig frameworkConfig;

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ApplicationContext ac = SpringApplication.run(RpcFrameworkExampleApplication.class, args);
//        System.out.println(ac.getBean(org.springframework.amqp.rabbit.connection.ConnectionFactory.class).toString());
        String[] allBeanNames = ac.getBeanDefinitionNames();
        for (String beanName : allBeanNames) {
            System.out.println(beanName);
        }
//        System.out.println(((AmqpTemplate)ac.getBean(org.springframework.amqp.rabbit.core.RabbitTemplate.class)).toString());
//        System.out.println("======================================");
//        BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor;
//        Collection<Method> found = new ArrayList<Method>();
//        for (Method m1 : DirectServiceListConfig.class.getDeclaredMethods()) {
//            System.out.println(m1.getReturnType().getName());
//            System.out.println(m1.getName());
//            for (String beanName : allBeanNames) {
//
////				System.out.println(applicationContext.getBean(beanName).getClass().getCanonicalName().toString());
////				Class c = Class.forName(applicationContext.getBean(beanName).getClass().getCanonicalName().toString());
////				System.out.println("interface : " + Arrays.asList(applicationContext.getBean(beanName).getClass().getInterfaces()));
//                for (Class clazz : Arrays.asList(applicationContext.getBean(beanName).getClass().getInterfaces())) {
////					System.out.println(clazz.getName());
////					System.out.println(m1.getReturnType().getName());
//                    if (clazz.getName().equals(m1.getReturnType().getName())) {
//                        System.out.println("simple name : " + clazz.getSimpleName());
//                        System.out.println("this is it : " + applicationContext.getBean(beanName).getClass().getCanonicalName() + " who implement : " + clazz.getName());
//                        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
//                        exporter.setService(applicationContext.getBean(beanName).getClass().newInstance());
//                        exporter.setServiceInterface(clazz);
//                    }
//                }
////				if(applicationContext.getBean(beanName).getClass().getInterfaces().equals(m1.getReturnType())){
////					applicationContext.getBean(beanName).getClass();
////				}
//
////			System.out.println(beanName);
//            }
//
////			boolean overridden = false;
////
////			for (Method m2 : found) {
////				if (m2.getName().equals(m1.getName())
////						&& Arrays.deepEquals(m1.getParameterTypes(), m2
////						.getParameterTypes())) {
////					overridden = true;
////					break;
////				}
////			}
////
////			if (!overridden)
////				found.add(m1);
//
//
//        }
//        allBeanNames = applicationContext.getBeanDefinitionNames();
//
//        for (String beanName : allBeanNames) {
//            System.out.println(applicationContext.getBean(beanName).getClass());
//
//            System.out.println(beanName);
//        }
    }
}
