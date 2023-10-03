import java.util.Random;

public class Consumer implements Runnable {
    private final int id;
    private final Manager manager;

    private Random random = new Random();

    public Consumer(int id, Manager manager) {
        this.id = id;
        this.manager = manager;

        new Thread(this).start();
    }

    @Override
    public void run() {
        while(manager.itemConsumed < manager.itemTarget) {
            try {
                manager.accessConsumerConsumed.acquire();
                if (manager.itemConsumed < manager.itemTarget) {
                    manager.emptySemaphore.acquire();
                    Thread.sleep(random.nextInt(0, 500));
                    manager.access.acquire();

                    String item = manager.take();
                    System.out.println("Consumer " + id + " took " + item);
                    manager.itemConsumed++;

                    manager.access.release();
                    manager.fullSemaphore.release();
                }
                manager.accessConsumerConsumed.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}