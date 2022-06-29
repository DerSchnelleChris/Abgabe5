public class ThreadingExample {

	static  Integer b = 0;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		var lock = new java.util.concurrent.locks.ReentrantLock();
		lock.lock();
		lock.lock();
		System.out.println("lock");
		lock.unlock();
		lock.unlock();
		
		
		Integer a = 5;
		Object myTicket = new Object();
		synchronized(myTicket) {
			System.out.println("sync");
		}
		
		
		
		
		Thread t1 = new Thread(()->{
			int localB = 0;
			
			for (int i = 0; i < 10000; ++i)
			{
				localB += 1;
			}
			lock.lock();
			b = b + localB;
			lock.unlock();
		});
		Thread t2 = new Thread(()->{
			int localB = 0;
			
			for (int i = 0; i < 10000; ++i)
				localB += 1;
			lock.lock();
			b = b + localB;
			lock.unlock();
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		
		System.out.println(b);
		
	}

}
