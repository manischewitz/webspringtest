package db;

import org.junit.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
@Configuration
@EnableCaching
public class RedisConfig {

	
		@Bean
		public CacheManager cacheManager(RedisTemplate redisTemplate) {
		return new RedisCacheManager(redisTemplate);
		}
		@Bean
		public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.afterPropertiesSet();
		return jedisConnectionFactory;
		}
		@Bean
		public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisCF) {
			RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
			redisTemplate.setConnectionFactory(redisCF);
			redisTemplate.afterPropertiesSet();
			return redisTemplate;
		}
	

	
	
	@Test
	public void redisTest(){
		RedisConnection connection = this.redisConnectionFactory().getConnection();
		connection.set("foo".getBytes(), "bar".getBytes());
		byte[] fooBytes = connection.get("foo".getBytes());
		String bar = new String(fooBytes);
		System.out.println(bar);
		assertEquals("bar",bar);
		
		/*try{
			Jedis jedis = new Jedis("localhost");
			System.out.println(jedis.ping());
			jedis.set("foo", "bar");
			String str = jedis.get("foo");
			System.out.println(str);
			jedis.close();
			assertEquals("bar",str);
		}catch(Exception e){
			e.printStackTrace();
		}*/
	}
	
}
