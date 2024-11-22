public class SimulationClock implements Runnable {
    private volatile int currentTime = 0;
    private volatile boolean running = true;

    public synchronized int getTime() {
        return currentTime;
    }

    public synchronized void incrementTime() {
        currentTime++;
    }

    public void stopClock() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000); // 1 second real-time = 1 unit simulation time
                incrementTime();
                synchronized (this) {
                    notifyAll(); // Notify waiting threads
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
