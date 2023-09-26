package com.jam2in.app.config;

import net.spy.memcached.ArcusClient;
import net.spy.memcached.ConnectionFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArcusConfiguration {
  @Value("${arcus.zk-address}")
  private String zkAddress;
  @Value("${arcus.service-code}")
  private String serviceCode;

  @Bean
  public ArcusClient arcusClient() {
	return ArcusClient.createArcusClient(zkAddress, serviceCode, new ConnectionFactoryBuilder());
  }
}
