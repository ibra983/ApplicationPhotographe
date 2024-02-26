package com.example.appliblogphoto;

public class Commentaire {

    private int id;
    private String commentaire;

    public Commentaire(int id, String commentaire) {
        this.id = id;
        this.commentaire = commentaire;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

}
