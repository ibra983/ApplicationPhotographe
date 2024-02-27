package com.example.appliblogphoto;

/**
 * Classe représentant un commentaire d'article.
 */
public class Commentaire {

    private int id;
    private String commentaire;

    /**
     * Constructeur de la classe Commentaire.
     * @param id L'identifiant unique du commentaire.
     * @param commentaire Le contenu du commentaire.
     */
    public Commentaire(int id, String commentaire) {
        this.id = id;
        this.commentaire = commentaire;
    }

    // Getters et Setters pour accéder et modifier les attributs du commentaire

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
