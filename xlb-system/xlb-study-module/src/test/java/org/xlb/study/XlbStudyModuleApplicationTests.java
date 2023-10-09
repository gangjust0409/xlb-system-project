package org.xlb.study;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorkerBuilderStep1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.xlb.common.utils.LogInfo;
import org.xlb.study.config.LinearityTable;
import org.xlb.study.config.LinearityTableByLinkedList;
import org.xlb.study.thead.DaemonThead;
import org.xlb.study.thead.InterruptThread;
import org.xlb.study.thead.SimpleThead;
import org.xlb.study.thead.synchronize.SocietyHeiNiu;
import org.xlb.study.thead.synchronize.SocietyHuangNiu;
import org.xlb.study.thead.waitAndNotify.TaskQueue;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

/**
 * Java8 ：
 * 		内置函数式接口
 *
 */
@ComponentScan(basePackages = "org.xlb")
@SpringBootTest
class XlbStudyModuleApplicationTests {

	@Qualifier("java8Test")
	@Autowired
	private org.xlb.study.java8.func_interface.Test java8Test;

	//排序算法
	@Test
	void testSortAlgorithm(){
		/*List<Integer> nums = List.of(1, 10, 7,65,2,9, 52);
		//排序后的值
		System.out.println("排序前：");
		for (int i = 0; i < nums.size(); i++) {
			System.out.print(nums.get(i) + "\t");
		}
		List<Integer> numbers = SortAlgorithm.bubblingSort(nums);
		System.out.println("\n排序后：");
		for (int i = 0; i < numbers.size(); i++) {
			System.out.printf("%s\t", numbers.get(i));
		}*/
	}


	//TODO 数据结构

	//线性表 ---> 链表
	@Test
	void testLinkedListShowLinearityTable(){
		LinearityTableByLinkedList linearityTableByLinkedList = new LinearityTableByLinkedList();
		linearityTableByLinkedList.add(1);
		linearityTableByLinkedList.add(5);
		linearityTableByLinkedList.add(8);
		linearityTableByLinkedList.add(10);
		linearityTableByLinkedList.add(500);
		linearityTableByLinkedList.add(600);
		linearityTableByLinkedList.add(12);

		for (Integer i = 0; i < linearityTableByLinkedList.size(); i++) {
			System.out.print(linearityTableByLinkedList.get(i) + "\t");
		}

	}

	@Autowired
	private ZeebeClient zeebeClient;

	@Test
	void testCamunda(){
		JobWorkerBuilderStep1 jobWorkerBuilderStep1 = zeebeClient.newWorker();
		JobWorkerBuilderStep1.JobWorkerBuilderStep2 payment = jobWorkerBuilderStep1.jobType("payment");
		System.err.println(payment);

	}

	//虚拟线程
	@Test
	void testVirtualThead(){

		/*Queue<String> tasks = new LinkedList<>();

		//方式一：直接创建运行
		Thread.startVirtualThread(() -> {
			for (int i = 0; i < 1000000; i++) {
				tasks.add(String.format("任务%s添加完成！", i+1));
			}
		});
		//方式二：创建之后，手动运行
		Thread.ofVirtual().unstarted(() -> {
			for (String task : tasks) {
				System.out.print("\n" + task + "\t");
			}
		});
		//方式三：通过 TheadFactory 工厂创建
		ThreadFactory threadFactory = Thread.ofVirtual().factory();
		threadFactory.newThread(() -> {
			System.out.println(Thread.currentThread().getName());
		}).start();*/

	}

