package c.example.speechtobsl.structure_converter.models;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;


import c.example.speechtobsl.utils.TestUtils;
import c.example.speechtobsl.structure_converter.utils.POS;

public class TagModelTest {

    private ArrayList<JSONObject> POSTags;
    private ArrayList<String> text;
    private TestUtils utils;
    private TagModel tagger;

    @Before
    public void setUp() throws Exception {
        this.utils = new TestUtils();
        this.POSTags = utils.POSTags;
        this.POSTags.add(this.utils.obj14);
        this.text = utils.englishText;
        this.tagger = new TagModel(this.POSTags,this.text);
    }

    @After
    public void tearDown() throws Exception {
        this.POSTags.clear();
        this.text.clear();
    }

    @Test
    public void getExactTag() {
        //good word, original true
        JSONObject result1 = tagger.getExactTag("the", true);
        assertEquals(utils.obj3,result1);
        //good word, original false
        JSONObject result2 = tagger.getExactTag("name", false);
        assertEquals(utils.obj4,result2);
        //bad word
        JSONObject result3 = tagger.getExactTag("girl", true);
        assertEquals(0, result3.length());
    }

    @Test
    public void getGeneralTag() {
        POS result1 = tagger.getGeneralTag("yesterday", true);
        assertEquals(POS.TIM,result1);
        POS result2 = tagger.getGeneralTag("and", true);
        assertEquals(POS.CONN,result2);
        POS result3 = tagger.getGeneralTag("the", true);
        assertEquals(POS.DET,result3);
        POS result4 = tagger.getGeneralTag("big", true);
        assertEquals(POS.ADJ,result4);
        POS result5 = tagger.getGeneralTag("of", true);
        assertEquals(POS.PREP,result5);
        POS result6 = tagger.getGeneralTag("could", true);
        assertEquals(POS.MDL,result6);
        POS result7 = tagger.getGeneralTag("dog", false);
        assertEquals(POS.NOUN,result7);
        POS result8 = tagger.getGeneralTag("quickly", true);
        assertEquals(POS.ADV,result8);
        POS result9 = tagger.getGeneralTag("run", true);
        assertEquals(POS.VRB,result9);
        POS result10 = tagger.getGeneralTag("what", true);
        assertEquals(POS.QTN,result10);

        //word doesn't exist
        POS result11 = tagger.getGeneralTag("boy", true);
        assertEquals(POS.NA,result11);

    }
}
