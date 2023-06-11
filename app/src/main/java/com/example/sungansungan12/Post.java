package com.example.sungansungan12;
public class Post {
    private String name;
    private String description;
    private String price;
    private String available;
    private String imageUrl;
    private String userId; // 올린 사람의 ID 변수 추가

    public Post() {
        // 기본 생성자 (Firebase Realtime Database에서 필요)
    }

    public Post(String name, String description, String price, String available, String imageUrl, String userId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    // Getter 메서드
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getAvailable() {
        return available;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    // Setter 메서드
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
