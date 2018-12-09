package com.cpda.common.api;

import com.cpda.common.utils.SerializeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis 服务组件
 * 
 */
@Service("redisService")
public class RedisService {

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public void setHash(String key,String hashKey,Object value){
		redisTemplate.opsForHash().put(key,hashKey,value);
	}

	public Map<String,Object> getHash(String key,String hashKey){
		return (HashMap)redisTemplate.opsForHash().get(key,hashKey);
	}

	/**
	 * 存储key
	 */
	public boolean set(final String key, final Object value) {

		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
            	RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				System.out.println(value.toString().getBytes());
				System.out.println(value.toString().getBytes());
				return connection.set(serializer.serialize(key), SerializeUtils.serialize(value));
            }
	    });
		return result;
	}

	public void setV(final String key, final String value){
		System.out.println(key+","+value);
		stringRedisTemplate.opsForValue().set(key,value);
	}

	public Object getV(String key){
		return stringRedisTemplate.opsForValue().get(key);
	}

	/**
	 * 获取Object类型key
	 */
	public Object getObject(final String key){
		Object result = redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
            	RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value =  connection.get(serializer.serialize(key));
                return  SerializeUtils.deserialize(value);
            }
	    });
		return result;
	}
	
	/**
	 * 获取String类型key
	 */
	public String get(final String key){
		String result = redisTemplate.execute(new RedisCallback<String>() {
            
            public String doInRedis(RedisConnection connection) throws DataAccessException {
            	RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value =  connection.get(serializer.serialize(key));
                String rs = serializer.deserialize(value);
                return rs;
            }
	    });
		return result;
	}

	/**
	 * 设置过期时间
	 * @param key
	 * @param expire 毫秒
	 * @return
	 */
	public boolean expire(final String key, long expire) {
		return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}

	
	public String lpop(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
            	RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] res =  connection.lPop(serializer.serialize(key));
                return serializer.deserialize(res);
            }
	    });
		return result;
	}
	
	public void delete(final String key){
		redisTemplate.delete(key);
	}
	
}