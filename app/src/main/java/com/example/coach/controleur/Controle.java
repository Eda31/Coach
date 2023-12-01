package com.example.coach.controleur;

import android.content.Context;

import com.example.coach.modele.Profil;
import com.example.coach.outils.Serializer;

/**
 * Classe singleton Controle : répond aux attentes de l'activity
 */
public class Controle {
    private static Controle instance;
    private static Profil profil;
    private static String nomFic = "saveprofil"; // Propriété statique pour le nom du fichier de sérialisation
    private static Context context; // Ajout de la propriété contexte
    private Controle() {
        super();
    }

    // Ajout du contexte au constructeur
    public Controle(Context context) {
        super();
        this.context = context;
        recupSerialize(context); // Appel de recupSerialize au moment de la création du Contrôle
    }

    /**
     * Création d'une instance unique de la classe
     * @return l'instance unique
     */
    public static Controle getInstance(Context context) {
        if(instance == null){
            instance = new Controle(context);
            recupSerialize(context); // Appel de recupSerialize avec le contexte
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
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe, Context context) {
        profil = new Profil(poids, taille, age, sexe);
        //Sérialisation du profil
        Serializer.serialize(nomFic, profil, context);
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
        Object objetProfil = Serializer.deSerialize("saveprofil", context);

        // Transtypage en Profil
        if (objetProfil instanceof Profil) {
            profil = (Profil) objetProfil;
        } else {
            profil = null;
        }
    }
}
