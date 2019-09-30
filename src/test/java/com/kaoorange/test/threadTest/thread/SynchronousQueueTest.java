package com.kaoorange.test.threadTest.thread;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;

/**
 * Created with IDEA
 * author:licheng
 * Date:2019/9/3
 * Time:下午9:20
 *
 * 这个是测试同步队列，用于线程之间通讯
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SynchronousQueueTest {

    private static final Logger logger = LoggerFactory.getLogger(SynchronousQueueTest.class);

    @Autowired
    private ExecutorService cachedThreadPool;

    private final CountDownLatch countDownLatch = new CountDownLatch(2);


    @Test
    public void testSQ() {
        final BlockingQueue<String> synchronousQueue = new SynchronousQueue();
        cachedThreadPool.submit(new Producer(synchronousQueue, countDownLatch));
        cachedThreadPool.submit(new Consumer(synchronousQueue, countDownLatch));

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    class Producer implements Runnable {

        private BlockingQueue<String> synchronousQueue;
        private CountDownLatch countDownLatch;

        public Producer(BlockingQueue<String> synchronousQueue, CountDownLatch countDownLatch) {
            this.synchronousQueue = synchronousQueue;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            logger.info("我是生产者，我开始往同步队列中put一个字符串 111");
            try {
                synchronousQueue.put("111");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("我是生产者，我往同步队列中put字符串 111 完成");

            logger.info("我是生产者，我开始往同步队列中put一个字符串 222");
            try {
                synchronousQueue.put("222");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("我是生产者，我往同步队列中put字符串 222 完成");
            countDownLatch.countDown();
        }
    }

    class Consumer implements Runnable {

        private BlockingQueue<String> synchronousQueue;
        private CountDownLatch countDownLatch;

        public Consumer(BlockingQueue<String> synchronousQueue, CountDownLatch countDownLatch) {
            this.synchronousQueue = synchronousQueue;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            logger.info("我是消费者，我启动了，我啥也不干先sleep5秒");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                logger.info("我是消费者，我开始第一次take" + synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info("我是消费者，我启动了，我再sleep5秒");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                logger.info("我是消费者，我开始第二次take" + synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            countDownLatch.countDown();
        }
    }
}
