package com.example.coach.outils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

/**
 * Classe qui permet de serialiser et deserialiser des objets
 * @author Emds
 *
 */
public class Serializer {

	/**
	 * Serialisation d'un objet
	 * @param filename Nom du fichier de sauvegarde
	 * @param object Objet à sérialiser
	 * @param context Contexte de l'application
	 */
	public static void serialize(String filename, Serializable object, Context context) {
		try {
			FileOutputStream file = context.openFileOutput(filename, Context.MODE_PRIVATE) ;
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(file);
				oos.writeObject(object) ;
				oos.flush() ;
				oos.close() ;
			} catch (IOException e) {
				// erreur de serialisation
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// fichier non trouve
			e.printStackTrace();
		}
	}

	/**
	 * Deserialisation d'un objet
	 * @param filename Nom du fichier de sauvegarde
	 * @param context Contexte de l'application
	 * @return Objet désérialisé
	 */
	public static Object deSerialize(String filename, Context context) {
		try {
			FileInputStream file = context.openFileInput(filename) ;
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(file);
				try {
					Serializable object = (Serializable) ois.readObject();
					ois.close() ;
					return object ;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// fichier non trouve
			e.printStackTrace();
		}
		return null ;
	}

}
