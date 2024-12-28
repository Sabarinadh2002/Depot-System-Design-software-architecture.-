import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class QueueofCustomers {
    private Queue<Customer> customerQueue = new LinkedList<>();

    public void addCustomer(Customer c){
        customerQueue.add(c);
        Log.getInstance().append("Customer added: " + c.getName());
    }
    public Customer removeCustomer(){
        Customer c = customerQueue.poll();
        if (c != null){
            Log.getInstance().append("Customer removed from queue: "+ c.getName());
        }
        return c;
    }
    public void loadCustomerData(String customerFile){
        try (BufferedReader br = new BufferedReader(new BufferedReader(customerFile))){
            String line;
            boolean isFirstLine = true;
            while((line = br.readLine()) != null){
                if (isFirstLine){
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 3){
                    int queueNumber = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String parcelID = parts[2].trim();
                    Customer customer = new Customer(queueNumber, name, parcelID);
                    queue.addCustomer(customer);
                }
            }
            Log.getInstance().append("Customer data loaded successfully from " + customerFile);
        } catch (IOException e) {
            Log.getInstance().append("Error reading customer file: " + e.getMessage());
        }
    }

    public Queue<Customer> getCustomerQueue(){
        return customerQueue;
    }
}
