package com.kaoorange.test.util.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Created with IDEA
 * author:licheng
 * Date:2019/9/5
 * Time:上午11:01
 **/
public class OrangePoolFactory extends BasePooledObjectFactory<OrangePoolObj> {

    private static GenericObjectPool<OrangePoolObj> pool;

    static {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        // 关于连接池的配置-GenericObjectPool参数解析 : https://www.jianshu.com/p/5cb54a5bfc3a
        // 数量控制参数
        poolConfig.setMaxIdle(10); //链接池中最大空闲的连接数,默认也为8
        poolConfig.setMaxTotal(20); //链接池中最大连接数,默认为8
        poolConfig.setMinIdle(5); //连接池中最少空闲的连接数,默认为0
        // 超时参数
        poolConfig.setMaxWaitMillis(2000); //当连接池资源耗尽时，等待时间，超出则抛异常，默认为-1即永不超时
        poolConfig.setBlockWhenExhausted(true); //当这个值为true的时候，maxWaitMillis参数才能生效。为false的时候，当连接池没资源，则立马抛异常。默认为true
        // test参数
        /*testOnCreate
        默认false，create的时候检测是有有效，如果无效则从连接池中移除，并尝试获取继续获取
        testOnBorrow
        默认false，borrow的时候检测是有有效，如果无效则从连接池中移除，并尝试获取继续获取
        testOnReturn
        默认false，return的时候检测是有有效，如果无效则从连接池中移除，并尝试获取继续获取
        testWhileIdle
        默认false，在evictor线程里头，当evictionPolicy.evict方法返回false时，而且testWhileIdle为true的时候则检测是否有效，如果无效则移除*/
        // 检测参数
        poolConfig.setTimeBetweenEvictionRunsMillis(60000); //空闲链接检测线程检测的周期，毫秒数。如果为负值，表示不运行检测线程。默认为-1.
        poolConfig.setLifo(false);
        pool = new GenericObjectPool<OrangePoolObj>(new OrangePoolFactory(), poolConfig);
    }

    @Override
    public OrangePoolObj create() throws Exception {
        return new OrangePoolObj();
    }

    @Override
    public PooledObject<OrangePoolObj> wrap(OrangePoolObj test) {
        return new DefaultPooledObject(test);
    }

    public static OrangePoolObj borrowObject() throws Exception{
        OrangePoolObj test = (OrangePoolObj) pool.borrowObject(1000);
        return test;
    }

    public static void returnObject(OrangePoolObj test) throws Exception{
        pool.returnObject(test);
    }

    public static void close() throws Exception{
        pool.close();
    }

    public static void clear() throws Exception{
        pool.clear();
    }

    public static void invalidateObject(OrangePoolObj test) throws Exception {
        pool.invalidateObject(test);
    }

    public static GenericObjectPool<OrangePoolObj> getPool() {
        return pool;
    }
}
