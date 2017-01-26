package biz.stratadigm.tpi.entity.dto;

import com.google.gson.annotations.SerializedName;

public class ThaliDTO {
    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("submitted")
    private String submitted;

    @SerializedName("target")
    private String target;

    @SerializedName("limited")
    private Boolean limited;

    @SerializedName("region")
    private String region;

    @SerializedName("price")
    private Integer price;

    @SerializedName("image")
    private String image;

    @SerializedName("userid")
    private Long userId;

    @SerializedName("venue")
    private Long venue;

    @SerializedName("verified")
    private Boolean verified;

    @SerializedName("accepted")
    private Boolean accepted;

    public ThaliDTO() {
    }

    @Deprecated
    public ThaliDTO(String id, String name, String submitted, String target, String limited,
                    String region, String price, String image, String userid, String venue,
                    String verified, String accepted) {
        this.setId(Long.valueOf(id));
        this.setName(name);
        this.setSubmitted(submitted);
        this.setTarget(target);
        this.setLimited(Boolean.valueOf(limited));
        this.setRegion(region);
        this.setPrice(Integer.valueOf(price));
        this.setImage(image);
        this.setUserId(Long.valueOf(userid));
        this.setVenue(Long.valueOf(venue));
        this.setVerified(Boolean.valueOf(verified));
        this.setAccepted(Boolean.valueOf(accepted));
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

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean getLimited() {
        return limited;
    }

    public void setLimited(Boolean limited) {
        this.limited = limited;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVenue() {
        return venue;
    }

    public void setVenue(Long venue) {
        this.venue = venue;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public static class Media {

        @SerializedName("url")
        private String url;

        @SerializedName("size")
        private Size size;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Size getSize() {
            return size;
        }

        public void setSize(Size size) {
            this.size = size;
        }

        public static class Size {
            @SerializedName("w")
            private int w;

            @SerializedName("h")
            private int h;
        }
    }
}
