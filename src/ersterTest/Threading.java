package ersterTest;

public class Threading extends Thread {
           static int cnt = 0;
           static int b = 0;

           public static void main(String[] args)
           {
               var lock = new java.util.concurrent.locks.ReentrantLock();
                Thread t1 = new Threading();
                Thread t2 = new Threading();
                Thread t3 = new Thread(()-> {
                    int localB = 0;
                    for (int i = 0; i < 1000; ++i) {
                        localB += 1;
                    }
                    lock.lock();
                    b = b + localB;
                    lock.unlock();
                });
                Thread t4 = new Thread(() -> {
                    int localB = 0;
                    for (int i=0; i<1000; ++i) {
                        localB += 1;
                    }
                    lock.lock();
                    b= b+localB;
                    lock.unlock();
                });
                //t1.start();
                //t2.start();
                System.out.println("Lock und Unlock");
                t3.start();
                t4.start();
                System.out.println(b);
              }

           public void run()
           {
                 while (true) {
                       System.out.println(cnt++);
                       if (cnt > 150) {
                           break;
                       }
                     }
               }
         }

