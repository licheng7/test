package com.kaoorange.test.threadTest.pool;

import com.kaoorange.test.util.pool.OrangePoolFactory;
import com.kaoorange.test.util.pool.OrangePoolObj;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;

/**
 * Created with IDEA
 * author:licheng
 * Date:2019/9/5
 * Time:下午3:33
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPoolFactoryTest {

    private static final Logger logger = LoggerFactory.getLogger(TestPoolFactoryTest.class);

    @Autowired
    private ExecutorService cachedThreadPool;

    @Test
    public void testPool() {
        try {
            for(int i=1; i<=500; i++) {
                cachedThreadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OrangePoolObj test = OrangePoolFactory.borrowObject();
                            test.sayHi();
                            logger.info(String.valueOf(OrangePoolFactory.getPool().getNumActive()));
                            Thread.sleep(200);
                            OrangePoolFactory.returnObject(test);
                        } catch (Exception e) {
                            logger.info("完犊子");
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
