package com.kaoorange.test.threadTest.thread;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created with IDEA
 * author:licheng
 * Date:2019/9/3
 * Time:下午8:49
 *
 * 这个是测试线程栅栏CountDownLatch类的使用
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CountDownLatchTest {

    private static final Logger logger = LoggerFactory.getLogger(CountDownLatchTest.class);

    @Autowired
    private ExecutorService cachedThreadPool;

    //CountDownLatch是个计数器，初始化计数器个数为10，每次执行countDown()减1，直到0，await()方法开始执行
    private final CountDownLatch countDownLatch = new CountDownLatch(10);

    @Test
    public void testCountDown() {
        logger.info("我是主线程，我开始执行");

        try {
            for(int i=1; i<=20; i++) {
                Future<Boolean> future = cachedThreadPool.submit(new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {
                        logger.info("子线程开始，我开始执行");
                        Thread.sleep(100);
                        countDownLatch.countDown();
                        logger.info("我是计数器，距离执行还剩下" + countDownLatch.getCount());
                        return Boolean.TRUE;
                    }
                });
            }
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("我是主线程，我重新开始执行");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
