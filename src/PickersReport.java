import java.util.Map;

public class PickersReport extends Thread {

    private final StringBuilder stringBuilder = new StringBuilder();
    private AppleStorage storage;

    public PickersReport() {
    }

    public void setAppleStorage(AppleStorage storage) {
        this.storage = storage;
    }

    /**
     * Метод расчитывает скорость сбора яблок и добавляет данные общий в отчёт.
     */
    private void produceReport() {
        Map<Integer, AppleStorage.TimeAndCount> appleStorageMap = storage.getHarvestDataMap();
        for (Map.Entry<Integer, AppleStorage.TimeAndCount> entry : appleStorageMap.entrySet()) {
            double speed = entry.getValue().getCount() / entry.getValue().getTime();
            stringBuilder.append("; ");
            stringBuilder.append("id:");
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" = ");
            stringBuilder.append(speed);
        }
    }

    /**
     * Метод выводит информацию из общей структуры в консоль.
     */
    private void printReport() {
       System.out.println(stringBuilder.substring(2));
    }

    @Override
    public void run() {
        produceReport();
        printReport();
    }
}