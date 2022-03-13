package skirental;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class SkiRental {

    private Map<String, Equipment> rentals = new TreeMap<>();

    public Map<String, Equipment> getRentals() {
        return rentals;
    }

    public void loadFromFile(Path path) {
        try (Scanner scanner = new Scanner(path)){
            scanner.nextLine();
            while (scanner.hasNext()) {
                putRental(scanner.nextLine());
            }
        } catch (IOException ioe) {

            throw new IllegalStateException("Cannot read file: " + path.toString());
        }
    }

    private void putRental(String line) {
        String[] parts = line.split(";");
        String name = parts[0];
        String[] equipmentParts = parts[1].split(" ");
        int sizeOfSkis = Integer.parseInt(equipmentParts[0]);
        int sizeOfBoot = Integer.parseInt(equipmentParts[1]);
        rentals.put(name, new Equipment(sizeOfSkis, sizeOfBoot));
    }

    public List<String> listChildren() {
        return rentals.keySet().stream().filter(name -> {
            int sizeOfSkis = rentals.get(name).getSizeOfSkis();
            int sizeOfBoot = rentals.get(name).getSizeOfBoot();
            return 0 < sizeOfSkis && sizeOfSkis <= 120 && 0 < sizeOfBoot && sizeOfBoot <= 37;
        }).toList();
    }

    public String getNameOfPeopleWithBiggestFoot() {
        return rentals.keySet().stream().filter(name -> rentals.get(name).getSizeOfSkis() > 0 && rentals.get(name).getSizeOfBoot() > 0).sorted(Comparator.comparing(name -> rentals.get(name).getSizeOfBoot()).reversed()).toList().get(0);
    }
}
