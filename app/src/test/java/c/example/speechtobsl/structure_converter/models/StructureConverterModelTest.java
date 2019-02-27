package c.example.speechtobsl.structure_converter.models;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import c.example.speechtobsl.TestUtils;

import static org.junit.Assert.*;

public class StructureConverterModelTest {

    TestUtils utils;
    JSONObject parsedText;
    String text;

    @Before
    public void setUp() throws Exception {
        this.utils = new TestUtils();
        this.parsedText = this.utils.parsedText;
        this.text = this.utils.englishString;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getPOSTags() {
        StructureConverterModel model = new StructureConverterModel();
        //haven't called convertSentence yet so no POS tags have been created
        ArrayList<JSONObject> posTags = model.getPOSTags();
        assertEquals(0, posTags.size());

        model.convertSentence(this.parsedText, this.text);
        posTags = model.getPOSTags();
        assertEquals(this.utils.POSTags.size(), posTags.size());

    }

    @Test
    public void convertSentence() {
        StructureConverterModel model = new StructureConverterModel();

        //valid
        ArrayList<String> result1 = model.convertSentence(this.parsedText, this.text);
        assertEquals(this.utils.parsedSentence,result1);

        //invalid parse, valid text
        JSONObject fakeParse = new JSONObject();
        ArrayList<String> result2 = model.convertSentence(fakeParse,text);
        assertEquals(new ArrayList<String>(), result2);

        //valid parse, invalid text
        String invalidText = "The boy saw the girl with the telescope";
        ArrayList<String> result3 = model.convertSentence(this.parsedText,invalidText);
        assertEquals(new ArrayList<String>(), result3);
    }
}
