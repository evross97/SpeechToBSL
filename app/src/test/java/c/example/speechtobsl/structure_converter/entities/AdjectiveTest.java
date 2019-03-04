package c.example.speechtobsl.structure_converter.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AdjectiveTest {

    private Adjective adj1;
    private Adjective adj2;
    private Adjective adj3;
    private ArrayList<String> adverbs;

    @Before
    public void setUp() throws Exception {
        this.adverbs = new ArrayList<>();
        this.adverbs.add("quickly");
        this.adverbs.add("carefully");
        this.adj1 = new Adjective("big", adverbs);
        this.adj2 = new Adjective("small", new ArrayList<>());
        this.adj3 = new Adjective("", new ArrayList<>());
    }

    @After
    public void tearDown() throws Exception {
        this.adverbs.clear();
    }

    @Test
    public void toArrayString() {
        String[] exp1 = new String[]{"quickly", "carefully", "big"};
        ArrayList<String> expected1 = new ArrayList<>(Arrays.asList(exp1));
        ArrayList<String> expected2 = new ArrayList<>();
        expected2.add("small");
        ArrayList<String> result1 = this.adj1.toArrayString();
        ArrayList<String> result2 = this.adj2.toArrayString();
        assertEquals(expected1, result1);
        assertEquals(expected2, result2);
    }
}
