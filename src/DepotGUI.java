import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepotGUI extends JFrame {
    private Manager manager;

    private JTextArea customerTextArea;
    private JTextArea parcelTextArea;
    private JButton processButton;
    private JButton refreshButton;
    private JButton saveLogButton;

    public DepotGUI(Manager manager) {
        super("Depot Parcel System");
        this.manager = manager;

        // Create UI components
        customerTextArea = new JTextArea(15, 25);
        parcelTextArea   = new JTextArea(15, 25);

        processButton = new JButton("Process Next Customer");
        refreshButton = new JButton("Refresh");
        saveLogButton = new JButton("Write Log to File");

        // Layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JPanel buttonPanel = new JPanel(new FlowLayout());

        centerPanel.add(new JScrollPane(customerTextArea));
        centerPanel.add(new JScrollPane(parcelTextArea));

        buttonPanel.add(processButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(saveLogButton);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);  // Center on screen
        this.setVisible(true);

        // Initialize displayed data
        refreshDisplay();

        // Add action listeners (controller logic)
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.processOneCustomer();
                refreshDisplay();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshDisplay();
            }
        });

        saveLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.getInstance().writeToFile("Depot_log.txt");
                JOptionPane.showMessageDialog(DepotGUI.this,
                        "Log written to Depot_log.txt",
                        "Log Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Refresh the text areas with updated data from the Manager.
     */
    private void refreshDisplay() {
        // Show current queue of customers
        customerTextArea.setText(manager.getQueue().toString());

        // Show all parcels
        parcelTextArea.setText(manager.getParcels().generateReport());
    }
}