	//todowait和notiry
	@Test
	void testWaitAndNotiry(){

		TaskQueue taskQueue = new TaskQueue();

		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				taskQueue.addTask(String.format("任务%s添加完成！", i + 1));
			}
		}).start();
		//第二个线程读取
		new Thread(() -> {
			while (true) {
				String task = null;
				try {
					task = taskQueue.getTask();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				System.out.println("任务：" + task);
			}
		}).start();
	}

	//可重入锁  JVM允许同一个线程重复获取同一个锁，这种能被同一个线程反复获取的锁，就叫做可重入锁。
	@Test
	void testReentrantLock() {
		ReentrantClazz reentrantClazz = new ReentrantClazz();
		reentrantClazz.add();
		System.out.println(reentrantClazz.count);
	}

	class ReentrantClazz{

		public Integer count = 0;

		//添加的方法
		public synchronized void add(){
			count += 5;
			update();
		}

		//修改的方法
		private synchronized void update(){
			count += 10;
		}
	}

	//线性表  --> 数组
	@Test
	void testLinearityTable(){
		LinearityTable linearityTable = new LinearityTable(10);
		linearityTable.add("张三");
		linearityTable.add("王五");
		linearityTable.add("李四");
		linearityTable.add("张小三");
		linearityTable.add("朱元璋");
		linearityTable.add("哈哈强");
		System.out.println(linearityTable.size());

		for (Integer i = 0; i < linearityTable.size(); i++) {
			System.out.println(linearityTable.get(i).toString());
		}
	}

	/**
	 * 同步线程：加锁可以实现线程同步
	 */
	@Test
	void testSynchronizeThead() throws InterruptedException {
		Thread huangNiuSocietyThread = new SocietyHuangNiu();
		Thread heiNiuSocietyThread = new SocietyHeiNiu();
		huangNiuSocietyThread.start();
		heiNiuSocietyThread.start();
		heiNiuSocietyThread.join();
		heiNiuSocietyThread.join();
	}

	@Test
	void testDaemonThead(){
		Thread daemonThead = new DaemonThead();
		daemonThead.setDaemon(true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		daemonThead.start();
	}

	/**
	 * main线程通过调用t.interrupt()从而通知t线程中断，而此时t线程正位于hello.join()的等待中，此方法会立刻结束等待并抛出InterruptedException。由于我们在t线程中捕获了InterruptedException，因此，就可以准备结束该线程。在t线程结束前，对hello线程也进行了interrupt()调用通知其中断。如果去掉这一行代码，可以发现hello线程仍然会继续运行，且JVM不会退出。
	 *
	 * 另一个常用的中断线程的方法是设置标志位。我们通常会用一个running标志位来标识线程是否应该继续运行，在外部线程中，通过把HelloThread.running置为false，就可以让线程结束：
	 */
	@Test
	void testThreadRunning() throws InterruptedException {
		InterruptRunning thread = new InterruptRunning();
		thread.start();
		Thread.sleep(1000);
		thread.running = false;
	}

	/**
	 * 终止线程方法二
	 */
	class InterruptRunning extends Thread{

		/**
		 * 是一个线程间共享的变量。线程间共享变量需要使用volatile关键字标记，确保每个线程都能读取到更新后的变量值。
		 */
		public volatile boolean running = true;

		@Override
		public void run() {
			int n = 0;
			while (running) {
				n++;
				System.out.println("线程：" + Thread.currentThread().getName() + "执行了：" + n +"次。");
			}
			System.out.println("线程：" + Thread.currentThread().getName() + "end");
		}
	}

	/**
	 * 终止线程方法一
	 * 如果线程处于等待状态，例如，t.join()会让main线程进入等待状态，此时，如果对main线程调用interrupt()，join()方法会立刻抛出InterruptedException，因此，目标线程只要捕获到join()方法抛出的InterruptedException，就说明有其他线程对其调用了interrupt()方法，通常情况下该线程应该立刻结束运行。
	 */
	@Test
	void testInterruptThread2() throws InterruptedException {
		Runnable runnable = () -> {
			InterruptThread interruptThread = new InterruptThread();
			interruptThread.start();
			try{
				interruptThread.join(); //等待 InterruptThread 执行完成
				System.out.println("jjjjj");
			} catch (InterruptedException e) {
				System.out.println(e.getMessage() + "\tInterruptedException");
			}
			interruptThread.interrupt();
		};
		Thread thread = new Thread(runnable);
		thread.start();
		Thread.sleep(1000);
		thread.interrupt();
		thread.join();
		System.out.println("end");
	}

	@Test
	void testInterruptThead() throws InterruptedException {
		System.out.println("start testInterruptThead");
		Thread interruptThread = new InterruptThread();
		interruptThread.start();
		Thread.sleep(1000);
		interruptThread.interrupt(); // 停止线程
		interruptThread.join(); // 等待该线程执行完
		System.out.println("end testInterruptThead");
	}

	@Test
	void testThead() throws InterruptedException {
		System.out.println("test thead start...");
		Thread simpleThead = new SimpleThead();
		simpleThead.start();
		simpleThead.join();//等待线程
		System.out.println("test thead end...");
	}

	@Test
	void contextLoads() {
		java8Test.accept("账上", name -> {
			System.out.printf("哈哈哈哈，%s", name);
		});

		Double money = java8Test.count(() -> 55.5 * 568.23);
		java8Test.accept("李四", (name) -> {
			System.out.printf("%s的钱：%s", name, money);
		});

		Boolean flag = java8Test.test("abcdfdsfds", (str) -> {
			return str.contains("s");
		});
		java8Test.accept("断言型接口", str -> System.out.printf("%s，%s", str, flag));

		Integer compare = java8Test.compare("15233", price -> {
			return Integer.parseInt(price);
		});

		BigDecimal bigDecimal = java8Test.tstFunc(128.23, BigDecimal::new, price -> Double.valueOf(price * 23.56));
		java8Test.accept("多个函数连接", str -> System.out.printf("%s\t%s", str, bigDecimal));

	}

	//java10
	@Test
	void testJava10(){
		/*var str  = "hhh";
		System.err.println(str); //有利于 jvm的G1 停顿时间*/
	}

	@Test
	void testJava11() {
		Integer[] arr = {18, 20, 5, 95, 85, 65, 75, 85};
		Arrays.sort(arr);
		// stream 新增方法
		Stream<Integer> stream = Stream.of(arr);
//		takeWhile  当当前条件不满足不会往下执行了
//		stream.takeWhile(num -> num < 60).collect(Collectors.toList()).forEach(System.out::print);
		// 18 20 5
		//dropWhile 只要前面条件不满足之后，后面的直接输出了，不进行条件比对了
//		stream.dropWhile(num -> num < 66).collect(Collectors.toList()).forEach(System.out::print);
		/*User user = null;
		User newUser = Optional.of(user).orElse(new User());
		System.err.println(newUser);*/
		/*HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/test/1005")).build();
		HttpResponse.BodyHandler<String> responseHander = HttpResponse.BodyHandlers.ofString();
		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, responseHander);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println(response.body());*/


	}

	class User{
		private Long id;
		private String name;
	}

	//jdk12
	@Test
	void testJava12(){
		String s = "str";
		/*var number = switch (s) {
			case "str" -> 1;
			case "num" -> 2;
			default -> 3;
		};
		System.err.println(number);*/
	}

	@Test
	void testJava13() {
		String str = "ss";
		/*Class clazz = switch (str) {
			case "ss" -> {
				System.out.println("ss");
				yield String.class;
			}
			case "num" -> {
				yield Number.class;
			}
			default -> {
				yield Object.class;
			}
		};
		System.out.println(clazz);*/
	}

	@Test
	void testJava14(){
		Object user = new User();
		//instanceof 如果是一个类型，那么直接赋值给 user1
		/*if (user instanceof User user1) {
			System.err.println(user1);
		}
		Bean bean = new Bean("张三", 18);
		System.err.println(bean.age);
		System.err.println(bean);*/

	}

	@Test
	void testJava18(){

	}

/*
	private String format(Object o) {
		return switch (o) {
			case Integer i -> String.format("哈哈哈：%s", i);
			case String str -> String.format("好好干%s", str);
			default -> String.format("无");
		};
	}
*/

	// record 类似 lombok
	/*record Bean(String name, Integer age){

	}*/

}
