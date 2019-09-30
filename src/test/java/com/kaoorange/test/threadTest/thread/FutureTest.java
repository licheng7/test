package com.kaoorange.test.threadTest.thread;

import com.kaoorange.test.util.http.HttpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

/**
 * Created with IDEA
 * author:licheng
 * Date:2019/9/3
 * Time:上午11:29
 *
 * 这个测试类除妖用在测试Future的cancel方法
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class FutureTest {

    private static final Logger logger = LoggerFactory.getLogger(FutureTest.class);

    @Autowired
    private ExecutorService cachedThreadPool;

    @Test
    public void testCancel() {
        this.cancel();
    }

    private void cancel() {
        logger.info("主线程开始，我先做了A");

        for(int i=1; i<=1000; i++) {
            cachedThreadPool.submit(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    //logger.info("子线程开始，我开始做B");
                    for(int i=1; i<=1000; i++) {
                        Thread.sleep(200);
                        //HttpUtil.doGet("https://www.baidu.com");
                        //logger.info("i=" + i);
                    }
                    return Boolean.TRUE;
                }
            });
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Future<Boolean> future = cachedThreadPool.submit(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                logger.info("子线程开始，我开始做B");
                for(int i=1; i<=1000; i++) {
                    //Thread.sleep(100);
                    HttpUtil.doGet("https://www.baidu.com");
                    logger.info("i=" + i);
                }
                return Boolean.TRUE;
            }
        });

        for(int i=1; i<=500; i++) {
            cachedThreadPool.submit(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    //logger.info("子线程开始，我开始做B");
                    for(int i=1; i<=100; i++) {
                        Thread.sleep(200);
                        //HttpUtil.doGet("https://www.baidu.com");
                        //logger.info("i=" + i);
                    }
                    return Boolean.TRUE;
                }
            });
        }

        try {
            logger.info("我拿到了future返回的结果" + String.valueOf(future.get(100, TimeUnit.MILLISECONDS)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            logger.info("取消子线程执行" + future.cancel(true));
        }

        logger.info("主线程结束");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
