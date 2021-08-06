import java.util.Random;

public class ApplePicker extends Thread {

    private static final int MIN_RANDOM_DELAY_MS = 900;
    private static final int MAX_RANDOM_DELAY_MS = 3500;
    private static final double MIN_RANDOM_FACTOR = 0.8;
    private static final double MAX_RANDOM_FACTOR = 1.5;
    private static final int VALUE_FOR_COUNTING_NUMBER_APPLES = 10;

    private final int id;
    private final AppleStorage appleStorage;

    public ApplePicker(int id, AppleStorage appleStorage) {
        this.id = id;
        this.appleStorage = appleStorage;
    }

    @Override
    public void run() {
        collectApples();
        printSalary();
    }

    /**
     * Данный метод выводит в консоль информацию и зарплате конкретного сборщика.
     */
    private void printSalary() {
        double salary = appleStorage.getSalary(id);
        System.out.printf("Я сборщик яблок %s, получил зарплату: %s%n", id, salary);
    }

    /**
     * Данный метод симулирует сборку яблок сборщиками и отправку данных
     * (количество яблок и затраченное на это время) в общую структуру.
     */
    private void collectApples() {
        Random random = new Random();
        int harvestTimeMillisecond =
                random.nextInt(MAX_RANDOM_DELAY_MS - MIN_RANDOM_DELAY_MS) + MIN_RANDOM_DELAY_MS;
        try {
            Thread.sleep(harvestTimeMillisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double randomFactor = MIN_RANDOM_FACTOR + (MAX_RANDOM_FACTOR - MIN_RANDOM_FACTOR) * random.nextDouble();
        double harvestTimeSecond = (double) harvestTimeMillisecond / 1000;
        int pickedApplesCount = (int) (harvestTimeSecond * VALUE_FOR_COUNTING_NUMBER_APPLES * Math.PI * randomFactor);
        appleStorage.addRecord(id, harvestTimeMillisecond, pickedApplesCount);
    }
}
