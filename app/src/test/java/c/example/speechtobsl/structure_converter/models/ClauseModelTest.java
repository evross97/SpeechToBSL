package c.example.speechtobsl.structure_converter.models;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import c.example.speechtobsl.TestUtils;
import c.example.speechtobsl.structure_converter.entities.Clause;

import static org.junit.Assert.*;

public class ClauseModelTest {

    private ArrayList<JSONObject> POSTags;
    private ArrayList<JSONObject> parse;
    private ArrayList<String> sentence;

    private TestUtils utils;

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
    public void createClause() {
        ClauseModel model = new ClauseModel(this.POSTags,this.parse,this.sentence);

        //has nps, vps, question
        ArrayList<String> clauseWords = new ArrayList<>(this.sentence.subList(0,8));
        Clause result1 = model.createClause(clauseWords,0);
        assertEquals(this.utils.c1.toArrayString(),result1.toArrayString());
        //has vps, connector, and prep for verb
        ArrayList<String> clauseWords2 = new ArrayList<>(this.sentence.subList(8,13));
        System.out.println(clauseWords2);
        Clause result2 = model.createClause(clauseWords2,1);
        assertEquals(this.utils.c2.toArrayString(),result2.toArrayString());
        //has nps, incorrect timeframe - should create correct clause with timeframe omitted
        clauseWords.add("tomorrow");
        Clause result3 = model.createClause(clauseWords,2);
        assertEquals(this.utils.c1.toArrayString(),result3.toArrayString());
    }
}
