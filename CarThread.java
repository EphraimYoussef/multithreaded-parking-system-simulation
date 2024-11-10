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

    public int getGateNumber() {
        return gateNumber;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getParkingTime() {
        return parkingTime;
    }
}
