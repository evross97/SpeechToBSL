package c.example.speechtobsl.structure_converter.models;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

import c.example.speechtobsl.TestUtils;
import c.example.speechtobsl.structure_converter.entities.NounPhrase;

public class NounModelTest {

    private ArrayList<JSONObject> POSTags;
    private ArrayList<JSONObject> parse;
    private ArrayList<String> englishText;

    private TestUtils utils;
    private NounModel model;

    @Before
    public void setUp() throws Exception {
        this.utils = new TestUtils();
        this.POSTags = this.utils.POSTags;
        this.parse = this.utils.parse;
        this.englishText = this.utils.englishText;
        this.model = new NounModel(this.POSTags, this.parse,this.englishText);
    }

    @After
    public void tearDown() throws Exception {
        this.POSTags.clear();
        this.parse.clear();
        this.englishText.clear();
    }

    @Test
    public void createNP() {
        //also tests private getSingular method
        //good noun - plural
        NounPhrase result1 = this.model.createNP("names");
        assertEquals(this.utils.np1.toArrayString(),result1.toArrayString());
        //good noun - plural - with adjectives
        NounPhrase result2 = this.model.createNP("dogs");
        assertEquals(this.utils.np2.toArrayString(), result2.toArrayString());
        //good noun - pronoun
        NounPhrase result3 = this.model.createNP("they");
        assertEquals(this.utils.np3.toArrayString(),result3.toArrayString());
        //bad noun
        NounPhrase result4 = this.model.createNP("girl");
        NounPhrase expected4 = new NounPhrase("girl");
        assertEquals(expected4.toArrayString(), result4.toArrayString());
    }

    @Test
    public void removePreps() {
        //TODO
    }
}
