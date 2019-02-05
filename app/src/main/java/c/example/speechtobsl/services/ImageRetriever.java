package c.example.speechtobsl.services;

import android.content.Context;

import java.util.ArrayList;

import c.example.speechtobsl.utils.Image;

public class ImageRetriever {

    Context appCtx;
    SignDatabase db;

    public ImageRetriever(Context ctx) {
        this.appCtx = ctx;
        this.db = new SignDatabase(this.appCtx);
    }

    public ArrayList<Image> getImageSentence(ArrayList<String> sentence) {
        ArrayList<Image> images = new ArrayList<>();
        for(String word : sentence) {
            System.out.println(word);
            images.addAll(this.getSigns(word));
        }
        return images;
    }

    public ArrayList<Image> getSigns(String word) {
        ArrayList<Image> finalSigns = new ArrayList<>();
        Image sign = db.queryDatabase(word);
        Boolean signFound = sign.getImage() != null;
        if(!signFound){
            System.out.println("Need wordnet");
            SynonymsClient synClient = new SynonymsClient();
            ArrayList<String> synonyms = synClient.getSynonyms(word);
            for(String syn : synonyms) {
                sign = db.queryDatabase(syn);
                signFound = sign.getImage() != null;
                if(signFound) {
                    sign.setDesc(word.toUpperCase());
                    finalSigns.add(sign);
                    break;
                }
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
        String[] letters = word.split("");
        for(String letter : letters) {
            if(!letter.equals("")) {
                Image sign = db.queryDatabase(letter);
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
