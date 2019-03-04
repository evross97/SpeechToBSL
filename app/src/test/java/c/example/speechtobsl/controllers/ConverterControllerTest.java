package c.example.speechtobsl.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import c.example.speechtobsl.TestUtils;
import c.example.speechtobsl.models.StanfordParserModel;
import c.example.speechtobsl.models.StructureConverterModel;
import c.example.speechtobsl.structure_converter.models.ParseModel;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class ConverterControllerTest {

    private TestUtils utils;
    @InjectMocks private ConverterController cController;
    @Mock private StructureConverterModel scModel;
    @Mock private StanfordParserModel parser;

    @Before
    public void setUp() throws Exception {
        this.utils = new TestUtils();
        this.cController = new ConverterController();
        MockitoAnnotations.initMocks(this);
        Mockito.when(this.scModel.getPOSTags()).thenReturn(this.utils.POSTags);
        Mockito.when(this.parser.getParse("Empty")).thenThrow(new JSONException("Exception"));
        Mockito.when(this.parser.getParse(this.utils.englishString)).thenReturn(this.utils.parsedText.toString());
        Mockito.when(this.scModel.convertSentence(any(JSONObject.class),eq(this.utils.englishString))).thenReturn(this.utils.parsedSentence);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTags() {
        ArrayList<JSONObject> tags = this.cController.getTags();
        assertEquals(this.utils.POSTags, tags);
    }

    @Test
    public void convertSentence() {
        ArrayList<String> empty = new ArrayList<>();
        try {
            empty = this.cController.convertSentence("Empty");
        } catch(Exception e){
            assertEquals(0, empty.size());
        }

        ArrayList<String> complete = this.cController.convertSentence(this.utils.englishString);
        assertEquals(this.utils.parsedSentence,complete);

    }
}
