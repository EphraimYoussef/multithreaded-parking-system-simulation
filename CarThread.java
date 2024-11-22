import java.util.concurrent.Semaphore;

public class CarThread extends Thread {
    private int carNumber;
    private int gateNumber;
    private int arrivalTime;
    private int parkingTime;
    private Semaphore parkingSlotsSemaphore;

    public CarThread(int carNumber, int gateNumber, int arrivalTime, int parkingTime, Semaphore parkingSlotsSemaphore) {
        this.carNumber = carNumber;
        this.gateNumber = gateNumber;
        this.arrivalTime = arrivalTime;
        this.parkingTime = parkingTime;
        this.parkingSlotsSemaphore = parkingSlotsSemaphore;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public void run() {
        try {
            System.out.printf("Car %d from Gate %d arrived at time %d%n", carNumber, gateNumber, arrivalTime);

            // Try to acquire a parking slot
            if (parkingSlotsSemaphore.tryAcquire()) {
                System.out.printf("Car %d from Gate %d parked. (Parking Status: %d spots occupied)%n",
                        carNumber, gateNumber, 4 - parkingSlotsSemaphore.availablePermits());

                // Simulate parking time
                Thread.sleep(parkingTime * 1000);

                // Car leaves after parking
                parkingSlotsSemaphore.release();
                System.out.printf("Car %d from Gate %d left after %d units of time. (Parking Status: %d spots occupied)%n",
                        carNumber, gateNumber, parkingTime, 4 - parkingSlotsSemaphore.availablePermits());
            } else {
                System.out.printf("Car %d from Gate %d waiting for a spot.%n", carNumber, gateNumber);

                // Wait until a spot is available
                parkingSlotsSemaphore.acquire();

                // Park the car
                System.out.printf("Car %d from Gate %d parked after waiting. (Parking Status: %d spots occupied)%n",
                        carNumber, gateNumber, 4 - parkingSlotsSemaphore.availablePermits());

                // Simulate parking time
                Thread.sleep(parkingTime * 1000);

                // Car leaves after parking
                parkingSlotsSemaphore.release();
                System.out.printf("Car %d from Gate %d left after %d units of time. (Parking Status: %d spots occupied)%n",
                        carNumber, gateNumber, parkingTime, 4 - parkingSlotsSemaphore.availablePermits());
            }
        } catch (InterruptedException e) {
            System.out.printf("Car %d from Gate %d encountered an error.%n", carNumber, gateNumber);
            Thread.currentThread().interrupt();
        }
    }
}
