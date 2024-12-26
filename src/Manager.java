public class Manager {
    private QueueofCustomers queue;
    private ParcelMap parcels;
    private Worker worker;

    public Manager(){
        queue = new QueueofCustomers();
        parcels = new ParcelMap();
        worker = new Worker();

    }
    public void loadParcelData(String ParcelFile){

    }
    public void loadCustomerData(String customerFile){

    }
    public void processQueue(){
        while (!queue.getCustomerQueue().isEmpty()){
            Customer c = queue.removeCustomer();
            worker.processCustomer(c, parcels);
        }
    }
    public void initGUI(){

    }
    public static void main (String[]args){
        Manager manager = new Manager();
        manager.loadParcelData("parcels.txt");
        manager.loadCustomerData("customers.txt");
        manager.initGUI();
    }
}
