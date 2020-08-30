package com.distribute;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import kilim.Mailbox;
import kilim.Pausable;
import kilim.Task;

public class SimpleBenchMark2 {
    private CountDownLatch latch = new CountDownLatch(10000);
    private Random random =new Random();

    public static void main(String[] args) throws Exception{
      SimpleBenchMark2 benchMark2=new SimpleBenchMark2();
      benchMark2.runBenchMark();
    }
    public void runBenchMark() throws Exception{
      long beginTime = System.currentTimeMillis();
      for (int i = 0; i < 10000; i++) {
        new Handler().start();
      }
      latch.await();
      System.out.println("Consume Time:"
      +(System.currentTimeMillis()-beginTime)+"ms");
      System.exit(0);
    }
  class Handler extends Task {
    public void execute() throws Pausable,Exception{
      Mailbox<String> resultMailbox =new Mailbox<>(1);
      resultMailbox.get(random.nextInt(10)+100);
      latch.countDown();
    }
  }
}
