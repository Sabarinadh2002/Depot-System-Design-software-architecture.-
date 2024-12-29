import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class ParcelMap {
    private Map <String,Parcel> parcelMap = new HashMap<>();

    public void addParcel(Parcel p){
        parcelMap.put(p.getParcelID(),p);
        Log.getInstance().append("Parcel added: "+ p.getParcelID());
    }
    public Parcel getParcelByID(String parcelID){
        return parcelMap.get(parcelID);
    }

    public void loadParcelData(String parcelFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(parcelFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // If there's a header row, you'd skip it, but let's assume no header
                // If there's an empty line, watch out for parts.length < 6

                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String parcelID = parts[0].trim();
                    int daysInDepot = Integer.parseInt(parts[1].trim());
                    float weight    = Float.parseFloat(parts[2].trim());
                    float length    = Float.parseFloat(parts[3].trim());
                    float width     = Float.parseFloat(parts[4].trim());
                    float height    = Float.parseFloat(parts[5].trim());

                    Parcel parcel = new Parcel(
                            parcelID, daysInDepot, weight,
                            length, width, height
                    );
                    addParcel(parcel);
                } else {
                    Log.getInstance().append(
                            "[WARN] Skipped line (expected 6 columns): " + line
                    );
                }
            }
            Log.getInstance().append("Parcel data loaded successfully from " + parcelFile);
        } catch (IOException e) {
            Log.getInstance().append("Error reading parcel file: " + e.getMessage());
        } catch (NumberFormatException e) {
            Log.getInstance().append("Error parsing numeric fields in parcel file: " + e.getMessage());
        }
    }

    public Map<String, Parcel> getParcelMap(){
        return parcelMap;
    }
    /**
     * Optional: Generate a simple text report of parcels.
     */
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("---- Parcel Report ----\n");
        for (Parcel p : parcelMap.values()) {
            sb.append(p.toString()).append("\n");
        }
        sb.append("-----------------------\n");
        return sb.toString();
    }
}
