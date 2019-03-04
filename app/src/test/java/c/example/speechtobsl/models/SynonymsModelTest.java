package c.example.speechtobsl.models;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import c.example.speechtobsl.TestUtils;
import c.example.speechtobsl.outer_framework.Client;
import c.example.speechtobsl.structure_converter.models.TagModel;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class SynonymsModelTest {

    @InjectMocks private SynonymsModel sModel;
    @Mock private Client thesaurus;
    private TestUtils utils;

    @Before
    public void setUp() throws Exception {
        this.utils = new TestUtils();
        MockitoAnnotations.initMocks(this);
        Mockito.when(thesaurus.sendRequest(any(String[].class))).thenReturn(this.utils.synResponse);
        TagModel tagger = new TagModel(this.utils.POSTags,this.utils.englishText);
        this.sModel = new SynonymsModel(tagger);
    }

    @Test
    public void getSynonyms() {
        ArrayList<String> result1 = this.sModel.getSynonyms("");
        assertNotEquals(new InterruptedException(), result1);
        ArrayList<String> result2 = this.sModel.getSynonyms("dog");
        assertNotEquals(new InterruptedException(), result2);
        assertEquals(5, result2.size());
    }
}
