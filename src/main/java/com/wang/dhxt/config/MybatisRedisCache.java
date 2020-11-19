package com.wang.dhxt.config;

import com.wang.dhxt.common.utils.ApplicationContextHolder;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MybatisRedisCache implements Cache {
    private static final Logger log = LoggerFactory.getLogger(MybatisRedisCache.class);
    private String id;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    //private static final long EXPIRE_TIME_IN_MINUTES = 30; // redis过期时间


    public MybatisRedisCache(final String id) {
        this.id = id;
    }

    private RedisTemplate<Object, Object> getRedisTemplate(){
        return ApplicationContextHolder.getBean("redisTemplate");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.boundHashOps(getId()).put(key, value);
        log.info("[结果放入到缓存中: " + key + "=" + value+" ]");

    }

    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = getRedisTemplate();
        Object value = redisTemplate.boundHashOps(getId()).get(key);
        log.info("[从缓存中获取了: " + key + "=" + value+" ]");
        return value;
    }

    @Override
    public Object removeObject(Object key) {
        RedisTemplate redisTemplate = getRedisTemplate();
        Object value = redisTemplate.boundHashOps(getId()).delete(key);
        log.info("[从缓存删除了: " + key + "=" + value+" ]");
        return value;
    }

    @Override
    public void clear() {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.delete(getId());
        log.info("清空缓存!!!");
    }

    @Override
    public int getSize() {
        RedisTemplate redisTemplate = getRedisTemplate();
        Long size = redisTemplate.boundHashOps(getId()).size();
        return size == null ? 0 : size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}
