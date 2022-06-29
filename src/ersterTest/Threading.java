package ersterTest;

public class Threading extends Thread {
           static int cnt = 0;

           public static void main(String[] args)
           {
                Thread t1 = new Threading();
                Thread t2 = new Threading();
                t1.start();
                t2.start();
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

