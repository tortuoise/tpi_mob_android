package biz.stratadigm.tpi.entity.vo;

public class VenueVO {
    private Long id;
    private String name;
    private Integer thalis;
    private Loc loc;

    public VenueVO(Long id, String name, Integer thalis, Float lat, Float lng) {
        this.id = id;
        this.name = name;
        this.thalis = thalis;
        this.loc = new VenueVO.Loc();
        this.loc.setLat(lat);
        this.loc.setLng (lng);
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

    public void setName(String name) {
        this.name = name;
    }

    public Integer getThalis() {
        return thalis;
    }

    public void setThalis(Integer thalis) {
        this.thalis = thalis;
    }

    public Loc getLoc() {
        return loc;
    }
 
    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    public static class Loc {
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
