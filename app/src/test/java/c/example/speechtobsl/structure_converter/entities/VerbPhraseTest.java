package c.example.speechtobsl.structure_converter.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.TestUtils;

import static org.junit.Assert.*;

public class VerbPhraseTest {

    private TestUtils utils;
    private VerbPhrase vp1;
    private VerbPhrase vp2;
    private VerbPhrase vp3;

    @Before
    public void setUp() throws Exception {
        this.utils = new TestUtils();
        this.vp1 = this.utils.vp1;
        this.vp2 = this.utils.vp2;
        this.vp3 = this.utils.vp3;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getVerb() {
        String result1 = this.vp1.getVerb();
        assertEquals("of", result1);

        String result2 = this.vp2.getVerb();
        assertEquals("run", result2);
    }

    @Test
    public void setLemmaVerb() {
        VerbPhrase vp4 = new VerbPhrase("swimming");
        ArrayList<String> before1 = vp4.toArrayString();
        vp4.setLemmaVerb("swim");
        ArrayList<String> after1 = vp4.toArrayString();
        assertNotEquals(before1, after1);
        assertTrue(after1.contains("swim"));
    }

    @Test
    public void getIsModal() {
        Boolean result1 = this.vp2.getIsModal();
        assertTrue(!result1);
        Boolean result2 = this.vp3.getIsModal();
        assertTrue(result2);
    }

    @Test
    public void setIsModal() {
        Boolean result1 = this.vp2.getIsModal();
        this.vp2.setIsModal(true);
        Boolean result2 = this.vp2.getIsModal();
        assertTrue(!result1);
        assertTrue(result2);
    }

    @Test
    public void setPrepVerb() {
        ArrayList<String> before1 = this.vp2.toArrayString();
        this.vp2.setPrepVerb(true);
        ArrayList<String> after1 = this.vp2.toArrayString();
        assertEquals(before1, after1);
    }

    @Test
    public void setNegated() {
        ArrayList<String> before1 = this.vp2.toArrayString();
        this.vp2.setNegated(true);
        ArrayList<String> after1 = this.vp2.toArrayString();
        assertNotEquals(before1, after1);
        assertTrue(after1.contains("NO"));
    }

    @Test
    public void setAdverbs() {
        ArrayList<String> advs = new ArrayList<>();
        advs.add("quickly");
        ArrayList<String> before = this.vp3.toArrayString();
        this.vp3.setAdverbs(advs);
        ArrayList<String> after = this.vp3.toArrayString();
        assertTrue(before.size() < after.size());
        assertTrue(after.contains("quickly"));
    }

    @Test
    public void toArrayString() {
        ArrayList<String> result1 = this.vp1.toArrayString();
        ArrayList<String> expected1 = new ArrayList<>(Arrays.asList("of"));
        assertEquals(expected1, result1);

        ArrayList<String> result2 = this.vp2.toArrayString();
        ArrayList<String> expected2 = new ArrayList<>(Arrays.asList("quickly", "run"));
        assertEquals(expected2, result2);

        ArrayList<String> result3 = this.vp3.toArrayString();
        ArrayList<String> expected3 = new ArrayList<>(Arrays.asList("could"));
        assertEquals(expected3, result3);

        VerbPhrase vp4 = new VerbPhrase("play","play",false,false,true,new ArrayList<>());
        ArrayList<String> result4 = vp4.toArrayString();
        ArrayList<String> expected4 = new ArrayList<>(Arrays.asList("NO", "play"));
        assertEquals(expected4, result4);
    }
}
