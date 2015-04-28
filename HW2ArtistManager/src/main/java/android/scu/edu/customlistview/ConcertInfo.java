package android.scu.edu.customlistview;

import android.util.Log;
import java.io.Serializable;

public class ConcertInfo implements  Serializable{
    private String artistName;
    private String location;
    private String phone;

    public ConcertInfo(String records){
        super();
        String[] localTemp = records.split(",");
        this.artistName = localTemp[0];
        this.location = localTemp[1];
        this.phone = localTemp[2];
        //Log.e("Wenyi", "ConcertInfo Constructor:" + localTemp[0] + ", " + localTemp[1] + ", " + localTemp[2]);
    }
    public String getArtistName() {
        return this.artistName;
    }
    public String getLocation() {
        return this.location;
    }
    public String getPhone() {
        return this.phone;
    }
}
