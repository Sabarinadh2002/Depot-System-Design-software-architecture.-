package model;

public class Parcel {
    private String parcelID;
    private int daysInDepot;
    private float weight;
    private float length;
    private float width;
    private float height;
    private boolean collected;

    /**
     * @param parcelID     e.g. "X009"
     * @param daysInDepot  e.g. 9
     * @param weight       e.g. 1
     * @param length       e.g. 9
     * @param width        e.g. 9
     * @param height       e.g. 7
     */
    public Parcel(String parcelID, int daysInDepot, float weight,
                  float length, float width, float height) {
        this.parcelID = parcelID;
        this.daysInDepot = daysInDepot;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.collected = false;
    }

    public String getParcelID() {
        return parcelID;
    }

    public int getDaysInDepot() {
        return daysInDepot;
    }

    public float getWeight() {
        return weight;
    }

    public float getLength() {
        return length;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    /**
     * Example: Simple fee calculation that factors weight, days, and volume
     */
    public float calculateFee() {
        float baseFee = 5.0f;
        float weightFee = this.weight * 0.5f;
        float timeFee = this.daysInDepot * 1.0f;
        float dimensionFee = (length * width * height) * 0.01f; // optional scaling

        return baseFee + weightFee + timeFee + dimensionFee;
    }

    @Override
    public String toString() {
        return String.format(
                "ParcelID: %s | DaysInDepot: %d | Weight: %.2f | " +
                        "Dimensions: %.2f x %.2f x %.2f | Collected: %b",
                parcelID, daysInDepot, weight, length, width, height, collected
        );
    }
}
