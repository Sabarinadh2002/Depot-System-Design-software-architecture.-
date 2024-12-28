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

        String customerFile = "/Custs.csv";
        String parcelFile = "/Parcels.csv";

        manager.loadCustomerData(customerFile);
        manager.loadParcelData(parcelFile);

        manager.processQueue();

        Log.getInstance().writeToFile("Depot_log.txt");

        System.out.println(manager.parcels.generateReport());
        System.out.println("Processing complete. Log written to depot_log.txt");
    }
}
