package com.study.onlinestore.service.impl;

import com.study.applicationcontext.entity.BeanDefinition;
import com.study.applicationcontext.service.BeanFactoryPostProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PropertyPlaceholder implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(List<BeanDefinition> beanDefinitions) {
        Properties properties = getAppProperties();

        for (BeanDefinition beanDefinition : beanDefinitions) {
            Map<String, String> dependencies = beanDefinition.getDependencies();

            for (String fieldName : dependencies.keySet()) {
                String value = dependencies.get(fieldName);
                if (value.startsWith("${") && value.endsWith("}")) {
                    String propertyName = value.substring(2, value.length() - 1);
                    String propertyValue = properties.getProperty(propertyName);
                    if (propertyValue != null) {
                        dependencies.put(fieldName, propertyValue);
                    }
                }
            }

        }
    }

    private static Properties getAppProperties() {
        String propertiesLocation = System.getProperty("properties.location");
        if (propertiesLocation != null) {
            File file = new File(propertiesLocation, "application.properties");
            Properties properties = new Properties();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                properties.load(reader);
            } catch (Exception e) {
                throw new RuntimeException("File with properties for application couldn't be read at: " + file);
            }
            return properties;
        } else {
            throw new RuntimeException("Properties for application was not specified.");
        }
    }
}
