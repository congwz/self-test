package com.viverselftest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class Redis extends CachingConfigurerSupport {

    private Logger logger = LoggerFactory.getLogger(Redis.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public JedisPool redisPoolFactory(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxTotal(200);
        jedisPoolConfig.setMaxIdle(50);
        jedisPoolConfig.setMinIdle(8);  //设置最小空闲数
        jedisPoolConfig.setMaxWaitMillis(10000);

        //在获取连接（/jedis实例）的时候检查有效性, 默认false
        jedisPoolConfig.setTestOnBorrow(true);
        //连接回收到连接池时，自动测试连接（/jedis实例）是否可用（ping()），如果连接不可用，则不会放回连接池
        jedisPoolConfig.setTestOnReturn(true);
        //自动检测空闲连接是否可用
        jedisPoolConfig.setTestWhileIdle(true);
        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(10);
        /*逐出连接的最小空闲时间 默认1800000毫秒(30分钟) （即一个对象至少停留在空闲状态的最短时间）
        扫描并驱逐；setMinEvictableIdleTimeMillis只有在timeBetweenEvictionRunsMillis大于0时才有意义*/
        jedisPoolConfig.setMinEvictableIdleTimeMillis(60000); //1min

        JedisPool jedisPool = null;
        if ("testredis".equals(password)) {
            jedisPool = new JedisPool(jedisPoolConfig, host, port, 10000);
        } else {
            jedisPool = new JedisPool(jedisPoolConfig, host, port, 10000, password);
        }

        logger.info("JedisPool注入成功！！！");
        return jedisPool;

    }
}
