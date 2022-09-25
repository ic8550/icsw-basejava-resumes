package dev.icsw.resumes;

public class MainConcurrency {
    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = createThread(lock1, lock2,
                10,
                "Thread1:    Holding lock1, waiting for lock2...",
                "Thread1:    Holding lock1 and lock2, executing normally...");
        Thread thread2 = createThread(lock2, lock1,
                10,
                "Thread2:    Holding lock2, waiting for lock1...",
                "Thread2:    Holding lock2 and lock1, executing normally...");
        thread1.start();
        thread2.start();
    }

    public static Thread createThread(Object lock1,
                                      Object lock2,
                                      int pause,
                                      String messageWaiting,
                                      String messageExecuting) {
        return new Thread(() -> {
            synchronized (lock1) {
                System.out.println(messageWaiting);
                try {
                    Thread.sleep(pause);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println(messageExecuting);
                }
            }
        });
    }
}
