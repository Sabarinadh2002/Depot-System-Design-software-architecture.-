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
    public Queue<Customer> getCustomerQueue(){
        return customerQueue;
    }
}
