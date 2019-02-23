package c.example.speechtobsl.controllers;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.entities.Image;
import c.example.speechtobsl.models.DatabaseModel;
import c.example.speechtobsl.models.SynonymsModel;
import c.example.speechtobsl.structure_converter.models.TagModel;

public class ImageRetrieverController {

    DatabaseModel db;
    ArrayList<Image> allImages;
    TagModel tagger;

    public ImageRetrieverController(Context ctx) {
        this.db = new DatabaseModel(ctx);
        this.allImages = new ArrayList<>();
    }

    public ArrayList<Image> getImageSentence(ArrayList<String> BSLSentence, ArrayList<JSONObject> tags, ArrayList<String> splitSentence) {
        this.tagger = new TagModel(tags,splitSentence);
        ArrayList<Image> images = new ArrayList<>();
        this.allImages = db.getAllImages(BSLSentence);
        for(String word : BSLSentence) {
            images.addAll(this.getSigns(word));
        }
        return images;
    }

    private ArrayList<Image> getSigns(String word) {
        ArrayList<Image> finalSigns = new ArrayList<>();
        Image sign = db.getDBSignForWord(this.allImages, word);
        Boolean signFound = sign.getImage() != null;
        if(!signFound){
            SynonymsModel synClient = new SynonymsModel(this.tagger);
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
                finalSigns = this.fingerSpellWord(word);
            }
        } else {
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
