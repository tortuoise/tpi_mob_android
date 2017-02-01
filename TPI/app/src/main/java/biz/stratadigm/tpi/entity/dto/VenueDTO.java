package biz.stratadigm.tpi.entity.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.ArrayList;

public class VenueDTO {

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("submitted")
    private String submitted;

    @SerializedName("loc")
    private Loc loc;

    @SerializedName("thalis")
    private ArrayList<Long> thalis;

    public VenueDTO() {
    }

    @Deprecated
    public VenueDTO(Long id, String name, String submitted, Float lat, Float lng, ArrayList<Long> thalis) {
        this.id = id;
        this.name = name;
        this.submitted = submitted;
        //this.lat = lat;
        //this.lng = lng;
        this.loc = new VenueDTO.Loc();
        this.loc.setLat(lat);
        this.loc.setLng (lng);
        this.thalis = thalis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setId(String name) {
        this.name = name;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    public ArrayList<Long> getThalis() {
        return thalis;
    }

    public void setThalis(ArrayList<Long> thalis) {
        this.thalis = thalis;
    }

    public static class Loc {
        @SerializedName("lat")
        private Float lat;

        @SerializedName("lng")
        private Float lng;
  
        public Float getLat() {
             return lat;
        }
  
        public void setLat(Float lat) { 
             this.lat = lat;
        }

        public Float getLng() {
             return lng;
        }

        public void setLng(Float lng) { 
             this.lng = lng;
        }
    }

}
