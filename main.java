import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class main {
    public static void main(String[] args) throws IOException {
        String inputFile = "input.txt";
        List<CarThread> carThreads = new ArrayList<>();
        Semaphore parkingSlots = new Semaphore(4);
        int[] gates = {0, 0, 0};

        // Read input from input.txt
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int gateNum = Integer.parseInt(parts[0].trim().split(" ")[1]);
                int carNum = Integer.parseInt(parts[1].trim().split(" ")[1]);
                int arrivalTime = Integer.parseInt(parts[2].trim().split(" ")[1]);
                int parkingTime = Integer.parseInt(parts[3].trim().split(" ")[1]);
                gates[gateNum-1]++;
                CarThread carThread = new CarThread(carNum, gateNum, arrivalTime, parkingTime, parkingSlots);
                carThreads.add(carThread);
            }
        }

        carThreads.sort(Comparator.comparingInt(CarThread::getArrivalTime));

        // Use an executor to manage threads
        ExecutorService executor = Executors.newCachedThreadPool();

        // Track start time for accurate simulation
        long simulationStartTime = System.currentTimeMillis();

        for (CarThread carThread : carThreads) {
            // Schedule each car thread based on its arrival time
            executor.execute(() -> {
                try {
                    long delay = Math.max(0, carThread.getArrivalTime() * 1000L - (System.currentTimeMillis() - simulationStartTime));
                    Thread.sleep(delay);
                    carThread.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all tasks to complete.
        }

        System.out.println("\nSimulation complete.");
        System.out.println("Total Cars Served: " + carThreads.size());
        System.out.println("Current Cars in Parking: 0");
        System.out.println("Details:");
        for (int i = 0; i < 3; i++) {
            System.out.printf("- Gate %d served %d cars.%n", i + 1, gates[i]);
        }
    }
}
