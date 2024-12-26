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



    public Map<String, Parcel> getParcelMap(){
        return parcelMap;
    }
}
