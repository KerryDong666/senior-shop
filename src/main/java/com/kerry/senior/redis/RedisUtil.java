package com.kerry.senior.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis工具类
 *
 * @author CP_dongchuan
 * @date 2018/3/27
 */
@Component
public class RedisUtil {

    private final JedisPool jedisPool;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RedisUtil(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    /**
     * 通过key获取value值对象
     *
     * @param key       k
     * @param valueType 需要获取的对象字节码类型
     * @return value对象
     */
    public <T> T get(String key, Class<T> valueType) {
        Jedis jedis = getJedis();
        try {
            if (StringUtils.isBlank(key)) {
                return null;
            }
            String retStr = jedis.get(key);
            if (valueType == String.class) {
                return (T) retStr;
            }
            return JSONObject.parseObject(retStr, valueType);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 通过key获取value字符串
     */
    public String get(String key) {
        return get(key, String.class);
    }

    /**
     * redis数据库的set操作:
     * 如果保存的对象不是String,则先转换为jsonString再保存,而不是直接保存对象,
     * 减少序列化和反序列化的性能开销
     */
    public <T> boolean set(String key, T value, int expireSeconds) {
        if (StringUtils.isBlank(key) || value == null) {
            return false;
        }
        Jedis jedis = jedisPool.getResource();
        //非String类型转换为jsonString
        String valueString = value instanceof String ? String.valueOf(value) : JSON.toJSONString(value);
        try {
            if (expireSeconds <= 0) { //永久存储
                jedis.set(key, valueString);
            } else { //设置过期时间
                jedis.setex(key, expireSeconds, valueString);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            returnResource(jedis);
        }
    }

    public <T> boolean set(String key, T value) {
        return set(key, value, 0);
    }

    public boolean set(String key, String value) {
        return set(key, value, 0);
    }

    public boolean setStringValue(String key, String value, int expireSeconds) {
        return set(key, value, expireSeconds);
    }

    /**
     * 获取jedis信息
     */
    private Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            logger.error("获取jedis客户端失败, errmsg = {}", e.getMessage());
        }
        return jedis;
    }

    /**
     * 减少值
     */
    public Long decr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.decr(key);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 判断key是否存在
     */
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            return  jedis.exists(key);
        }finally {
            returnResource(jedis);
        }
    }

    /**
     * 关闭jedis连接
     */
    private void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
