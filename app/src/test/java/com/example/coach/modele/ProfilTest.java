package com.example.coach.modele;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProfilTest {

    // création d’un profil : femme de 67kg, 1m65, 35 ans
    private final Profil profil = new Profil(67, 165, 35, 0);

    @Test
    public void getImg() {
        // résultat de l’img correspondant
        float img = (float) 32.2;
        assertEquals(img, profil.getImg(), (float)0.1);
    }

    @Test
    public void getMessage() {
        // message correspondant
        String message = "trop élevé";
        assertEquals(message, profil.getMessage());
    }
}