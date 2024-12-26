public class Parcel {
    private String parcelID;
    private Float weight;
    private int daysInDepot;
    private String dimensions;
    private boolean isCollected;

    public String getParcelID(){
        return parcelID;
    }
    public boolean isCollected() {
        return isCollected;
    }
    public void setCollected(boolean collected){
        isCollected = collected;
    }

    public float getWeight() {
        return weight;
    }

    public int getDaysInDepot() {
        return daysInDepot;
    }
}
