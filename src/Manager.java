public class Manager {
    private QueueofCustomers queue;
    private ParcelMap parcels;
    private Worker worker;

    public Manager(){
        queue = new QueueofCustomers();
        parcels = new ParcelMap();
        worker = new Worker();

    }
    public void loadParcelData(String parcelFile){
        parcels.loadParcelData(parcelFile);
    }
    public void loadCustomerData(String customerFile){
        queue.loadCustomerData(customerFile);
    }
    public void processQueue(){
        if (!queue.getCustomerQueue().isEmpty()){
            Customer c = queue.removeCustomer();
            worker.processCustomer(c, parcels);
        }else {
            Log.getInstance().append("No more customer in the queue. ");
        }
    }
    public void processOneCustomer() {
        if (!queue.getCustomerQueue().isEmpty()) {
            Customer c = queue.removeCustomer();
            worker.processCustomer(c, parcels);
        } else {
            Log.getInstance().append("No more customers in the queue.");
        }
    }

    public QueueofCustomers getQueue(){
        return queue;
    }
    public ParcelMap getParcels(){
        return parcels;
    }

    public Worker getWorker() {
        return worker;
    }

    public void initGUI(){
        new DepotGUI(this);
    }

    public static void main (String[]args){
        Manager manager = new Manager();

        String customerFile = "src/Custs.csv";
        String parcelFile = "src/Parcels.csv";

        manager.loadParcelData(parcelFile);
        manager.loadCustomerData(customerFile);


        manager.processQueue();

        manager.initGUI();

        Log.getInstance().writeToFile("Depot_log.txt");

        System.out.println(manager.parcels.generateReport());
        System.out.println("Processing complete. Log written to depot_log.txt");
    }
}
