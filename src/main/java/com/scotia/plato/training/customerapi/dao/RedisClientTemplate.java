package com.scotia.plato.training.customerapi.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class RedisClientTemplate {

	private final Logger logger = Logger.getLogger(RedisClientTemplate.class);


	@Autowired
	private JedisDataSource jedisDataSource;

	public String set(String key, String value) {
		String result = null;

		Jedis Jedis = jedisDataSource.getRedisClient();
		if (Jedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = Jedis.set(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			jedisDataSource.returnResource(Jedis, broken);
		}

		return result;
	}

	/**
	 * 获取单个值
	 *
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String result = null;
		Jedis Jedis = jedisDataSource.getRedisClient();
		if (Jedis == null) {
			return result;
		}

		boolean broken = false;
		try {
			result = Jedis.get(key);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			jedisDataSource.returnResource(Jedis, broken);
		}

		return result;
	}

	public Boolean exists(String key) {
		Boolean result = false;
		Jedis Jedis = jedisDataSource.getRedisClient();
		if (Jedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = Jedis.exists(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			jedisDataSource.returnResource(Jedis, broken);
		}

		return result;
	}
}
