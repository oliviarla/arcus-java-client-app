package com.jam2in.app.config.common;

import com.jam2in.arcus.app.common.property.ArcusPropertyJsonFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class ArcusPropertyJsonFetcherImpl implements ArcusPropertyJsonFetcher {

	private final ResourceLoader resourceLoader;
	@Override
	public String getProperty() {
		Resource resource = resourceLoader.getResource("file:/Users/oliviarla/Work/arcus-java-client-app/src/main/resources/arcus-properties.json");
		if (!resource.exists()) {
			return "";
		}
		try {
			return resource.getContentAsString(StandardCharsets.UTF_8);
		} catch (IOException e) {
			return "";
		}
	}
}
