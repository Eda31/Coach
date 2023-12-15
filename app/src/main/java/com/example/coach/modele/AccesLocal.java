package com.example.coach.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coach.outils.MesOutils;
import com.example.coach.outils.MySQLiteOpenHelper;

import java.util.Date;

public class AccesLocal extends MySQLiteOpenHelper {
    // Propriétés pour mémoriser le nom de la base, la version et l'accès à la base
    private static final String nomBase = "bdCoach.sqlite";
    private static final int versionBase = 1;
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private static AccesLocal instance;
    private SQLiteDatabase accesBD;

    // Constructeur
    private AccesLocal(Context context) {
        super(context, "bdCoach.sqlite",1);
        // Valorise la propriété accesBD avec une instance de MySQLiteOpenHelper
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context, "bdCoach.sqlite", 1);
        accesBD = mySQLiteOpenHelper.getWritableDatabase();
    }

    // Méthode publique et statique pour obtenir l'instance unique de la classe
    public static AccesLocal getInstance(Context context) {
        // Crée une nouvelle instance uniquement si instance est null
        if (instance == null) {
            instance = new AccesLocal(context);
        }
        return instance;
    }

    // Méthode pour ajouter un profil à la base de données
    public void ajout(Profil profil) {
        // Utilisez ContentValues pour insérer les valeurs dans la base de données de manière sécurisée
        ContentValues values = new ContentValues();
        values.put("datemesure", profil.getDateMesure().toString());
        values.put("poids", profil.getPoids());
        values.put("taille", profil.getTaille());
        values.put("age", profil.getAge());
        values.put("sexe", profil.getSexe());

        // Obtenez une nouvelle instance de SQLiteDatabase à partir de votre MySQLiteOpenHelper
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();

        // Insérez la nouvelle ligne dans la table "profil"
        db.insert("profil", null, values);

        // Fermez la connexion après l'insertion
        db.close();
    }

    // Ferme la connexion
    public void fermeture() {
        if (accesBD != null && accesBD.isOpen()) {
            accesBD.close();
        }
    }

    public Profil recupDernier() {
        SQLiteDatabase bd = accesBD;

        // Déclaration d'un objet profil initialisé à null
        Profil profil = null;

        // Valorisation de la propriété bd
        bd = mySQLiteOpenHelper.getReadableDatabase();

        // Création de la requête SELECT
        String req = "SELECT * FROM profil ORDER BY datemesure DESC LIMIT 1";

        // Création et exécution du curseur
        Cursor curseur = bd.rawQuery(req, null);

        // Positionnement sur la dernière ligne du curseur
        if (curseur.moveToLast()) {
            // Récupération des informations de la ligne du curseur
            String dateMesureStr = curseur.getString(0);
            int poids = curseur.getInt(1);
            int taille = curseur.getInt(2);
            int age = curseur.getInt(3);
            int sexe = curseur.getInt(4);

            // Conversion de la date (pour le moment, utilisation de la date du jour)
            Date dateMesure = MesOutils.convertStringToDate(dateMesureStr, "yyyy-MM-dd HH:mm:ss");

            // Valorisation de l'objet profil
            profil = new Profil(poids, taille, age, sexe, dateMesure);
        }
        // Fermeture du curseur
        curseur.close();

        // Fermeture de la base de données
        bd.close();

        // Retour de l'objet profil
        return profil;
    }
}
