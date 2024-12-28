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
        return ParcelMap.get(parcelID);
    }

    public  void loadParcelData(String parcelFile){
        try (BufferedReader br = new BufferedReader(new FileReader(parcelFile))){
            String line:
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null){
                if (isFirstLine){
                    isFirstLine = false;
                    continue;
                }
                String [] parts = line.split(",");
                if (parts.length == 4){
                    String parcelID = parts[0].trim();
                    String dimesions = parts[1].trim():
                    float weight = Float.parseFloat(parts[2].trim());
                    int daysInDepot = Integer.parseInt(parts[3].trim());

                    Parcel parcel = new Parcel(parcelID, dimesions, weight, daysInDepot);
                    parcel.addParcel(parcel);
                }
            }
            Log.getInstance().append("Parcel data loaded successfully from " + parcelFile);
        }catch (IOException e){
            Log.getInstance().append("Error reading parcel file: " + e.getMessage());
        }
    }

    public Map<String, Parcel> getParcelMap(){
        return parcelMap;
    }
}
