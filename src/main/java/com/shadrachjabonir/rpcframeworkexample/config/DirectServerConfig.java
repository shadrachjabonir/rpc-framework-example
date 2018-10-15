package com.shadrachjabonir.rpcframeworkexample.config;

import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

import java.lang.reflect.Method;
import java.util.*;

public class DirectServerConfig implements BeanDefinitionRegistryPostProcessor {

    private String serviceNameFormater(String className) {
        return className.toLowerCase().replace("service", "");
    }

    private Map<String, HttpInvokerServiceExporter> getListInvoker() {
        Map<String, HttpInvokerServiceExporter> result = new HashMap<>();
        Reflections reflections = new Reflections("com.shadrachjabonir");
        Set<Class<?>> allClasses =
                reflections.getTypesAnnotatedWith(org.springframework.stereotype.Service.class);
        List<Class> listOfClass = new ArrayList<>(allClasses);
//        String[] allBeanNames = context.getBeanDefinitionNames();
        HttpInvokerServiceExporter exporter;
        for (Method m1 : DirectServiceListConfig.class.getDeclaredMethods()) {
            System.out.println(m1.getReturnType().getName());
            System.out.println(m1.getName());

            for (Class clazz : listOfClass) {
                System.out.println(clazz.getName() + " iniiii");
                for (Class clazzz : Arrays.asList(clazz.getInterfaces())) {
                    System.out.println(clazzz.getName());
                    System.out.println(m1.getReturnType().getName());
                    if (clazzz.getName().equals(m1.getReturnType().getName())) {
                        System.out.println("this is it : " + clazz.getClass().getCanonicalName() + " who implement : " + clazzz.getName());
                        exporter = new HttpInvokerServiceExporter();
                        try {
                            exporter.setService(clazz.newInstance());
                            exporter.setServiceInterface(clazzz);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        result.put("/" + serviceNameFormater(clazzz.getSimpleName()), exporter);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        GenericBeanDefinition bd;
        Map<String, HttpInvokerServiceExporter> map = getListInvoker();

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            bd = new GenericBeanDefinition();
            bd.setBeanClass(HttpInvokerServiceExporter.class);
            HttpInvokerServiceExporter exporter = (HttpInvokerServiceExporter) pair.getValue();
            bd.getPropertyValues()
                    .add("service", exporter.getService())
                    .add("serviceInterface", exporter.getServiceInterface());
            registry.registerBeanDefinition(pair.getKey().toString(), bd);
            it.remove(); // avoids a ConcurrentModificationException
        }

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
