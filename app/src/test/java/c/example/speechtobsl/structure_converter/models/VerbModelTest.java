package c.example.speechtobsl.structure_converter.models;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

import c.example.speechtobsl.TestUtils;
import c.example.speechtobsl.structure_converter.entities.VerbPhrase;

public class VerbModelTest {

    private TestUtils utils;
    private ArrayList<JSONObject> POSTags;
    private ArrayList<JSONObject> parse;
    private ArrayList<String> sentence;

    @Before
    public void setUp() throws Exception {
        this.utils = new TestUtils();
        this.POSTags = this.utils.POSTags;
        this.parse = this.utils.parse;
        this.sentence = this.utils.englishText;
    }

    @After
    public void tearDown() throws Exception {
        this.POSTags.clear();
        this.parse.clear();
        this.sentence.clear();
    }

    @Test
    public void createVP() {
        VerbModel model = new VerbModel(this.POSTags,this.parse,this.sentence);
        //good verb, prep
        VerbPhrase result1 = model.createVP("of", true);
        assertEquals(this.utils.vp1.toArrayString(), result1.toArrayString());
        //good verb, not prep
        VerbPhrase result2 = model.createVP("run", false);
        assertEquals(this.utils.vp2.toArrayString(), result2.toArrayString());
        //bad verb
        VerbPhrase result3 = model.createVP("walk", false);
        VerbPhrase expected3 = new VerbPhrase("walk");
        assertEquals(expected3.toArrayString(),result3.toArrayString());
    }
}
