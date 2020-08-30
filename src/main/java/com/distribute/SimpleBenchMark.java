package com.distribute;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 传统的通过java并发包实现的消息队列
 */

public class SimpleBenchMark {

  private CountDownLatch latch =new CountDownLatch(10000);

  private Random random = new Random();

  public void runBenchmark() throws InterruptedException {
    long beginTime = System.currentTimeMillis();
    ExecutorService executorService= Executors.newFixedThreadPool(400);
    for (int i = 0; i < 10000 ; i++) {
      executorService.execute(new Handler());
    }
    latch.await();
    System.out.println("Consume Time:"
        + (System.currentTimeMillis()-beginTime)+"ms");
    executorService.shutdown();
  }

  public static void main(String[] args) throws InterruptedException {
    SimpleBenchMark benchMark=new SimpleBenchMark();
    benchMark.runBenchmark();

  }
  class Handler implements Runnable{

    @Override
    public void run() {
      BlockingQueue<String> resultQueue= new ArrayBlockingQueue<>(1);
      try{
        resultQueue.poll(random.nextInt(10)+100, TimeUnit.MILLISECONDS);
      }
      catch (InterruptedException e){
        e.printStackTrace();
      }
      latch.countDown();
    }
  }
}
