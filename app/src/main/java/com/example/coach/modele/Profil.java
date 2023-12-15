package com.example.coach.modele;

import android.util.Log;

import com.example.coach.outils.MesOutils;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe métier Profil
 * contient les informations du profil
 */
public class Profil implements Serializable {

    // constantes
    private static final long serialVersionUID = 1L; // numéro de version pour la sérialisation
    private static final Integer minFemme = 15; // maigre si en dessous
    private static final Integer maxFemme = 30; // gros si au dessus
    private static final Integer minHomme = 10; // maigre si en dessous
    private static final Integer maxHomme = 25; // gros si au dessus

    private final Integer poids;
    private final Integer taille;
    private final Integer age;
    private final Integer sexe;
    private float img = 0;
    private String message = "";
    private final Date dateMesure;

    /**
     * Constructeur : valorise directement les proriétés poids, taille, age, sexe
     * @param poids;
     * @param taille;
     * @param age;
     * @param sexe 1 pour homme, 0 pour femme
     * @param dateMesure la date de mesure
     */
    public Profil(Integer poids, Integer taille, Integer age, Integer sexe, Date dateMesure) {
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.dateMesure = new Date(); // Initialise la dateMesure avec la date actuelle
    }

    public Integer getPoids() {
        return poids;
    }

    public Integer getTaille() {
        return taille;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getSexe() {
        return sexe;
    }

    public Date getDateMesure() { return dateMesure; }

    /**
     * Retourne img après l'avoir calculé s'il est vide
     * @return img
     */
    public float getImg() {
        if(img == 0) {
            float tailleCm = ((float)taille)/100;
            img = (float)((1.2 * poids/(tailleCm*tailleCm)) + (0.23 * age) - (10.83 * sexe) - 5.4);
        }
        return img;
    }

    /**
     * retourne le message correspondant à l'img
     * @return message "normal", "trop faible", "trop élevé"
     */
    public String getMessage() {
        if(message.equals("")){
            message = "normal";
            Integer min = minFemme, max = maxFemme;
            if(sexe == 1){
                min = minHomme;
                max = maxHomme;
            }
            img = getImg();
            if(img<min){
                message = "trop faible";
            }else{
                if(img>max){
                    message = "trop élevé";
                }
            }
        }
        return message;
    }

    /**
     * Convertit le profil en objet JSONObject
     * @return JSONObject représentant le profil
     */
    public JSONObject convertToJSONObject() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("dateMesure", MesOutils.convertDateToString(dateMesure));
            jsonObject.put("poids", poids);
            jsonObject.put("taille", taille);
            jsonObject.put("age", age);
            jsonObject.put("sexe", sexe);
        } catch (Exception e) {
            Log.d("erreur", "Erreur lors de la conversion en JSONObject");
        }
        return jsonObject;
    }
}
