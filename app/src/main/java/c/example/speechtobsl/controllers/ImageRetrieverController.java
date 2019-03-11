package c.example.speechtobsl.controllers;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.entities.Image;
import c.example.speechtobsl.models.DatabaseModel;
import c.example.speechtobsl.models.SynonymsModel;
import c.example.speechtobsl.structure_converter.models.TagModel;

/**
 * Converts a BSL sentence into a set of images of BSL signs
 */
public class ImageRetrieverController {

    DatabaseModel db;
    ArrayList<Image> allImages;
    TagModel tagger;

    /**
     * Instantiates a new image retriever controller.
     *
     * @param ctx the context - needed by database
     */
    public ImageRetrieverController(Context ctx) {
        this.db = new DatabaseModel(ctx);
        this.allImages = new ArrayList<>();
    }

    /**
     * Converts a list of English words into a list of BSL images
     *
     * @param BSLSentence   the sentence in written BSL
     * @param tags          the POS tags
     * @param splitSentence the original English text
     * @return the BSL sentence in images
     */
    public ArrayList<Image> getImageSentence(ArrayList<String> BSLSentence, ArrayList<JSONObject> tags, ArrayList<String> splitSentence) {
        this.tagger = new TagModel(tags,splitSentence);
        ArrayList<Image> images = new ArrayList<>();
        if(BSLSentence != null  && BSLSentence.size() > 0) {
            this.allImages = db.getAllImages(BSLSentence);
            for(String word : BSLSentence) {
                images.addAll(this.getSigns(word));
            }
        }
        return images;
    }

    /**
     * Gets the signs required for a given English word
     * Could be the exact sign for that word, a sign for a synonym of that word, or the word fingerspellt
     * @param word
     * @return a list of images to display to sign the given word
     */
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

    /**
     * Gets letters to spell the word in BSL
     * @param word
     * @return list of BSL letters
     */
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
