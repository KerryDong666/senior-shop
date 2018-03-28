package com.kerry.senior.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author CP_dongchuan
 * @date 2018/3/27
 */
@Configuration
@PropertySource("classpath:application.properties")
public class JedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.timeout}")
    private long timeout;

    //public static void main(String[] args) {
    //    JedisPoolConfig config = new JedisPoolConfig();
    //    config.setMaxIdle(maxIdle);
    //}

    @Bean
    public JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(timeout);
        config.setMaxTotal(maxActive);
        config.setMinIdle(minIdle);
        return config;
    }

    @Bean
    public JedisPool getJedisPool(JedisPoolConfig config){
        System.out.println("JedisPoolConfig = " + config);
        JedisPool pool = new JedisPool(config,host,port,(int) timeout,password);
        return pool;
    }


}
