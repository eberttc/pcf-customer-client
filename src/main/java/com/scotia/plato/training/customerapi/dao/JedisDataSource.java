package com.scotia.plato.training.customerapi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Repository
public class JedisDataSource {

	@Autowired
	private JedisPool shardedJedisPool;

	public Jedis getRedisClient() {
		Jedis shardedJedis = null;
		shardedJedis = shardedJedisPool.getResource();
		return shardedJedis;
	}

	public void returnResource(Jedis shardedJedis) {
		shardedJedis.disconnect();
		shardedJedisPool.returnBrokenResource(shardedJedis);
	}

	public void returnResource(Jedis shardedJedis, boolean broken) {
		shardedJedis.shutdown();
	}
}
