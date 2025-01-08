package view;

import Contoller.Manager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Gui extends JFrame {
    private final Manager manager;

    private final JButton btnTabCustomer;
    private final JButton btnTabWorker;

    // Customer panel
    private final JButton btnCollectParcel;

    // Worker panel
    private final JButton btnAddCustomer;
    private final JButton btnAddParcel;
    private final JButton btnProcessParcel;

    // NEW buttons
    private final JButton btnRemoveParcel;
    private final JButton btnRemoveCustomer;

    private final JTextArea txtCustomerList;
    private final JTextArea txtParcelList;
    private final JTextArea txtProcessedList;

    private final CardLayout cardLayout;
    private final JPanel panelCenter;

    private static final String CARD_CUSTOMER = "CARD_CUSTOMER";
    private static final String CARD_WORKER   = "CARD_WORKER";

    public Gui(Manager manager) {
        super("Depot Application");
        this.manager = manager;

        cardLayout = new CardLayout();
        panelCenter = new JPanel(cardLayout);

        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // Top "tab" panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnTabCustomer = new JButton("Customer");
        btnTabWorker   = new JButton("Worker");

        setTabButtonActive(btnTabCustomer, true);
        setTabButtonActive(btnTabWorker,   false);

        btnTabCustomer.addActionListener(e -> {
            cardLayout.show(panelCenter, CARD_CUSTOMER);
            setTabButtonActive(btnTabCustomer, true);
            setTabButtonActive(btnTabWorker,   false);
        });
        btnTabWorker.addActionListener(e -> {
            cardLayout.show(panelCenter, CARD_WORKER);
            setTabButtonActive(btnTabCustomer, false);
            setTabButtonActive(btnTabWorker,   true);
        });

        topPanel.add(btnTabCustomer);
        topPanel.add(btnTabWorker);
        topPanel.setBackground(Color.WHITE);
        add(topPanel, BorderLayout.NORTH);

        // Shared text areas
        txtCustomerList  = new JTextArea();
        txtParcelList    = new JTextArea();
        txtProcessedList = new JTextArea();

        txtCustomerList.setEditable(false);
        txtParcelList.setEditable(false);
        txtProcessedList.setEditable(false);

        // ------------ CUSTOMER PANEL ------------
        JPanel panelCustomer = new JPanel(new BorderLayout(10, 10));
        btnCollectParcel = new JButton("Collect Parcel");

        JPanel custButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        custButtonPanel.add(btnCollectParcel);

        JPanel custCenterPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        custCenterPanel.add(scrollWrapped(txtCustomerList, "Customer List"));
        custCenterPanel.add(scrollWrapped(txtParcelList,   "Parcel List"));
        custCenterPanel.add(scrollWrapped(txtProcessedList,"Processed List"));

        panelCustomer.add(custButtonPanel,  BorderLayout.NORTH);
        panelCustomer.add(custCenterPanel,  BorderLayout.CENTER);

        // ------------ WORKER PANEL ------------
        JPanel panelWorker = new JPanel(new BorderLayout(10, 10));
        btnAddCustomer   = new JButton("Add Customer");
        btnAddParcel     = new JButton("Add Parcel");
        btnProcessParcel = new JButton("Process Parcel");

        // NEW Buttons
        btnRemoveParcel   = new JButton("Remove Parcel");
        btnRemoveCustomer = new JButton("Remove Customer");

        // We’ll place 5 buttons in a 3x2 or 2x3 grid
        JPanel workerButtonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        workerButtonPanel.add(btnAddCustomer);
        workerButtonPanel.add(btnAddParcel);
        workerButtonPanel.add(btnProcessParcel);
        workerButtonPanel.add(btnRemoveParcel);
        workerButtonPanel.add(btnRemoveCustomer);

        JPanel workerCenterPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        workerCenterPanel.add(scrollWrapped(txtCustomerList, "Customer List"));
        workerCenterPanel.add(scrollWrapped(txtParcelList,   "Parcel List"));
        workerCenterPanel.add(scrollWrapped(txtProcessedList,"Processed List"));

        panelWorker.add(workerButtonPanel,   BorderLayout.NORTH);
        panelWorker.add(workerCenterPanel,   BorderLayout.CENTER);

        // CardLayout
        panelCenter.add(panelCustomer, CARD_CUSTOMER);
        panelCenter.add(panelWorker,   CARD_WORKER);

        cardLayout.show(panelCenter, CARD_CUSTOMER);
        add(panelCenter, BorderLayout.CENTER);
    }

    // Helper
    private JPanel scrollWrapped(JTextArea area, String title) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel(title, SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }

    public void refreshViews() {
        txtCustomerList.setText(manager.getCustomerListAsString());
        txtParcelList.setText(manager.getParcelListAsString());
        txtProcessedList.setText(manager.getProcessedListAsString());
    }

    // Customer tab
    public void addCollectParcelListener(ActionListener listener) {
        btnCollectParcel.addActionListener(listener);
    }

    // Worker tab
    public void addAddCustomerListener(ActionListener listener) {
        btnAddCustomer.addActionListener(listener);
    }
    public void addAddParcelListener(ActionListener listener) {
        btnAddParcel.addActionListener(listener);
    }
    public void addProcessParcelListener(ActionListener listener) {
        btnProcessParcel.addActionListener(listener);
    }

    // NEW button hooks
    public void addRemoveParcelListener(ActionListener listener) {
        btnRemoveParcel.addActionListener(listener);
    }
    public void addRemoveCustomerListener(ActionListener listener) {
        btnRemoveCustomer.addActionListener(listener);
    }

    // Utility prompts
    public String promptCustomerName() {
        return JOptionPane.showInputDialog(this, "Enter Customer Name:");
    }

    public String promptParcelID() {
        return JOptionPane.showInputDialog(this, "Which parcel ID are you about to receive?");
    }

    public float promptFloat(String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(this, message);
            if (input == null) {
                return -1;  // user canceled
            }
            try {
                return Float.parseFloat(input.trim());
            } catch (NumberFormatException ex) {
                showError("Invalid numeric input: " + input);
            }
        }
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void setTabButtonActive(JButton btn, boolean isActive) {
        if (isActive) {
            btn.setForeground(Color.WHITE);
            btn.setBackground(Color.BLACK);
        } else {
            btn.setForeground(Color.BLACK);
            btn.setBackground(Color.WHITE);
        }
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
    }
}
