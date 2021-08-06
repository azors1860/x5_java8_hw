import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class AppleStorage {

    private final ConcurrentHashMap<Integer, TimeAndCount> harvestDataMap = new ConcurrentHashMap<>();
    private final CyclicBarrier waitingSalaryBarrier;
    private final static int SALARY_COEFFICIENT = 7;

    public AppleStorage(CyclicBarrier barrier) {
        waitingSalaryBarrier = barrier;
    }

    public ConcurrentHashMap<Integer, TimeAndCount> getHarvestDataMap() {
        return new ConcurrentHashMap<>(harvestDataMap);
    }

    /**
     * Добавляет данные в общее хранилище.
     *
     * @param id    - Идентификатор сборщика яблок.
     * @param time  - Время сбора яблок.
     * @param count - Количество собранных яблок.
     */
    public void addRecord(int id, double time, int count) {
        harvestDataMap.put(id, new TimeAndCount(time, count));
    }

    /**
     * Расчитать и получить значение заработной платы.
     *
     * @param id - Идентификтор аккаунта.
     * @return - Заработная плата.
     */

    public double getSalary(int id) {
        try {
            waitingSalaryBarrier.await();
        } catch (InterruptedException e) {
            //noop
        } catch (BrokenBarrierException e) {
            throw new RuntimeException("Возникла ошибка в ходе ожидания потока", e);
        }
        double pickedApplesCount = harvestDataMap.get(id).count;
        return pickedApplesCount * SALARY_COEFFICIENT;
    }

    static class TimeAndCount {
        private final double time;
        private final int count;

        public TimeAndCount(double time, int count) {
            this.time = time;
            this.count = count;
        }

        public double getTime() {
            return time;
        }

        public double getCount() {
            return count;
        }
    }
}