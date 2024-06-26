package com.example.appliblogphoto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe pour la gestion de la base de données de l'application.
 * Elle gère les opérations de création de la base de données, l'ajout d'utilisateurs, d'articles, de commentaires, etc.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Nom de la base de données
    private static final String DATABASE_NAME = "blog_app.db";
    // Version de la base de données. Si vous modifiez le schéma de la base de données, vous devez incrémenter la version.
    private static final int DATABASE_VERSION = 1;

    // Constantes pour la table des utilisateurs
    private static final String TABLE_NAME_USERS = "utilisateurs";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_LOGIN = "login";
    private static final String SQL_CREATE_ENTRIES_USERS =
            "CREATE TABLE " + TABLE_NAME_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_LOGIN + " TEXT )";

    // Constantes pour la table des articles
    private static final String TABLE_NAME_ARTICLES = "articles";
    public static final String COLUMN_ARTICLE_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_IMAGE_URL = "imageUrl";
    public static final String COLUMN_CONTENT = "content";
    private static final String SQL_CREATE_ENTRIES_ARTICLES =
            "CREATE TABLE " + TABLE_NAME_ARTICLES + " (" +
                    COLUMN_ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_IMAGE_URL + " TEXT, " +
                    COLUMN_CONTENT + " TEXT" +
                    ")";

    // Constantes pour la table des commentaires
    private static final String TABLE_NAME_COMMENTS = "commentaires";
    private static final String COLUMN_COMMENTS_ID = "id";
    private static final String COLUMN_COMMENTS = "commentaire";
    private static final String COLUMN_COMMENTS_USER_ID = "user_id";
    private static final String COLUMN_COMMENTS_ARTICLES_ID = "articles_id";
    private static final String SQL_CREATE_ENTRIES_COMMENTS =
            "CREATE TABLE " + TABLE_NAME_COMMENTS + " (" +
                    COLUMN_COMMENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_COMMENTS + " TEXT, " +
                    COLUMN_COMMENTS_USER_ID + " INTEGER, " +
                    COLUMN_COMMENTS_ARTICLES_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_COMMENTS_ARTICLES_ID + ") REFERENCES " +
                    TABLE_NAME_ARTICLES + "(" + COLUMN_ARTICLE_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_COMMENTS_USER_ID + ") REFERENCES " +
                    TABLE_NAME_USERS + "(" + COLUMN_ID + "))";

    /**
     * Constructeur de la classe DatabaseHelper.
     * @param context Contexte de l'application.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création des tables dans la base de données lors de la création de la base
        db.execSQL(SQL_CREATE_ENTRIES_USERS);
        db.execSQL(SQL_CREATE_ENTRIES_COMMENTS);
        db.execSQL(SQL_CREATE_ENTRIES_ARTICLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Méthode appelée lors de la mise à jour de la base de données, non implémentée ici.
    }

    /**
     * Méthode pour ajouter un nouvel utilisateur dans la base de données.
     * @param login Login de l'utilisateur.
     * @param email Email de l'utilisateur.
     * @param password Mot de passe de l'utilisateur.
     */
    public void ajouterNouvelUtilisateur(String login, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGIN, login);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        // Insertion d'une nouvelle ligne dans la table "utilisateurs"
        db.insert(TABLE_NAME_USERS, null, values);
    }

    /**
     * Méthode pour ajouter un commentaire dans la base de données.
     * @param commentaire Contenu du commentaire.
     * @param userID ID de l'utilisateur associé au commentaire.
     * @param articles_id ID de l'article associé au commentaire.
     */
    public void ajouterCommentaire(String commentaire, int userID, int articles_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENTS, commentaire);
        values.put(COLUMN_COMMENTS_USER_ID, userID);
        values.put(COLUMN_COMMENTS_ARTICLES_ID, articles_id);
        // Insertion d'une nouvelle ligne dans la table "commentaires"
        db.insert(TABLE_NAME_COMMENTS, null, values);
    }

    /**
     * Méthode pour obtenir l'ID de l'utilisateur à partir de son nom d'utilisateur et de son mot de passe.
     * @param username Nom d'utilisateur de l'utilisateur.
     * @param password Mot de passe de l'utilisateur.
     * @return L'ID de l'utilisateur, -1 s'il n'est pas trouvé.
     */
    @SuppressLint("Range")
    public int getUserID(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_LOGIN + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME_USERS, columns, selection, selectionArgs, null, null, null);
        int userID = -1;
        if (cursor.moveToFirst()) {
            userID = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        return userID;
    }

    /**
     * Méthode pour ajouter un article dans la base de données.
     * @param title Titre de l'article.
     * @param imageUrl URL de l'image de l'article.
     * @param content Contenu de l'article.
     */
    public void addArticle(String title, String imageUrl, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_IMAGE_URL, imageUrl);
        values.put(COLUMN_CONTENT, content);
        db.insert(TABLE_NAME_ARTICLES, null, values);
        db.close();
    }

    /**
     * Méthode pour récupérer tous les articles de la base de données.
     * @return Liste des articles.
     */
    public List<Article> getAllArticles() {
        List<Article> articlesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_ARTICLES, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ARTICLE_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String imageUrl = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL));
                String content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
                Article article = new Article(id, title, imageUrl, content);
                articlesList.add(article);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return articlesList;
    }

    /**
     * Méthode pour récupérer tous les commentaires associés à un article spécifique.
     * @param articleID ID de l'article.
     * @return Liste des commentaires de l'article.
     */
    public List<Commentaire> getAllCommentaires(int articleID) {
        List<Commentaire> commentairesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_COMMENTS + " WHERE " + COLUMN_COMMENTS_ARTICLES_ID + " = ?", new String[]{String.valueOf(articleID)});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_COMMENTS_ID));
                String commentaireText = cursor.getString(cursor.getColumnIndex(COLUMN_COMMENTS));
                Commentaire commentaire = new Commentaire(id, commentaireText);
                commentairesList.add(commentaire);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return commentairesList;
    }
}

