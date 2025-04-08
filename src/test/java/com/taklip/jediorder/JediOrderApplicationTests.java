// package com.taklip.jediorder;

// import java.util.concurrent.BrokenBarrierException;
// import java.util.concurrent.CyclicBarrier;

// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;

// import com.taklip.jediorder.bean.Id;
// import com.taklip.jediorder.service.IdService;

// @RunWith(SpringRunner.class)
// @SpringBootTest
// public class JediOrderApplicationTests {
// 	@Autowired
// 	private IdService idService;

// 	@Test
// 	public void contextLoads() {
// 		final int number = 100;

// 		Thread[] treads = new Thread[number];

// 		boolean flag = false;

// 		CyclicBarrier barrier = new CyclicBarrier(number, new BarrierRun(flag, number));

// 		System.out.println("Start generate id.");

// 		for (int i = 0; i < number; ++i) {
// 			System.out.println("Generator ID[" + i + "] started.");

// 			treads[i] = new Thread(new IDGenerator(i, barrier));

// 			treads[i].start();
// 		}
// 	}

// 	public class IDGenerator implements Runnable {
// 		private Integer genId;
// 		private final CyclicBarrier barrier;

// 		IDGenerator(Integer genId, CyclicBarrier barrier) {
// 			this.genId = genId;
// 			this.barrier = barrier;
// 		}

// 		@Override
// 		public void run() {
// 			try {
// 				barrier.await();

// 				doGenerator();

// 				barrier.await();
// 			} catch (InterruptedException e) {
// 				e.printStackTrace();
// 			} catch (BrokenBarrierException e) {
// 				e.printStackTrace();
// 			}
// 		}

// 		void doGenerator() {
// 			long id = idService.generateId();

// 			System.out.println("Generator ID[" + genId + "] create UNIQUE ID:" + id);

// 			Id idObj = idService.explainId(id);

// 			System.out.println("Generator ID[" + genId + "] explain UNIQUE ID[" + id + "], result:" + idObj);
// 		}
// 	}

// 	public class BarrierRun implements Runnable {
// 		boolean flag;
// 		int number;

// 		BarrierRun(boolean flag, int number) {
// 			this.flag = flag;
// 			this.number = number;
// 		}

// 		@Override
// 		public void run() {
// 			if (flag) {
// 				System.out.println("All Generator [" + number + "] completed.");
// 			}
// 			else {
// 				System.out.println("All Generator [" + number + "] initialized.");
// 				flag = true;
// 			}
// 		}
// 	}
// }