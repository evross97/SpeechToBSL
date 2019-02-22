package c.example.speechtobsl.controllers;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import c.example.speechtobsl.entities.Image;
import c.example.speechtobsl.models.DatabaseModel;
import c.example.speechtobsl.models.SynonymsModel;

public class ImageRetrieverController {

    DatabaseModel db;
    ArrayList<Image> allImages;

    public ImageRetrieverController(Context ctx) {
        this.db = new DatabaseModel(ctx);
        this.allImages = new ArrayList<>();
    }

    public ArrayList<Image> getImageSentence(ArrayList<String> sentence) {
        ArrayList<Image> images = new ArrayList<>();
        this.allImages = db.getAllImages(sentence);
        for(String word : sentence) {
            images.addAll(this.getSigns(word));
        }
        return images;
    }

    private ArrayList<Image> getSigns(String word) {
        ArrayList<Image> finalSigns = new ArrayList<>();
        Image sign = db.getDBSignForWord(this.allImages, word);
        Boolean signFound = sign.getImage() != null;
        if(!signFound){
            System.out.println("Need wordnet");
            SynonymsModel synClient = new SynonymsModel();
            ArrayList<String> synonyms = synClient.getSynonyms(word);
            if(synonyms.size() > 0) {
                ArrayList<Image> synSigns = db.getAllImages(synonyms);
                for(String syn : synonyms) {
                    sign = db.getDBSignForWord(synSigns, syn);
                    signFound = sign.getImage() != null;
                    if(signFound) {
                        sign.setDesc(word.toUpperCase());
                        finalSigns.add(sign);
                        break;
                    }
                }
            } else {
                signFound = false;
            }
            if(!signFound) {
                System.out.println("Need fingerspelling");
                finalSigns = this.fingerSpellWord(word);
            }
        } else {
            System.out.println("First time");
            finalSigns.add(sign);
        }
        return finalSigns;
    }

    private ArrayList<Image> fingerSpellWord(String word) {
        ArrayList<Image> letterSigns = new ArrayList<>();
        String[] lettersTemp = word.split("");
        ArrayList<String> letters = new ArrayList<>(Arrays.asList(lettersTemp));
        ArrayList<Image> allLetters = db.getAllImages(letters);
        for(String letter : letters) {
            if(!letter.equals("")) {
                Image sign = db.getDBSignForWord(allLetters, letter);
                sign.setDesc(word.toUpperCase());
                letterSigns.add(sign);
                Image emptySign = new Image(null," ");
                letterSigns.add(emptySign);
            }
        }
        Image emptyEndSign = new Image(null,"endWord");
        letterSigns.add(emptyEndSign);
        return letterSigns;
    }
}
