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
    private static final int DATABASE_VERSION = 2;
    // Nom de la table ( ici utilisateurs )
    private static final String TABLE_NAME_USERS = "utilisateurs";
    // Colonnes de la table utilisateurs
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_LOGIN = "login";
    // Requête de création de la table
    private static final String SQL_CREATE_ENTRIES_USERS =
            "CREATE TABLE " + TABLE_NAME_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_LOGIN + " TEXT )";





    // Nom de la table ( ici la table commentaires )
    private static final String TABLE_NAME_COMMENTS = "commentaires";
    // Colonnes de la table commentaires
    private static final String COLUMN_COMMENTS_ID = "id";
    private static final String COLUMN_COMMENTS = "commentaire";


    // Requête de création de la table
    private static final String SQL_CREATE_ENTRIES_COMMENTS =
            "CREATE TABLE " + TABLE_NAME_COMMENTS + " (" +
                    COLUMN_COMMENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_COMMENTS + " TEXT )";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_USERS);
        db.execSQL(SQL_CREATE_ENTRIES_COMMENTS);
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
    public void ajouterCommentaire(String commentaire) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENTS, commentaire);
        // Insertion d'une nouvelle ligne dans la table "commentaires"
        db.insert(TABLE_NAME_COMMENTS, null, values);
    }



    // Méthode pour récuperer le login de l'utilisateur connecté de la session cours.
    @SuppressLint("Range")
    public String getLoginUtilisateur(String login, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_LOGIN};
        String selection = COLUMN_LOGIN + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {login, password};
        Cursor cursor = db.query(TABLE_NAME_USERS, columns, selection, selectionArgs, null, null, null);
        String utilisateurLogin = null;
        if (cursor.moveToFirst()) {
            utilisateurLogin = cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN));
        }
        cursor.close();
        return utilisateurLogin;
    }



}

