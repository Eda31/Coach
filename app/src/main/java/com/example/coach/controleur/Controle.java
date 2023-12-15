package com.example.coach.controleur;

import android.content.Context;

import com.example.coach.modele.AccesDistant;
import com.example.coach.modele.Profil;
import com.example.coach.outils.Serializer;
import com.example.coach.vue.MainActivity;

import org.json.JSONObject;

import java.util.Date;

/**
 * Classe singleton Controle : répond aux attentes de l'activity
 */
public class Controle {
    private static Controle instance;
    private static Profil profil;
    private static final String nomFic = "saveprofil"; // Propriété statique pour le nom du fichier de sérialisation
    private static Context context; // Ajout de la propriété contexte
    private Controle() {
        super();
    }
    // private AccesLocal accesLocal;
    private AccesDistant accesDistant;
    // Ajout du contexte au constructeur
    private Controle(Context context) {
        super();
        if (context != null) {
            Controle.context = context;
            accesDistant = AccesDistant.getInstance();
            accesDistant.envoi("dernier", new JSONObject());
        }
        // recupSerialize(context); // Appel de recupSerialize au moment de la création du Contrôle
    }

    /**
     * Création d'une instance unique de la classe
     * @return l'instance unique
     */
    public static Controle getInstance(Context context) {
        if(instance == null){
            instance = new Controle(context);
            //recupSerialize(context); // Appel de recupSerialize avec le contexte
        }
        return instance;
    }

    /**
     * Création du profil par rapport aux informations saisies
     * @param poids;
     * @param taille en cm
     * @param age;
     * @param sexe 1 pour homme, 0 pour femme
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {
        profil = new Profil(poids, taille, age, sexe, new Date());
        //Sérialisation du profil
        //Serializer.serialize(nomFic, profil, context);

        // Ajout du nouveau profil dans la base locale
        // accesLocal = AccesLocal.getInstance(context);
        // accesLocal.ajout(profil); // Utilisation de la méthode ajout pour ajouter le profil
        accesDistant.envoi("enreg", profil.convertToJSONObject());
    }

    /**
     * getter sur le résultat du calcul de l'IMG pour le profil
     * @return img du profil
     */
    public float getImg() {
        if(profil != null) {
            return profil.getImg();
        }else{
            return 0;
        }
    }

    /**
     * getter sur le message correspondant à l'img du profil
     * @return message du profil
     */
    public String getMessage() {
        if(profil != null) {
            return profil.getMessage();
        }else{
            return "";
        }
    }

    /**
     * Récupère la taille du profil
     * @return taille du profil ou null si le profil est null
     */
    public Integer getTaille() {
        if (profil != null) {
            return profil.getTaille();
        } else {
            return null;
        }
    }

    /**
     * Récupère le poids du profil
     * @return poids du profil ou null si le profil est null
     */
    public Integer getPoids() {
        if (profil != null) {
            return profil.getPoids();
        } else {
            return null;
        }
    }

    /**
     * Récupère l'age du profil
     * @return age du profil ou null si le profil est null
     */
    public Integer getAge() {
        if (profil!= null) {
            return profil.getAge();
        } else {
            return null;
        }
    }

    /**
     * Récupère le sexe du profil
     * @return sexe du profil ou null si le profil est null
     */
    public Integer getSexe() {
        if (profil != null) {
            return profil.getSexe();
        } else {
            return null;
        }
    }
    /**
     * Methode privée et statique pour récupérer le profil depuis la sérialisation
     */
    private static void recupSerialize(Context context) {
        // Désérialisation du profil
        Object objetProfil = Serializer.deSerialize(nomFic, context);

        // Transtypage en Profil
        if (objetProfil instanceof Profil) {
            profil = (Profil) objetProfil;
        } else {
            profil = null;
        }
    }

    /**
     *
     * @param profil récupère profil
     */
    public void setProfil(Profil profil) {
        Controle.profil = profil;
        ((MainActivity)context).recupProfil();
    }
}
