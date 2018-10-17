package com.scotia.plato.training.customerapi.config;


import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.net.UnknownHostException;

@Configuration
public class RedisDataConfig {
	public static Logger LOGGER = LoggerFactory.getLogger(RedisDataConfig.class);

	@Primary
	@Bean
	public RedisConnectionFactory redisConnectionFactory() throws UnknownHostException {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(20);
		poolConfig.setMinIdle(2);
		poolConfig.setMaxIdle(5);

		JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);

		try {
			String vcap_services = System.getenv("VCAP_SERVICES");
			if (vcap_services != null && vcap_services.length() > 0) {
				JsonRootNode root = new JdomParser().parse(vcap_services);
				JsonNode rediscloudNode = root.getNode("rediscloud");
				JsonNode credentials = rediscloudNode.getNode(0).getNode("credentials");


				factory.setHostName(credentials.getStringValue("hostname"));
				factory.setUsePool(true);
				factory.setPassword(credentials.getStringValue("password"));
				factory.setPort(Integer.parseInt(credentials.getStringValue("port")));
			}
		} catch (InvalidSyntaxException ex) {

		}
		return factory;
	}

	@Bean(name = "redisUserTemplate")
	public RedisTemplate<String, String> redisTemplateUser(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}

}
