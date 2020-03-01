package com.sout.carcre.integration.redis;

import com.sout.carcre.controller.HomeController;

import com.sout.carcre.integration.handler.SessionUtil;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/3/1 22:58
 */
@Data
public class RedisLock{
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 重试时间
     */
    private static final int DEFAULT_ACQUIRY_RETRY_MILLIS = 100;
    /**
     * 锁的后缀
     */
    private static final String LOCK_SUFFIX = "_redis_lock";
    /**
     * 锁的key
     */
    private String lockKey;
    /**
     * 锁超时时间，防止线程在入锁以后，防止阻塞后面的线程无法获取锁
     */
    private int expireMsecs = 60 * 1000;
    /**
     * 线程获取锁的等待时间
     */
    private int timeoutMsecs = 10 * 1000;
    /**
     * 是否锁定标志
     */
    private volatile boolean locked = false;

    /**
     * 构造器
     *
     * @param isAddUser 是否关联用户
     * @param lockKey   锁的key
     */
    public RedisLock(RedisTemplate<String, Object> redisTemplate, String lockKey, Boolean isAddUser,String userId) {
        this.redisTemplate = redisTemplate;
        if (isAddUser) {
            // 获取当前登录用户
            String systemUser =userId;
            System.out.println("RedisLock:"+systemUser);
            if (null == systemUser || null == systemUser) {
                this.lockKey = lockKey + LOCK_SUFFIX;
            } else {
                int user =Integer.parseInt(systemUser);
                this.lockKey = user + "_" + lockKey + LOCK_SUFFIX;
            }
        } else {
            this.lockKey = lockKey + LOCK_SUFFIX;
        }
    }

    /**
     * 构造器
     *
     * @param redisTemplate
     * @param lockKey       锁的key
     * @param timeoutMsecs  获取锁的超时时间
     */
    public RedisLock(RedisTemplate<String, Object> redisTemplate, String lockKey, Boolean isAddUser, int timeoutMsecs,String userId) {
        this(redisTemplate, lockKey, isAddUser,userId);
        this.timeoutMsecs = timeoutMsecs;
    }

    /**
     * 构造器
     *
     * @param redisTemplate
     * @param lockKey       锁的key
     * @param timeoutMsecs  获取锁的超时时间
     * @param expireMsecs   锁的有效期
     */
    public RedisLock(RedisTemplate<String, Object> redisTemplate, String lockKey, Boolean isAddUser, int timeoutMsecs, int expireMsecs,String userId) {
        this(redisTemplate, lockKey, isAddUser, timeoutMsecs,userId);
        this.expireMsecs = expireMsecs;
    }

    public String getLockKey() {
        return lockKey;
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    private String get(final String key) {
        Object obj = redisTemplate.opsForValue().get(key);
        return obj != null ? obj.toString() : null;
    }

    /**
     * 存入值
     *
     * @param key
     * @param value
     * @return
     */
    private boolean setNX(final String key, final String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 设置值并返回旧值
     *
     * @param key
     * @param value
     * @return
     */
    private String getSet(final String key, final String value) {
        Object obj = redisTemplate.opsForValue().getAndSet(key, value);
        return obj != null ? (String) obj : null;
    }

    /**
     * 获取锁，获取失败的话过100ms重试，总超时时间 10 * 1000 ms
     *
     * @return 获取锁成功返回ture，超时返回false
     * @throws InterruptedException
     */
    public synchronized boolean lockRetry() {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires); // 锁到期时间
            if (this.setNX(lockKey, expiresStr)) {
                locked = true;
                return true;
            }
            // redis里key的时间
            String currentValue = this.get(lockKey);
            // 判断锁是否已经过期，过期则重新设置并获取
            if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis()) {
                // 设置锁并返回旧值
                String oldValue = this.getSet(lockKey, expiresStr);
                // 比较锁的时间，如果不一致则可能是其他锁已经修改了值并获取
                if (oldValue != null && oldValue.equals(currentValue)) {
                    locked = true;
                    return true;
                }
            }
            timeout -= DEFAULT_ACQUIRY_RETRY_MILLIS;
            // 延时
            try {
                Thread.sleep(DEFAULT_ACQUIRY_RETRY_MILLIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取锁，不重试，只获取一次，获取不到就返回失败
     *
     * @return 获取锁成功返回ture，失败返回false
     * @throws InterruptedException
     */
    public synchronized boolean lockNoRetry() {
        long expires = System.currentTimeMillis() + expireMsecs + 1;
        String expiresStr = String.valueOf(expires); // 锁到期时间
        if (this.setNX(lockKey, expiresStr)) {
            locked = true;
            return true;
        }
        // redis里key的时间
        String currentValue = this.get(lockKey);
        // 判断锁是否已经过期，过期则重新设置并获取
        if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            // 设置锁并返回旧值
            String oldValue = this.getSet(lockKey, expiresStr);
            // 比较锁的时间，如果不一致则可能是其他锁已经修改了值并获取
            if (oldValue != null && oldValue.equals(currentValue)) {
                locked = true;
                return true;
            }
        }
        return false;
    }

    /**
     * 释放获取到的锁
     */
    public synchronized void unlock() {
        if (locked) {
            redisTemplate.delete(lockKey);
            locked = false;
        }
    }
}
