import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Manager {
    public static int storageSize;
    public static Semaphore access;
    public static Semaphore fullSemaphore;
    public static Semaphore emptySemaphore;
    public static Semaphore accessProducerProduced;
    public static Semaphore accessConsumerConsumed;
    public static volatile int itemTarget;
    public static volatile int itemProduced;
    public static volatile int itemConsumed;

    private int lastIndex = 0;
    public static List<String> storage;

    public Manager(int storageSize, int itemTarget) {
        this.storageSize = storageSize;
        this.itemTarget = itemTarget;
        this.itemConsumed = 0;
        this.itemProduced = 0;
        access = new Semaphore(1);
        fullSemaphore = new Semaphore(storageSize);
        emptySemaphore = new Semaphore(0);
        accessProducerProduced = new Semaphore(1);
        accessConsumerConsumed = new Semaphore(1);
        storage = new ArrayList<>();
    }

    public int put(){
        storage.add("item " + lastIndex);
        lastIndex++;
        return lastIndex - 1;
    }

    public String take(){
        return storage.remove(0);
    }
}