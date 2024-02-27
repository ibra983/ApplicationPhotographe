package com.example.appliblogphoto;

public class Article {
    private int id;
    private String title;
    private String imageUrl;
    private String content;

    // Constructeur de la classe Article prenant en paramètres les différentes informations d'un article
    public Article(int id, String title, String imageUrl, String content) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.content = content;
    }

    // Getters et Setters pour accéder et modifier les attributs de l'article

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
