package c.example.speechtobsl.controllers;

import android.support.test.filters.MediumTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.test.core.app.ApplicationProvider;
import c.example.speechtobsl.TestUtils;
import c.example.speechtobsl.entities.Image;

import static org.junit.Assert.*;

@RunWith(androidx.test.runner.AndroidJUnit4.class)
@MediumTest
public class ImageRetrieverControllerTest {

    private TestUtils utils;
    private ImageRetrieverController iController;

    @Before
    public void setUp() throws Exception {
        this.utils = new TestUtils();

        this.iController = new ImageRetrieverController(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void getImageSentence1() {
        ArrayList<Image> emptySentence = iController.getImageSentence(new ArrayList<>(),this.utils.POSTags,this.utils.englishText);
        assertEquals(new ArrayList<>(), emptySentence);

        this.iController = new ImageRetrieverController(ApplicationProvider.getApplicationContext());
        ArrayList<Image> result = iController.getImageSentence(this.utils.BSLSentence,this.utils.POSTags,this.utils.englishText);
        ArrayList<Image> expected = new ArrayList<>(Arrays.asList(this.utils.blg,new Image(null, " "),this.utils.blg,new Image(null, " "),this.utils.blg,new Image(null, " "), new Image(null, "endWord"), this.utils.hound,this.utils.where));
        assertEquals(expected.size(),result.size());
        for(int i = 0; i < result.size(); i++) {
            assertEquals(expected.get(i).getDesc(),result.get(i).getDesc());
        }
    }
}
