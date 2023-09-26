package com.jam2in.app.config.common;

import com.jam2in.arcus.app.common.config.ArcusConfiguration;
import com.jam2in.arcus.app.common.property.ArcusPropertyJsonFetcher;
import com.jam2in.arcus.app.common.property.ArcusPropertyUpdateScheduler;
import com.jam2in.arcus.app.common.property.ArcusPropertyUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import(ArcusConfiguration.class)
@PropertySource(value = "classpath:arcus-properties.yml", factory = YamlPropertySourceFactory.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
@RequiredArgsConstructor
public class AppCommonConfiguration {
	private final ArcusPropertyJsonFetcher propertyJsonFetcher;

	/**
	 * Aspect Beans To Specify Cache Item
	 */
//	@Bean
//	public ArcusAnnotationAspect arcusAnnotationAspect() {
//		return new ArcusAnnotationAspect(arcusConfiguration);
//	}
//
//	@Bean
//	public ArcusJsonAspect arcusCacheJsonAspect() {
//		return new ArcusJsonAspect(arcusConfiguration);
//	}

	/**
	 * Beans For Automatically Update Property
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer
	propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	@Bean
	public ArcusPropertyUpdater arcusPropertyUpdater() {
		return new ArcusPropertyUpdater(propertyJsonFetcher);
	}
	@Bean
	public ArcusPropertyUpdateScheduler arcusPropertyUpdateScheduler() {
		return new ArcusPropertyUpdateScheduler(arcusPropertyUpdater());
	}
}
