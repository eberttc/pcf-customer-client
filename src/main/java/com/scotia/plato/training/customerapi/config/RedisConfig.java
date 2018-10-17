package com.scotia.plato.training.customerapi.config;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

@Configuration
public class RedisConfig {

	@Bean(name = "jedis.pool")
	public JedisPool jedisPool() {
		try {
			String vcap_services = System.getenv("VCAP_SERVICES");
			if (vcap_services != null && vcap_services.length() > 0) {
				// parsing rediscloud credentials
				JsonRootNode root = new JdomParser().parse(vcap_services);
				JsonNode rediscloudNode = root.getNode("rediscloud");
				JsonNode credentials = rediscloudNode.getNode(0).getNode("credentials");

				return new JedisPool(new JedisPoolConfig(),
						credentials.getStringValue("hostname"),
						Integer.parseInt(credentials.getStringValue("port")),
						Protocol.DEFAULT_TIMEOUT,
						credentials.getStringValue("password"));
			}
		} catch (InvalidSyntaxException ex) {

		}
		return null;
	}
}
