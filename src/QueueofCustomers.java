import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class QueueofCustomers {
    private Queue<Customer> customerQueue = new LinkedList<>();

    public void addCustomer(Customer c) {
        customerQueue.add(c);
        Log.getInstance().append("Customer added: " + c.getName()
                + " (Queue #" + c.getQueueNumber() + ")");
    }

    public Customer removeCustomer() {
        Customer c = customerQueue.poll();
        if (c != null) {
            Log.getInstance().append("Customer removed from queue: " + c.getName()
                    + " (Queue #" + c.getQueueNumber() + ")");
        }
        return c;
    }

    /**
     * Loads customer data from a CSV file with lines like:
     *   Name, ParcelID
     *   e.g.: John Brown,C101
     *
     * We auto-generate the queueNumber sequentially.
     */
    public void loadCustomerData(String customerFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(customerFile))) {
            String line;

            int lineCounter = 0;

            while ((line = br.readLine()) != null) {


                String[] parts = line.split(",");
                if (parts.length == 2) {
                    lineCounter++;
                    String name = parts[0].trim();
                    String parcelID = parts[1].trim();

                    Customer customer = new Customer(lineCounter, name, parcelID);
                    addCustomer(customer);
                }
            }
            Log.getInstance().append("Customer data loaded successfully from " + customerFile);
        } catch (IOException e) {
            Log.getInstance().append("Error reading customer file: " + e.getMessage());
        }
    }

    public Queue<Customer> getCustomerQueue() {
        return customerQueue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---- Customer Queue ----\n");
        for (Customer c : customerQueue) {
            sb.append(c.toString()).append("\n");
        }
        sb.append("------------------------\n");
        return sb.toString();
    }
}
