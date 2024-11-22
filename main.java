import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;

public class main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String inputFile = "input.txt";
        List<CarThread> carThreads = new ArrayList<>();
        Semaphore parkingSlots = new Semaphore(4);

        // Read input from input.txt
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int gateNum = Integer.parseInt(parts[0].trim().split(" ")[1]);
                int carNum = Integer.parseInt(parts[1].trim().split(" ")[1]);
                int arrivalTime = Integer.parseInt(parts[2].trim().split(" ")[1]);
                int parkingTime = Integer.parseInt(parts[3].trim().split(" ")[1]);

                CarThread carThread = new CarThread(carNum, gateNum, arrivalTime, parkingTime, parkingSlots);
                carThreads.add(carThread);
            }
        }

        carThreads.sort(Comparator.comparingInt(CarThread::getArrivalTime));

        // Start all car threads and simulate arrival times
        for (CarThread carThread : carThreads) {
            Thread.sleep(carThread.getArrivalTime() * 1000); // Simulate arrival delay
            carThread.start();
        }

        // Wait for all car threads to finish
        for (CarThread carThread : carThreads) {
            carThread.join();
        }

        System.out.println("Simulation complete. Total cars served: " + carThreads.size());
    }
}
