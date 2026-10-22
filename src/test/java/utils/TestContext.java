package utils;

public class TestContext {
    private String email;
    private String password;
    private String token;
    private Integer userId;
    private Integer adId;
    private String adTitle;
    private String adDescription;
    private String adCategory;
    private String adPrice;
    private String adCity;   // ← новое

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getAdId() { return adId; }
    public void setAdId(Integer adId) { this.adId = adId; }

    public String getAdTitle() { return adTitle; }
    public void setAdTitle(String adTitle) { this.adTitle = adTitle; }

    public String getAdDescription() { return adDescription; }
    public void setAdDescription(String adDescription) { this.adDescription = adDescription; }

    public String getAdCategory() { return adCategory; }
    public void setAdCategory(String adCategory) { this.adCategory = adCategory; }

    public String getAdPrice() { return adPrice; }
    public void setAdPrice(String adPrice) { this.adPrice = adPrice; }

    public String getAdCity() { return adCity; }
    public void setAdCity(String adCity) { this.adCity = adCity; }
}