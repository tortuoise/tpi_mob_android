package biz.stratadigm.tpi.entity.vo;

public class VenueVO {
    private String name;
    private Integer thalis;
    private Location location;

    public VenueVO(String name, Integer thalis, Float lat, Float lng) {
        this.name = name;
        this.thalis = thalis;
        this.location = new VenueVO.Location();
        this.location.setLat(lat);
        this.location.setLng (lng);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getThalis() {
        return thalis;
    }

    public void setThalis(Integer thalis) {
        this.thalis = thalis;
    }

    public Location getLocation() {
        return location;
    }
 
    public void setLocation(Location location) {
        this.location = location;
    }

    public static class Location {
        private Float lat;

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
