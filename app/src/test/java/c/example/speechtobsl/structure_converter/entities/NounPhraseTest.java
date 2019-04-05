package c.example.speechtobsl.structure_converter.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.utils.TestUtils;

import static org.junit.Assert.*;

public class NounPhraseTest {

    private TestUtils utils;
    private NounPhrase np1;
    private NounPhrase np2;
    private NounPhrase np3;

    
    @Before
    public void setUp() throws Exception {
        this.utils = new TestUtils();
        this.np1 = this.utils.np1;
        this.np2 = new NounPhrase("");
        this.np3 = this.utils.np3;
    }

    @Test
    public void setNoun() {
        ArrayList<String> before1 = this.np1.toArrayString();
        this.np1.setNoun("letter");
        ArrayList<String> after1 = this.np1.toArrayString();
        assertNotEquals(before1, after1);
        assertTrue(after1.contains("letter"));

        ArrayList<String> before2 = this.np2.toArrayString();
        this.np2.setNoun("girl");
        ArrayList<String> after2 = this.np2.toArrayString();
        assertNotEquals(before2, after2);
        assertEquals("[girl]", after2.toString());
    }

    @Test
    public void setPlural() {
        ArrayList<String> before1 = this.np1.toArrayString();
        this.np1.setPlural(false);
        ArrayList<String> after1 = this.np1.toArrayString();
        assertNotEquals(before1, after1);
        assertTrue(!after1.contains("MANY"));

        ArrayList<String> before2 = this.np3.toArrayString();
        this.np3.setPlural(true);
        ArrayList<String> after2 = this.np3.toArrayString();
        assertNotEquals(before2, after2);
        assertTrue(after2.contains("MANY"));
    }

    @Test
    public void setDeterminer() {
        ArrayList<String> before1 = this.np1.toArrayString();
        this.np1.setDeterminer("THE");
        ArrayList<String> after1 = this.np1.toArrayString();
        assertEquals(before1, after1);
        assertTrue(!after1.contains("THE"));

        ArrayList<String> before2 = this.np3.toArrayString();
        this.np3.setDeterminer("MY");
        ArrayList<String> after2 = this.np3.toArrayString();
        assertNotEquals(before2, after2);
        assertTrue(after2.contains("MY"));
    }

    @Test
    public void setAdjectives() {
        Adjective adj1 = new Adjective("big", new ArrayList<>());
        Adjective adj2 = new Adjective("quirky", new ArrayList<>());
        ArrayList<Adjective> adjs = new ArrayList<>();
        adjs.add(adj1);
        adjs.add(adj2);
        ArrayList<String> before1 = this.np1.toArrayString();
        this.np1.setAdjectives(adjs);
        ArrayList<String> after1 = this.np1.toArrayString();
        assertNotEquals(before1, after1);
        assertTrue(after1.contains("big") && after1.contains("quirky"));
    }

    @Test
    public void getPreposition() {
        String result1 = this.np1.getPreposition();
        assertEquals("of", result1);

        String result2 = this.np2.getPreposition();
        assertEquals("",result2);
    }

    @Test
    public void setPreposition() {
        String before = this.np2.getPreposition();
        this.np2.setPreposition("under");
        String after = this.np2.getPreposition();
        assertNotEquals(before, after);
        assertEquals("under", after);
    }

    @Test
    public void isSubject() {
        Boolean result1 = this.np1.isSubject();
        Boolean result2 = this.np3.isSubject();
        assertTrue(result1);
        assertTrue(!result2);
    }

    @Test
    public void setSubject() {
        Boolean before = this.np2.isSubject();
        this.np2.setSubject(true);
        Boolean after = this.np2.isSubject();
        assertNotEquals(before, after);
        assertTrue(after);
    }

    @Test
    public void toArrayString() {
        ArrayList<String> result1 = this.np1.toArrayString();
        ArrayList<String> expected1 = new ArrayList<>(Arrays.asList("MANY", "name"));
        assertEquals(result1, expected1);

        ArrayList<String> result2 = this.np2.toArrayString();
        ArrayList<String> expected2 = new ArrayList<>(Arrays.asList(""));
        assertEquals(result2, expected2);

        ArrayList<String> result3 = this.np3.toArrayString();
        ArrayList<String> expected3 = new ArrayList<>(Arrays.asList("they"));
        assertEquals(result3, expected3);

    }
}
