import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class WaterMonitoringSysApp {
    static final String WATER_SAMPLES = "water_samples.txt";
    static LinkedList linkedList;
    static Queue queue;

    public static void main(String[] args) {
        System.out.println("Starting Water Monitoring System...");
        linkedList = new LinkedList();
        queue = new Queue();

        File file = new File(WATER_SAMPLES);
        if (!file.exists()) {
            System.out.println("Error: water_samples.txt not found.");
            return;
        }

        try {
            System.out.println("Loading water samples from file...");
            loadWaterSamplesFromFile(WATER_SAMPLES);
            System.out.println("Water samples loaded successfully.");
            System.out.println("Running application...");
            runApplication();
        } catch (IOException ioe) {
            System.out.println("Error loading water samples:");
            ioe.printStackTrace();
        }
        System.out.println("Application terminated.");
    }

    private static void loadWaterSamplesFromFile(String filename) throws IOException {
    Scanner sc = new Scanner(new File(filename));

    while (sc.hasNextLine()) {
        String line = sc.nextLine().trim(); // Trim to remove unwanted spaces

        String[] data = line.split("\\s*,\\s*");

        try {
            WaterSample sample = new WaterSample(
                data[0], data[1], data[2],
                parseDouble(data[3]), parseDouble(data[4]), parseDouble(data[5]),
                parseDouble(data[6]), parseLong(data[7]), parseLong(data[8]),
                parseDouble(data[9]), parseDouble(data[10]), parseDouble(data[11]),
                parseDouble(data[12]), data[13]
            );

            linkedList.insertAtBack(sample);
            queue.enqueue(sample);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing line: " + line);
        }
    }
        sc.close();
    }

    private static double parseDouble(String s) {
        try {
            return Double.parseDouble(s.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + s);
            return 0.0;}
    }

    private static long parseLong(String s) {
        try {
            return Long.parseLong(s.trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid long format: " + s);
            return 0L;
        }
    }

    private static void runApplication() {
    Scanner sc = new Scanner(System.in);
    int choice;

    do {
        System.out.println("\nWelcome to our Water Quality Monitoring System");
        System.out.println("1 - Add new sample\n2 - Remove a sample\n3 - Update a sample\n4 - Search a sample\n5 - Generate report\n0 - Exit");
        System.out.print("Enter your choice (0-5): ");

        try {
            choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                WaterSample newSample = AddWaterSample();
                linkedList.insertAtBack(newSample);
                queue.enqueue(newSample);
                saveSampleToFile(newSample);
                System.out.println("Sample added successfully.");} 
            else if (choice == 2) {
                removeSampleFromLinkedList(sc);} 
            else if (choice == 3) {
                updateWaterSample();} 
            else if (choice == 4) {
                searchWaterSample();} 
            else if (choice == 5) {
                generateReport();} 
            else if (choice == 0) {
                System.out.println("Exiting application. Thank you for using.");} 
            else {
                System.out.println("Invalid choice. Please try again.");}
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            choice = -1; // Ensure loop continues if input is invalid
            }
        } while (choice != 0);
    }
    
    private static WaterSample AddWaterSample() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter date (dd/mm/yyyy): ");
        String date = scanner.nextLine();
        System.out.print("Enter time (HH:MM) *24-hour format*: ");
        String time = scanner.nextLine();
        System.out.print("Enter location: ");
        String location = scanner.nextLine();
        System.out.print("Enter ambient temperature: ");
        double temperature = scanner.nextDouble();
        System.out.print("Enter ambient humidity: ");
        double humidity = scanner.nextDouble();
        System.out.print("Enter the sample's temperature: ");
        double sTemperature = scanner.nextDouble();
        System.out.print("Enter pH: ");
        double pH = scanner.nextDouble();
        System.out.print("Enter electrical conductivity: ");
        long elecConductivity = scanner.nextLong();
        System.out.print("Enter total dissolved solids: ");
        long totalDissolvedSols = scanner.nextLong();
        System.out.print("Enter total suspended solids: ");
        double totalSuspendedSols = scanner.nextDouble();
        System.out.print("Enter dissolved oxygen: ");
        double dissolvedOxygen = scanner.nextDouble();
        System.out.print("Enter turbidity: ");
        double turbidity = scanner.nextDouble();
        System.out.print("Enter hardness: ");
        double hardness = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter hardness class (SOFT/MODERATELY HARD): ");
        String hardnessClass = scanner.nextLine();

        return new WaterSample(date, time, location, temperature, humidity, sTemperature, pH,
                elecConductivity, totalDissolvedSols, totalSuspendedSols,
                dissolvedOxygen, turbidity, hardness, hardnessClass);
    }
    
    private static void saveSampleToFile(WaterSample newSample) {
    List<String> fileContent = new ArrayList<>();
    
    // Read existing file content
    try (Scanner scanner = new Scanner(new File(WATER_SAMPLES))) {
        while (scanner.hasNextLine()) {
            fileContent.add(scanner.nextLine());
        }
    } catch (IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
    }

    fileContent.add(newSample.toTXT());

    // Write everything back to the file
    try (PrintWriter writer = new PrintWriter(new File(WATER_SAMPLES))) {
        for (String line : fileContent) {
            writer.println(line);
        }
    } catch (IOException e) {
        System.out.println("Error saving sample to file: " + e.getMessage());
    }
    }

    private static void removeSampleFromLinkedList(Scanner sc) {
    System.out.println("Attributes:\n1 - Date\n2 - Location\n3 - Ambient Temperature\n4 - pH\n5 - Hardness Class");
    
    System.out.print("Enter attribute number to remove (1-5): ");
    while (!sc.hasNextInt()) {
        System.out.println("Invalid input. Please enter a number between 1 and 5.");
        sc.next(); // Consume invalid input
    }
    int attributeNum = sc.nextInt();
    sc.nextLine(); // Consume newline
    
    if (attributeNum < 1 || attributeNum > 5) {
        System.out.println("Invalid attribute number. Please enter a valid option.");
        return;
    }
    
    System.out.print("Enter value to remove: ");
    String value = sc.nextLine().trim();
    
    if (value.isEmpty()) {
        System.out.println("Value cannot be empty. Please enter a valid value.");
        return;
    }
    
    Node current = linkedList.getHead();
    Node previous = null;
    boolean found = false;
    
    while (current != null) {
        WaterSample sample = current.getData();
        boolean match = false;
        
        if (attributeNum == 1 && sample.getDate().equalsIgnoreCase(value)) {
            match = true;
        } else if (attributeNum == 2 && sample.getLocation().equalsIgnoreCase(value)) {
            match = true;
        } else if (attributeNum == 3 && String.valueOf(sample.getTemperature()).equalsIgnoreCase(value)) {
            match = true;
        } else if (attributeNum == 4 && String.valueOf(sample.getPH()).equalsIgnoreCase(value)) {
            match = true;
        } else if (attributeNum == 5 && sample.getHardnessClass().equalsIgnoreCase(value)) {
            match = true;
        }
        
        if (match) {
            printTableHeader("MATCHING SAMPLE FOUND");
            printTableRow(sample);
            System.out.print("Do you want to delete this sample? (yes/no): ");
            String confirm = sc.nextLine().trim();
            
            if (confirm.equalsIgnoreCase("yes")) {
                if (previous == null) { // Removing head
                } else {
                    previous.next = current.getNext();
                }
                found = true;
                System.out.println("Sample removed successfully.");
                return; // Exit after deletion
            } else {
                System.out.println("Deletion canceled.");
                return;
            }
        }
        
        previous = current;
        current = current.getNext();
    }
    
    if (!found) {
        System.out.println("Sample not found.");
    }
    }
    
    private static void updateWaterSample() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Date: ");
        String date = scanner.nextLine();
        System.out.print("Enter Time: ");
        String time = scanner.nextLine();
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();
        
        Node current = linkedList.getHead();
        WaterSample selectedSample = null;

        while (current != null) {
            WaterSample sample = current.getData();
            if (sample.getDate().equalsIgnoreCase(date) && sample.getTimestamp().equalsIgnoreCase(time) && sample.getLocation().equalsIgnoreCase(location)) {
                printTableHeader("MATCHING SAMPLE(S) FOUND");
                printTableRow(sample);
                selectedSample = sample;
                break;
            }
            current = current.getNext();
        }
        
        if (selectedSample == null) {
            System.out.println("Sample not found.");
            return;
        }
        
        System.out.println("Select attribute to update:");
        System.out.println("1. Date\n2. Time\n3. Location\n4. Temperature\n5. Humidity\n6. Sample Temperature\n7. pH\n8. Electrical Conductivity\n9. Total Dissolved Solids\n10. Total Suspended Solids\n11. Dissolved Oxygen\n12. Turbidity\n13. Hardness\n14. Hardness Class");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter new value: ");
        String newValue = scanner.nextLine();
        
        if (choice == 1) selectedSample.setDate(newValue);
        else if (choice == 2) selectedSample.setTimestamp(newValue);
        else if (choice == 3) selectedSample.setLocation(newValue);
        else if (choice == 4) selectedSample.setTemperature(Double.parseDouble(newValue));
        else if (choice == 5) selectedSample.setHumidity(Double.parseDouble(newValue));
        else if (choice == 6) selectedSample.setSTemperature(Double.parseDouble(newValue));
        else if (choice == 7) selectedSample.setPH(Double.parseDouble(newValue));
        else if (choice == 8) selectedSample.setElecConductivity(Long.parseLong(newValue));
        else if (choice == 9) selectedSample.setTotalDissolvedSols(Long.parseLong(newValue));
        else if (choice == 10) selectedSample.setTotalSuspendedSols(Double.parseDouble(newValue));
        else if (choice == 11) selectedSample.setDissolvedOxygen(Double.parseDouble(newValue));
        else if (choice == 12) selectedSample.setTurbidity(Double.parseDouble(newValue));
        else if (choice == 13) selectedSample.setHardness(Double.parseDouble(newValue));
        else if (choice == 14) selectedSample.setHardnessClass(newValue);
        else {
            System.out.println("Invalid choice.");
            return;
        }
        updateSampleInFile(selectedSample);
        System.out.println("Sample updated successfully.");
    }
    
    private static void updateSampleInFile(WaterSample updatedSample) {
        try {
            List<String> fileContent = new ArrayList<>();
            Scanner scanner = new Scanner(new File(WATER_SAMPLES));
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data[0].equals(updatedSample.getDate()) && data[1].equals(updatedSample.getTimestamp()) && data[2].equals(updatedSample.getLocation())) {
                    fileContent.add(updatedSample.toTXT());
                } else {
                    fileContent.add(line);
                }
            }
            scanner.close();
            
            PrintWriter writer = new PrintWriter(new File(WATER_SAMPLES));
            for (String line : fileContent) {
                writer.println(line);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error updating sample in file.");
        }
    }
    
    private static void searchWaterSample() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter search criteria (press Enter to skip any field):");

    System.out.print("Enter Date (dd/mm/yyyy) or press Enter to skip: ");
    String date = scanner.nextLine().trim().toLowerCase();

    System.out.print("Enter Time (24-hour format) or press Enter to skip: ");
    String time = scanner.nextLine().trim().toLowerCase();

    System.out.print("Enter Location or press Enter to skip: ");
    String location = scanner.nextLine().trim().toLowerCase();

    System.out.print("Enter Hardness Class or press Enter to skip: ");
    String hardnessClass = scanner.nextLine().trim().toLowerCase();

    Node current = linkedList.getHead();
    boolean found = false;

    printTableHeader("WATER SAMPLE(S) FOUND");

    while (current != null) {
        WaterSample sample = current.getData();
        boolean matches = true; // Assume match, then check conditions

        if (!date.isEmpty() && !sample.getDate().equalsIgnoreCase(date)) matches = false;
        if (!time.isEmpty() && !sample.getTimestamp().equalsIgnoreCase(time)) matches = false;
        if (!location.isEmpty() && !sample.getLocation().equalsIgnoreCase(location)) matches = false;
        if (!hardnessClass.isEmpty() && !sample.getHardnessClass().toLowerCase().equals(hardnessClass)) matches = false;

        if (matches) {
            printTableRow(sample);
            found = true;
        }
        current = current.getNext();
    }

    if (!found) {
        System.out.println("No matching sample found.");
    }
    }


    
    private static void generateReport() {
    if (queue.isEmpty() && linkedList.isEmpty()) {  
        System.out.println("No samples available for report.");
        return;
    }
    
    int countSoft = 0, countModeratelyHard = 0;
    int sampleCount = 0;
    double totalTemperature = 0, totalPH = 0;

    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter start date (dd/mm/yyyy) or 'all': ");
    String startDate = scanner.nextLine();
    System.out.print("Enter end date (dd/mm/yyyy) or 'all': ");
    String endDate = scanner.nextLine();
    System.out.print("Enter location or 'all': ");
    String location = scanner.nextLine();
    System.out.print("Display all data? (yes/no): ");
    String displayAll = scanner.nextLine();
    
    
    Node currentNode = linkedList.getHead();

    while (currentNode != null) {
        WaterSample sample = currentNode.getData();
        if (displayAll.equalsIgnoreCase("no")) {
            sampleCount++;
            totalTemperature += sample.getTemperature();
            totalPH += sample.getPH();
            if (sample.getHardnessClass().equalsIgnoreCase("SOFT")) countSoft++;
            else if (sample.getHardnessClass().equalsIgnoreCase("MODERATELY HARD")) countModeratelyHard++;
        }
        else if ((startDate.equalsIgnoreCase("all") || endDate.equalsIgnoreCase("all") || isWithinDateRange(sample.getDate(), startDate, endDate)) &&
            (location.equalsIgnoreCase("all") || sample.getLocation().equalsIgnoreCase(location))) {
            printTableHeader("WATER SAMPLE REPORT");
            printTableRow(currentNode.getData());
            sampleCount++;
            totalTemperature += sample.getTemperature();
            totalPH += sample.getPH();

            if (sample.getHardnessClass().equalsIgnoreCase("SOFT")) countSoft++;
            else if (sample.getHardnessClass().equalsIgnoreCase("MODERATELY HARD")) countModeratelyHard++;
        }
        currentNode = currentNode.getNext();
    }

    if (sampleCount > 0) {
        System.out.printf("\nTotal Samples: %d, Avg Temperature: %.2f, Avg pH: %.2f\n",
        sampleCount, totalTemperature / sampleCount, totalPH / sampleCount);
        System.out.println("Soft Water Samples: " + countSoft);
        System.out.println("Moderately Hard Samples: " + countModeratelyHard);
    } else {
        System.out.println("No data available for the specified criteria.");
    }
}

    private static boolean isWithinDateRange(String sampleDate, String startDate, String endDate) {
        if (startDate.equalsIgnoreCase("all") || endDate.equalsIgnoreCase("all")) return true;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date sample = sdf.parse(sampleDate);
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            return !sample.before(start) && !sample.after(end);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
            return false;
        }
    }
    
    private static void printTableHeader(String title) {
    System.out.println("\n" + "-------------------------------------------------------------------------------------------------------------------------- " + title + " -----------------------------------------------------------------------------------------------------------");
    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    System.out.printf("| %-12s | %-10s | %-20s | %-12s | %-12s | %-12s | %-18s | %-18s | %-18s | %-18s | %-18s | %-12s | %-12s | %-16s |%n","Date", "Time", "Location", "Temp (°C)", "Humidity", "Sample Temp", "pH", "Elec Conductivity", "TDS (mg/L)","TSS (mL/L)", "DO (mg/L)", "Turbidity", "Hardness", "Hardness Class");
    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void printTableRow(WaterSample sample) {
    System.out.printf("| %-12s | %-10s | %-20s | %-12.2f | %-12.2f | %-12.2f | %-18.2f | %-18d | %-18d | %-18.2f | %-18.2f | %-12.2f | %-12.2f | %-16s |%n",
            sample.getDate(), sample.getTimestamp(), sample.getLocation(), sample.getTemperature(),
            sample.getHumidity(), sample.getSTemperature(), sample.getPH(), sample.getElecConductivity(),
            sample.getTotalDissolvedSols(), sample.getTotalSuspendedSols(), sample.getDissolvedOxygen(),
            sample.getTurbidity(), sample.getHardness(), sample.getHardnessClass());
    }
}