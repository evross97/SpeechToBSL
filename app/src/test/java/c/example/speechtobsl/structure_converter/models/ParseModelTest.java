package c.example.speechtobsl.structure_converter.models;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

import c.example.speechtobsl.utils.TestUtils;
import c.example.speechtobsl.structure_converter.utils.POS;

public class ParseModelTest {

    private ArrayList<JSONObject> parse;
    private TagModel tagger;
    private ParseModel parseModel;
    private ArrayList<String> sentence;

    private TestUtils utils;

    @Before
    public void setUp() throws Exception {
        this.utils = new TestUtils();
        this.parse = this.utils.parse;
        this.tagger = new TagModel(this.utils.POSTags,this.utils.englishText);
        this.sentence = this.utils.englishText;
        this.parseModel = new ParseModel(this.parse,this.tagger);
    }

    @After
    public void tearDown() throws Exception {
        this.parse.clear();
    }

    @Test
    public void findLinks() {
        ArrayList<String> emptyList = new ArrayList<>();
        //correct word, valid POS
        ArrayList<String> result1 = this.parseModel.findLinks(this.sentence,"what", POS.VRB);
        ArrayList<String> expected1 = new ArrayList<>();
        expected1.add("are");
        expected1.add("run");
        assertEquals(expected1, result1);
        //correct word, invalid POS
        ArrayList<String> result2 = this.parseModel.findLinks(this.sentence,"dogs", POS.QTN);
        assertEquals(emptyList, result2);
        //incorrect word
        ArrayList<String> result3 = this.parseModel.findLinks(this.sentence,"girl", POS.NOUN);
        assertEquals(emptyList, result3);
    }
}
