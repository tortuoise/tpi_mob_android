package biz.stratadigm.tpi.entity.vo;

public class ThaliVO {
    private Long id;
    private String name;
    private String region;
    private String imageUrl;
    private Long venueId;
    private Integer price;

    public ThaliVO(String name, String region, String imageUrl, Integer price){
        if (name == "") this.name = "Standard Thali";
        else this.name = name;
        this.region = region;
        this.imageUrl = imageUrl;
        this.price = price;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
