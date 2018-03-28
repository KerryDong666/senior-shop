package com.kerry.senior.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author CP_dongchuan
 * @date 2018/3/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;
    @Test
    public void get() throws Exception {

        System.out.println(redisUtil.get("myname"));
    }

    @Test
    public void get1() throws Exception {
    }

    @Test
    public void set() throws Exception {
    }

    @Test
    public void set1() throws Exception {
    }

    @Test
    public void set2() throws Exception {
    }

    @Test
    public void setStringValue() throws Exception {
    }

}