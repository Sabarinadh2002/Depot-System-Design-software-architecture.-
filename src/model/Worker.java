package model;

public class Worker {

    public void processCustomer(Customer customer, ParcelMap parcelMap){
        // Use the correct method name:
        Parcel parcel = parcelMap.getParcelByID(customer.getDesiredParcelID());
        if (parcel == null){
            Log.getInstance().append("Parcel not found for " + customer.getName());
            return;
        }
        if (!parcel.isCollected()){
            float fee = calculateFee(parcel);
            parcel.setCollected(true);
            Log.getInstance().append("Parcel " + parcel.getParcelID()
                    + " collected by " + customer.getName()
                    + ". Fee: " + fee);

        } else {
            Log.getInstance().append("Parcel already collected: " + parcel.getParcelID());
        }
    }
    public float calculateFee(Parcel p){
        float weightfee = p.getWeight()*0.5f;
        float timeFee = p.getDaysInDepot()*1.0f;
        float dimensionalFactor = 1.0f;
        return weightfee + timeFee + dimensionalFactor;
    }
}
