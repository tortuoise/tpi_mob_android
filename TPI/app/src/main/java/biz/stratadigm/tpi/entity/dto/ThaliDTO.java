package biz.stratadigm.tpi.entity.dto;

/**
 * Created by tamara on 12/19/16.
 */

public class ThaliDTO {
    public String id;
    public String name;
    public String submitted;
    public String target;
    public String limited;
    public String region;
    public String price;
    public String image;
    public String userid;
    public String venue;
    public String verified;
    public String accepted;

    public ThaliDTO(String id, String name, String submitted, String target, String limited,
                    String region, String price, String image, String userid, String venue,
                    String verified, String accepted) {
        this.id = id;
        this.name = name;
        this.submitted = submitted;
        this.target = target;
        this.limited = limited;
        this.region = region;
        this.price = price;
        this.image = image;
        this.userid = userid;
        this.venue = venue;
        this.verified = verified;
        this.accepted = accepted;
    }
}
