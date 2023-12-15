package com.example.coach.modele;

import android.util.Log;

import com.example.coach.controleur.Controle;
import com.example.coach.outils.AccesHTTP;
import com.example.coach.outils.AsyncResponse;
import com.example.coach.outils.MesOutils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class AccesDistant implements AsyncResponse {
    // Constance
    public static final String SERVERADDR = "http://192.168.1.65/coach/serveurcoach.php";
    private static AccesDistant instance = null;
    private final Controle controle;
    // Constructeur privé
    private AccesDistant() {
        controle = Controle.getInstance(null);
    }

    // Méthode getInstance pour obtenir l'instance unique
    public static AccesDistant getInstance() {
        if (instance == null) {
            instance = new AccesDistant();
        }
        return instance;
    }
    //Méthode envoi
    public void envoi(String operation, JSONObject lesDonneesJSON) {
        // Instancier un objet AccesHTTP
        AccesHTTP accesDonnees = new AccesHTTP();

        // Définir le délégué pour la réponse asynchrone
        accesDonnees.delegate = this;

        // Ajouter les paramètres nécessaires à la requête
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesDonneesJSON.toString());

        // Exécuter la tâche asynchrone pour envoyer la requête au serveur
        accesDonnees.execute(SERVERADDR);
    }
    //Méthode processFinish
    public void processFinish(String output) {
        Log.d("serveur", "************" + output);
        try {
            JSONObject retour = new JSONObject(output);

            String code = retour.getString("code");
            String message = retour.getString("message");
            String result = retour.getString("result");

            if (!"200".equals(code)) {
                Log.d("erreur", "************ retour serveur code="+code+" result="+result);
            } else {
                Log.d("ok", "************ retour serveur message="+message+" result="+result);
                // Construire l'objet JSONObject à partir de la chaîne "result"
                JSONObject info = new JSONObject(result);

                // Récupérer les différentes parties du profil
                String dateMesureString = info.getString("datemesure");
                Date dateMesure = MesOutils.convertStringToDate(dateMesureString, "yyyy-MM-dd HH:mm:ss");
                Integer poids = info.getInt("poids");
                Integer taille = info.getInt("taille");
                Integer age = info.getInt("age");
                Integer sexe = info.getInt("sexe");

                // Créer un objet Profil avec les informations récupérées
                Profil profil = new Profil(poids, taille, age, sexe, dateMesure);

                // Appeler la méthode setProfil de la classe Controle
                controle.setProfil(profil);
            }
        } catch (JSONException e) {
            Log.d("erreur", "************ output n’est pas au format JSON");
        }
    }
}
