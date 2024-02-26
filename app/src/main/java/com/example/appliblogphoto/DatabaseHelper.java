package com.example.appliblogphoto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import java.util.List;
import java.util.ArrayList;



public class DatabaseHelper extends SQLiteOpenHelper {


    // Nom de la base de données
    private static final String DATABASE_NAME = "blog_app.db";
    // Version de la base de données. Si vous modifiez le schéma de la base de données, vous devez incrémenter la version.
    private static final int DATABASE_VERSION = 1;
    // Nom de la table ( ici utilisateurs )
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



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_USERS);
        db.execSQL(SQL_CREATE_ENTRIES_COMMENTS);
        db.execSQL(SQL_CREATE_ENTRIES_ARTICLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // Méthode pour ajouter un user dans la BDD ( login, mail, mot de passe )
    public void ajouterNouvelUtilisateur(String login, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGIN, login);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        // Insertion d'une nouvelle ligne dans la table "utilisateurs"
        db.insert(TABLE_NAME_USERS, null, values);
    }


    // Méthode pour vérifier si l'utilisateur est bien dispo dans la BDD
    // donc autorise connexion si c'est le cas
    // public boolean verifierUtilisateur(String login, String password) {
        // SQLiteDatabase db = this.getReadableDatabase();
        // String[] columns = {COLUMN_ID};
        // String selection = COLUMN_LOGIN + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        // String[] selectionArgs = {login, password};
        // String limit = "1";
        //  Cursor cursor = db.query(TABLE_NAME_USERS, columns, selection, selectionArgs, null, null, null, limit);
        //   boolean utilisateurExiste = cursor.moveToFirst();
        //   cursor.close();
    //    return utilisateurExiste;
    //}


    // Méthode pour ajouter un commentaire
    public void ajouterCommentaire(String commentaire, int userID, int articles_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENTS, commentaire);
        values.put(COLUMN_COMMENTS_USER_ID, userID);
        values.put(COLUMN_COMMENTS_ARTICLES_ID, articles_id);
        // Insertion d'une nouvelle ligne dans la table "commentaires"
        db.insert(TABLE_NAME_COMMENTS, null, values);
    }



    // Méthode pour récuperer le login de l'utilisateur connecté de la session cours.
    //@SuppressLint("Range")
    //public String getLoginUtilisateur(String login, String password) {
    //   SQLiteDatabase db = this.getReadableDatabase();
    //  String[] columns = {COLUMN_LOGIN};
    //  String selection = COLUMN_LOGIN + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
    //  String[] selectionArgs = {login, password};
    //  Cursor cursor = db.query(TABLE_NAME_USERS, columns, selection, selectionArgs, null, null, null);
    //  String utilisateurLogin = null;
    //  if (cursor.moveToFirst()) {
    //      utilisateurLogin = cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN));
    //  }
    //  cursor.close();
    //  return utilisateurLogin;
    //  }


    @SuppressLint("Range")
    public int getUserID(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID}; // Supposons que COLUMN_ID est la colonne contenant l'identifiant de l'utilisateur
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


    // Méthode pour insérer un article
    public void addArticle(String title, String imageUrl, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_IMAGE_URL, imageUrl);
        values.put(COLUMN_CONTENT, content);
        db.insert(TABLE_NAME_ARTICLES, null, values);
        db.close();
    }


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

}

