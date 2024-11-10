import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class main {
    public static void main(String[] args) throws IOException {
        String inputFile = "input.txt";
        List<CarThread> carThreads = new ArrayList<>();
        Semaphore parkingSlots = new Semaphore(4);

        // read input form input.txt
        try(BufferedReader br = new BufferedReader(new FileReader(inputFile))){
            String line ;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String gateInfo = parts[0].trim();
                String carInfo = parts[1].trim();
                String arriveInfo = parts[2].trim();
                String parkingTimeInfo = parts[3].trim();

                int gateNum = Integer.parseInt(gateInfo.split(" ")[1]);
                int carNum = Integer.parseInt(carInfo.split(" ")[1]);
                int arrivalTime = Integer.parseInt(arriveInfo.split(" ")[1]);
                int parkingTime = Integer.parseInt(parkingTimeInfo.split(" ")[1]);

                CarThread carThread = new CarThread(carNum,gateNum,arrivalTime,parkingTime,parkingSlots);
                carThreads.add(carThread);
            }
        }

        for (CarThread carThread : carThreads) {
            System.out.println(carThread.getCarNumber());
        }
    }
}
