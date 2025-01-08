package view;

import Contoller.Manager;

import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionListener;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Manager manager = new Manager();
            manager.loadCustomersFromCSV("src/Database/Custs.csv");
            manager.loadParcelsFromCSV("src/Database/Parcels.csv");

            Gui gui = new Gui(manager);
            gui.setVisible(true);

            // (A) On window closing, ask user if they want to save log
            gui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int choice = JOptionPane.showConfirmDialog(
                            gui,
                            "Save data to Depot_log.txt?",
                            "Save Log?",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (choice == JOptionPane.YES_OPTION) {
                        manager.writeLogToFile("src/Database/Depot_log.txt");
                        JOptionPane.showMessageDialog(gui,
                                "Data saved to Depot_log.txt\nTotal Fees = " + manager.getProcessedListAsString(),
                                "Log Saved",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    // Proceed with closure
                    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            });

            // (B) Button Actions

            // 1. Collect Parcel
            gui.addCollectParcelListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String pid = gui.promptParcelID();
                    if (pid != null && !pid.trim().isEmpty()) {
                        String result = manager.collectParcel(pid.trim());
                        gui.showMessage(result);
                        gui.refreshViews();
                    }
                }
            });

            // 2. Add Customer
            gui.addAddCustomerListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = gui.promptCustomerName();
                    if (name == null || name.trim().isEmpty()) return;

                    String parcelID = gui.promptParcelID();
                    if (parcelID == null || parcelID.trim().isEmpty()) return;

                    manager.addCustomer(name.trim(), parcelID.trim());
                    gui.showMessage("Customer " + name + " added for parcel " + parcelID);
                    gui.refreshViews();
                }
            });

            // 3. Add Parcel
            gui.addAddParcelListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String pid = gui.promptParcelID();
                    if (pid == null || pid.trim().isEmpty()) return;

                    float days   = gui.promptFloat("Days in Depot (int):");
                    if (days < 0) return;
                    float weight = gui.promptFloat("Weight:");
                    if (weight < 0) return;
                    float length = gui.promptFloat("Length:");
                    if (length < 0) return;
                    float width  = gui.promptFloat("Width:");
                    if (width < 0) return;
                    float height = gui.promptFloat("Height:");
                    if (height < 0) return;

                    manager.addParcel(pid, (int)days, weight, length, width, height);
                    gui.showMessage("Parcel " + pid + " added.");
                    gui.refreshViews();
                }
            });

            // 4. Process Parcel
            gui.addProcessParcelListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    manager.processParcel();
                    gui.showMessage("Processed first uncollected parcel that has a matching customer (if any).");
                    gui.refreshViews();
                }
            });

            // 5. Remove Parcel
            gui.addRemoveParcelListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String pid = JOptionPane.showInputDialog(gui, "Enter Parcel ID to remove:");
                    if (pid != null && !pid.trim().isEmpty()) {
                        boolean removed = manager.removeParcelByID(pid.trim());
                        if (removed) {
                            gui.showMessage("Parcel " + pid + " removed.");
                        } else {
                            gui.showMessage("Parcel " + pid + " not found!");
                        }
                        gui.refreshViews();
                    }
                }
            });

            // 6. Remove Customer
            gui.addRemoveCustomerListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String cname = JOptionPane.showInputDialog(gui, "Enter Customer Name to remove:");
                    if (cname != null && !cname.trim().isEmpty()) {
                        boolean removed = manager.removeCustomerByName(cname.trim());
                        if (removed) {
                            gui.showMessage("Customer " + cname + " removed.");
                        } else {
                            gui.showMessage("Customer " + cname + " not found!");
                        }
                        gui.refreshViews();
                    }
                }
            });

            // Finally, refresh to show initial data
            gui.refreshViews();
        });
    }
}
