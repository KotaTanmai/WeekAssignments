import java.util.*;

public class Problem8ParkingLotOpenAddressing {

    static class ParkingSpot {
        String licensePlate;
        long entryTime;
        boolean occupied;

        ParkingSpot() {
            licensePlate = null;
            entryTime = 0;
            occupied = false;
        }
    }

    private ParkingSpot[] table;
    private int size;
    private int occupiedCount;

    public Problem8ParkingLotOpenAddressing(int size) {
        this.size = size;
        table = new ParkingSpot[size];
        for (int i = 0; i < size; i++) {
            table[i] = new ParkingSpot();
        }
        occupiedCount = 0;
    }

    private int hash(String licensePlate) {
        return Math.abs(licensePlate.hashCode()) % size;
    }

    public void parkVehicle(String licensePlate) {
        int index = hash(licensePlate);
        int probes = 0;

        while (table[index].occupied) {
            index = (index + 1) % size;
            probes++;
        }

        table[index].licensePlate = licensePlate;
        table[index].entryTime = System.currentTimeMillis();
        table[index].occupied = true;
        occupiedCount++;

        System.out.println("Vehicle " + licensePlate +
                " parked at spot #" + index +
                " (" + probes + " probes)");
    }

    public void exitVehicle(String licensePlate) {
        int index = hash(licensePlate);

        while (table[index].occupied) {
            if (table[index].licensePlate.equals(licensePlate)) {

                long durationMillis = System.currentTimeMillis() - table[index].entryTime;
                double hours = durationMillis / (1000.0 * 60 * 60);
                double fee = hours * 5;

                table[index].occupied = false;
                table[index].licensePlate = null;
                occupiedCount--;

                System.out.println("Vehicle " + licensePlate + " exited from spot #" + index);
                System.out.println("Duration: " + String.format("%.2f", hours) +
                        " hours, Fee: $" + String.format("%.2f", fee));
                return;
            }

            index = (index + 1) % size;
        }

        System.out.println("Vehicle not found.");
    }

    public void getStatistics() {
        double occupancy = ((double) occupiedCount / size) * 100;
        System.out.println("Parking Occupancy: " + String.format("%.2f", occupancy) + "%");
    }

    public static void main(String[] args) throws InterruptedException {

        Problem8ParkingLotOpenAddressing parkingLot =
                new Problem8ParkingLotOpenAddressing(500);

        parkingLot.parkVehicle("ABC-1234");
        parkingLot.parkVehicle("ABC-1235");
        parkingLot.parkVehicle("XYZ-9999");

        Thread.sleep(2000);

        parkingLot.exitVehicle("ABC-1234");

        parkingLot.getStatistics();
    }
}