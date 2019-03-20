package c.example.speechtobsl.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import c.example.speechtobsl.utils.TestUtils;
import c.example.speechtobsl.outer_framework.Client;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class StanfordParserModelTest {


    @InjectMocks private StanfordParserModel spModel;
    @Mock private Client client;

    private TestUtils utils;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.utils = new TestUtils();
        //this.spModel = new StanfordParserModel();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getParse() {
        Mockito.when(client.sendRequest(any(String[].class))).thenReturn("hi");
        String result1 = this.spModel.getParse("");
        assertNotEquals(new InterruptedException(), result1);
        String result2 = this.spModel.getParse(this.utils.englishString);
        assertNotEquals(new InterruptedException(), result2);
        assertEquals("hi", result2);
    }
}
