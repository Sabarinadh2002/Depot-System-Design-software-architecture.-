package Contoller;

import model.Customer;
import model.Parcel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Manager {
    private final List<Customer> customers = new ArrayList<>();
    private final List<Parcel> parcels = new ArrayList<>();
    private final List<Parcel> processedParcels = new ArrayList<>();

    private int nextCustomerSeq = 1;
    private float totalFees = 0.0f;            // keep track of total fees
    private StringBuilder logData = new StringBuilder(); // record all events

    // ------------------------------------------
    // Load from CSV
    // ------------------------------------------
    public void loadCustomersFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name     = parts[0].trim();
                    String parcelID = parts[1].trim().toUpperCase();
                    Customer c = new Customer(nextCustomerSeq++, name, parcelID);
                    customers.add(c);
                } else {
                    System.out.println("[WARN] Skipped line (expected 2 columns): " + line);
                }
            }
            log("Customers loaded successfully from " + filename);
        } catch (IOException e) {
            log("Error reading customers file: " + e.getMessage());
        }
    }

    public void loadParcelsFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String parcelID  = parts[0].trim();
                    int daysInDepot  = Integer.parseInt(parts[1].trim());
                    float weight     = Float.parseFloat(parts[2].trim());
                    float length     = Float.parseFloat(parts[3].trim());
                    float width      = Float.parseFloat(parts[4].trim());
                    float height     = Float.parseFloat(parts[5].trim());

                    Parcel p = new Parcel(parcelID, daysInDepot, weight, length, width, height);
                    parcels.add(p);
                } else {
                    System.out.println("[WARN] Skipped line (expected 6 columns): " + line);
                }
            }
            log("Parcels loaded successfully from " + filename);
        } catch (IOException e) {
            log("Error reading parcel file: " + e.getMessage());
        } catch (NumberFormatException e) {
            log("Error parsing numeric fields: " + e.getMessage());
        }
    }

    // ------------------------------------------
    // Add methods
    // ------------------------------------------
    public void addCustomer(String name, String parcelID) {
        if (name == null || name.trim().isEmpty()) {
            return;
        }
        if (parcelID == null || parcelID.trim().isEmpty()) {
            return;
        }
        int seq = customers.size() + 1;
        Customer c = new Customer(seq, name.trim(), parcelID.trim());
        customers.add(c);
        log("Added new Customer: " + c.getName() + ", Parcel: " + parcelID);
    }

    public void addParcel(String parcelID, int daysInDepot, float weight,
                          float length, float width, float height) {
        if (parcelID == null || parcelID.trim().isEmpty()) {
            return;
        }
        Parcel p = new Parcel(parcelID.trim(), daysInDepot, weight, length, width, height);
        parcels.add(p);
        log("Added new Parcel: " + parcelID);
    }

    // ------------------------------------------
    // Process logic
    // ------------------------------------------
    public void processParcel() {
        for (int i = 0; i < parcels.size(); i++) {
            Parcel p = parcels.get(i);
            if (!p.isCollected()) {
                Customer matchingCustomer = findCustomerForParcel(p.getParcelID());
                if (matchingCustomer == null) {
                    // No matching customer => skip
                    continue;
                }
                // we have a matching customer => collect
                p.setCollected(true);
                float fee = calculateFee(p);
                totalFees += fee;
                processedParcels.add(p);
                parcels.remove(i);
                customers.remove(matchingCustomer);

                log("Processed Parcel: " + p.getParcelID()
                        + ", Fee: " + fee + ", Customer: " + matchingCustomer.getName());

                break;
            }
        }
    }

    /**
     * Attempt to collect a specific parcel by ID, returning a message.
     */
    public String collectParcel(String parcelID) {
        parcelID = parcelID.toUpperCase();

        // 1) Already in processed?
        for (Parcel proc : processedParcels) {
            if (proc.getParcelID().equals(parcelID)) {
                return "Parcel already collected!";
            }
        }

        // 2) Search in parcels
        for (int i = 0; i < parcels.size(); i++) {
            Parcel p = parcels.get(i);
            if (p.getParcelID().equals(parcelID)) {
                if (p.isCollected()) {
                    return "Parcel already collected!";
                }
                // Check for matching customer
                Customer matchingCustomer = findCustomerForParcel(parcelID);
                if (matchingCustomer == null) {
                    return "No matching customer!";
                }
                // Collect
                p.setCollected(true);
                float fee = calculateFee(p);
                totalFees += fee;
                processedParcels.add(p);
                parcels.remove(i);
                customers.remove(matchingCustomer);

                log("Collected Parcel: " + parcelID
                        + ", Fee: " + fee + ", Customer: " + matchingCustomer.getName());

                return "You have collected the parcel successfully!";
            }
        }

        // 3) Not found
        return "Invalid ID!";
    }

    // ------------------------------------------
    // Remove logic
    // ------------------------------------------
    public boolean removeParcelByID(String pid) {
        pid = pid.toUpperCase();
        for (int i = 0; i < parcels.size(); i++) {
            Parcel p = parcels.get(i);
            if (p.getParcelID().equals(pid)) {
                parcels.remove(i);
                log("Removed Parcel: " + pid);
                return true;
            }
        }
        return false;
    }

    public boolean removeCustomerByName(String name) {
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            if (c.getName().equalsIgnoreCase(name.trim())) {
                customers.remove(i);
                log("Removed Customer: " + name);
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------
    // Helper methods
    // ------------------------------------------
    private Customer findCustomerForParcel(String parcelID) {
        for (Customer c : customers) {
            if (c.getDesiredParcelID().equalsIgnoreCase(parcelID)) {
                return c;
            }
        }
        return null;
    }

    private float calculateFee(Parcel p) {
        // Example formula: baseFee + weightFee + daysFee
        float baseFee = 5.0f;
        float weightFee = p.getWeight() * 0.5f;
        float daysFee   = p.getDaysInDepot() * 1.0f;
        return baseFee + weightFee + daysFee; // or add dimension factor if you like
    }

    private void log(String msg) {
        logData.append(msg).append("\n");
        System.out.println("[LOG] " + msg); // optional console output
    }

    // Write log data to file, including total fees
    public void writeLogToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.print(logData.toString());
            pw.println("Total Fees Collected: " + totalFees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ------------------------------------------
    // Display
    // ------------------------------------------
    public String getCustomerListAsString() {
        if (customers.isEmpty()) return "(no customers)";
        StringBuilder sb = new StringBuilder();
        for (Customer c : customers) {
            sb.append(c).append("\n");
        }
        return sb.toString();
    }

    public String getParcelListAsString() {
        if (parcels.isEmpty()) return "(no parcels)";
        StringBuilder sb = new StringBuilder();
        for (Parcel p : parcels) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }

    public String getProcessedListAsString() {
        if (processedParcels.isEmpty()) return "(none processed yet)";
        StringBuilder sb = new StringBuilder();
        for (Parcel p : processedParcels) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }
}
