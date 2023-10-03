import java.util.Random;

public class Producer implements Runnable{
    private final int id;
    private final Manager manager;

    private Random random = new Random();

    public Producer(int id, Manager manager) {
        this.id = id;
        this.manager = manager;

        new Thread(this).start();
    }

    @Override
    public void run() {
        while(manager.itemProduced < manager.itemTarget) {
            try {
                manager.accessProducerProduced.acquire();
                if (manager.itemProduced < manager.itemTarget){
                    manager.fullSemaphore.acquire();
                    Thread.sleep(random.nextInt(0, 500));
                    manager.access.acquire();

                    int itemIndex = manager.put();
                    System.out.println("Producer " + id + " added item " + itemIndex);
                    manager.itemProduced++;

                    manager.access.release();
                    manager.emptySemaphore.release();
                }
                manager.accessProducerProduced.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}