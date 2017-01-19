package biz.stratadigm.tpi.entity.dto;

/**
 * Created by tamara on 12/19/16.
 */

public class VenueDTO {
    public String id;
    public String name;
    public String submitted;
    public String lat;
    public String lnh;
    public String thalis;

    public VenueDTO(String id, String name, String submitted, String lat, String lnh, String thalis) {
        this.id = id;
        this.name = name;
        this.submitted = submitted;
        this.lat = lat;
        this.lnh = lnh;
        this.thalis = thalis;
    }
}
