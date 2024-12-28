public class Parcel {
    private String parcelID;
    private Float weight;
    private int daysInDepot;
    private String dimensions;
    private boolean collected;

    public Parcel (String parcelID, String dimensions, float weight, int daysInDepot){
        this.parcelID = parcelID;
        this.dimensions = dimensions;
        this.daysInDepot = daysInDepot;
        this.collected = false;
    }

    public String getParcelID(){
        return parcelID;
    }
    public boolean isCollected() {
        return collected;
    }
    public void setCollected(boolean collected){
        this.collected = collected;
    }

    public float getWeight() {
        return weight;
    }

    public float calculatefee(){
        float baseFee = 5.0f;
        float weightFee = this.weight * 0.5f;
        float timeFee = this.daysInDepot * 1.0f;

        return baseFee + weightFee + timeFee;
    }

    public int getDaysInDepot() {
        return daysInDepot;
    }
}
