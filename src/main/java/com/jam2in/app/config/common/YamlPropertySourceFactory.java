package com.jam2in.app.config.common;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.env.PropertySource;

import java.io.IOException;
import java.util.Properties;

/**
 * Custom PropertySourceFactory Getting Property Resource From yaml File
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
		YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
		factory.setResources(encodedResource.getResource());

		Properties properties = factory.getObject();

		return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
	}
}