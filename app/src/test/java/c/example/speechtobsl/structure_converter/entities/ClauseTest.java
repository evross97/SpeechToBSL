package c.example.speechtobsl.structure_converter.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.utils.TestUtils;

import static org.junit.Assert.*;

public class ClauseTest {

    private Clause c1;
    private Clause c2;
    private Clause c3;

    private TestUtils utils;

    @Before
    public void setUp() throws Exception {
        this.utils = new TestUtils();
        this.c1 = this.utils.c1;
        this.c2 = this.utils.c2;
        this.c3 = new Clause(2);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void setNPs() {
        ArrayList<NounPhrase> NPs = new ArrayList<>();
        NPs.add(this.utils.np2);
        this.c3.setNPs(NPs);
        assertTrue(this.c3.toArrayString().contains("dog"));
    }

    @Test
    public void getVPs() {
        ArrayList<VerbPhrase> result1 = this.c2.getVPs();
        assertEquals(2, result1.size());
        assertEquals(this.utils.vp3, result1.get(0));
        assertEquals(this.utils.vp2, result1.get(1));


        ArrayList<VerbPhrase> result2 = this.c3.getVPs();
        assertEquals(0,result2.size());
    }

    @Test
    public void setVPs() {
        ArrayList<VerbPhrase> VPs = new ArrayList<>();
        VPs.add(this.utils.vp1);
        this.c3.setVPs(VPs);
        assertEquals(1,this.c3.getVPs().size());
    }

    @Test
    public void setConnector() {
        ArrayList<String> before = c3.toArrayString();
        c3.setConnector("but");
        ArrayList<String> after = c3.toArrayString();
        assertEquals(before.size()+1, after.size());
        assertTrue(after.contains("but"));
    }

    @Test
    public void setBeforeConnector() {
        int before = c2.toArrayString().indexOf("and");
        c2.setBeforeConnector(true);
        int after = c2.toArrayString().indexOf("and");
        Boolean condition = after > before;
        assertTrue(condition);
    }

    @Test
    public void getTimeFrame() {
        String result1 = c2.getTimeFrame();
        assertEquals("FUTURE", result1);
    }

    @Test
    public void setTimeFrame() {
        String result1 = c3.getTimeFrame();
        c3.setTimeFrame("yesterday");
        String result2 = c3.getTimeFrame();
        assertNotEquals(result1, result2);
        assertEquals("yesterday", result2);
    }

    @Test
    public void setQuestion() {
        ArrayList<String> before = c3.toArrayString();
        c3.setQuestion("why");
        ArrayList<String> after = c3.toArrayString();
        assertNotEquals(before.size(), after.size());
        assertEquals(after.get(0), "why");
    }

    @Test
    public void toArrayString() {
        String[] exp1 = new String[]{"MANY", "dog", "big", "MANY", "name", "of", "what"};
        ArrayList<String> expected1 = new ArrayList<>(Arrays.asList(exp1));
        ArrayList<String> result1 = c1.toArrayString();
        assertEquals(expected1, result1);

        String[] exp2 = new String[]{"and", "FUTURE", "they", "could", "quickly", "run"};
        ArrayList<String> expected2 = new ArrayList<>(Arrays.asList(exp2));
        ArrayList<String> result2 = c2.toArrayString();
        assertEquals(expected2, result2);

        ArrayList<String> result3 = c3.toArrayString();
        assertEquals(new ArrayList<String>(), result3);
    }
}
