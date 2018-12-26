package com.cpda.config;

import com.cpda.common.utils.ProtostuffSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.*;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * redis 配置
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(CacheProperties.class)
public class RedisConfig extends CachingConfigurerSupport {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		return om;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

		RedisTemplate<String, Object> template = new RedisTemplate<>();

		RedisSerializer<String> redisSerializer = new StringRedisSerializer(); //key序列化

		template.setConnectionFactory(factory);

		//key序列化方式
		template.setKeySerializer(redisSerializer);
		//value序列化
		//template.setDefaultSerializer(new ProtostuffSerializer());
		template.afterPropertiesSet();
		return template;
	}

	/*@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}*/

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		return new RedisCacheManager(
				RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
				// 默认策略，未配置的 key 会使用这个
				this.getRedisCacheConfigurationWithTtl(30),
				// 指定 key 策略
				this.getRedisCacheConfigurationMap()
		);
	}

	@Bean
	public KeyGenerator simpleKeyGenerator() {
		return (o, method, objects) -> {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(o.getClass().getSimpleName());
			stringBuilder.append(".");
			stringBuilder.append(method.getName());
			stringBuilder.append("[");
			for (Object obj : objects) {
				stringBuilder.append(obj.toString());
			}
			stringBuilder.append("]");

			return stringBuilder.toString();
		};
	}

	/**
	 * 指定不同key的缓存策略
	 * @return
	 */
	private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
		redisCacheConfigurationMap.put("string:menu-tree", this.getRedisCacheConfigurationWithTtl(600));
		redisCacheConfigurationMap.put("UserInfoListAnother", this.getRedisCacheConfigurationWithTtl(18000));

		return redisCacheConfigurationMap;
	}

	/**
	 * 默认key的缓存策略
	 * @param minutes
	 * @return
	 */
	private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer minutes) {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
				RedisSerializationContext
						.SerializationPair
						.fromSerializer(jackson2JsonRedisSerializer)
		).entryTtl(Duration.ofMinutes(minutes));

		return redisCacheConfiguration;
	}
	/*@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return (target, method, objects) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getName());
			sb.append("::" + method.getName() + ":");
			for (Object obj : objects) {
				sb.append(obj.toString());
			}
			return sb.toString();
		};
	}*/
}
