import java.util.concurrent.CyclicBarrier;

public class Main {

    public static void main(String[] args) {

        int pickersCount = 500;
        PickersReport report = new PickersReport();
        CyclicBarrier waitingSalaryBarrier = new CyclicBarrier(pickersCount, report);
        AppleStorage storage = new AppleStorage(waitingSalaryBarrier);
        report.setAppleStorage(storage);

        for (int i = 1; i < pickersCount + 1; i++) {
            new ApplePicker(i, storage).start();
        }
    }
}
