package com.jam2in.app.config;

import com.navercorp.arcus.spring.cache.ArcusCacheConfiguration;
import com.navercorp.arcus.spring.cache.ArcusCacheManager;
import net.spy.memcached.ArcusClient;
import net.spy.memcached.ArcusClientPool;
import net.spy.memcached.ConnectionFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class ArcusJavaClientConfiguration {
  @Value("${arcus.zk-address}")
  private String zkAddress;
  @Value("${arcus.service-code}")
  private String serviceCode;

  @Bean
  public ArcusClientPool arcusClientPool() {
	return ArcusClient.createArcusClientPool(zkAddress, serviceCode, new ConnectionFactoryBuilder(), 10);
  }

  @Bean
  public ArcusCacheManager arcusCacheManager() {
    return new ArcusCacheManager(
        arcusClientPool(),
        /* default cache configuration (missing cache) */
        defaultCacheConfig(),
        /* a map of cache configuration (key=cache name, value=cache configuration) */
        initialCacheConfig()
    );
  }

  @Bean
  public ArcusCacheConfiguration defaultCacheConfig() {
    ArcusCacheConfiguration defaultCacheConfig = new ArcusCacheConfiguration();
    defaultCacheConfig.setPrefix("DEFAULT");
    defaultCacheConfig.setExpireSeconds(60);
    defaultCacheConfig.setTimeoutMilliSeconds(800);
    return defaultCacheConfig;
  }

  @Bean
  public Map<String, ArcusCacheConfiguration> initialCacheConfig() {
    Map<String, ArcusCacheConfiguration> initialCacheConfig = new HashMap<>();
    initialCacheConfig.put("testCache", testCacheConfig());
    return initialCacheConfig;
  }

  @Bean
  public ArcusCacheConfiguration testCacheConfig() {
    ArcusCacheConfiguration cacheConfig = new ArcusCacheConfiguration();
    cacheConfig.setServiceId("TEST-");
    cacheConfig.setPrefix("PRODUCT");
    cacheConfig.setExpireSeconds(60);
    cacheConfig.setTimeoutMilliSeconds(800);
    return cacheConfig;
  }
}
