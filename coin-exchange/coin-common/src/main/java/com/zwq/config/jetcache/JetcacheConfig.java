package com.zwq.config.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Acer
 * @Date 2021/08/21 16:31
 * @Version 1.0
 */
@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.zwq.service.impl")
public class JetcacheConfig {

}
