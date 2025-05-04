package com.organizo.organizobackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.*;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.time.Duration;
/**
 * Configuração de conexão e template Redis.
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {


    // 1) ObjectMapper com suporte a Java 8 date/time
    @Bean
    public ObjectMapper jacksonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    // 2) Factory de conexão (host/porta via properties ou variáveis de ambiente)
    @Bean
    public LettuceConnectionFactory redisConnectionFactory(
            @Value("${spring.redis.host:localhost}") String host,
            @Value("${spring.redis.port:6379}") int port) {
        return new LettuceConnectionFactory(host, port);
    }

    // 3) Template que usa o nosso ObjectMapper para JSON
    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            LettuceConnectionFactory cf,
            ObjectMapper mapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(cf);

        // Serializadores de chave (String) e valor (JSON com suporte a LocalDateTime)
        StringRedisSerializer keySer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer valSer =
                new GenericJackson2JsonRedisSerializer(mapper);

        template.setKeySerializer(keySer);
        template.setValueSerializer(valSer);
        template.setHashKeySerializer(keySer);
        template.setHashValueSerializer(valSer);
        template.afterPropertiesSet();
        return template;
    }

    // 4) CacheManager que também usa JSON configurado
    @Bean
    public CacheManager cacheManager(
            LettuceConnectionFactory cf,
            ObjectMapper mapper) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer(mapper))
                );

        return RedisCacheManager.builder(cf)
                .cacheDefaults(config)
                .build();
    }

    // 5) Na falha do cache, não quebra a aplicação
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler();
    }
}