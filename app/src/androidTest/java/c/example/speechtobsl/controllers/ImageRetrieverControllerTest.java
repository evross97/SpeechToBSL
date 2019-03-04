package c.example.speechtobsl.controllers;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.test.core.app.ApplicationProvider;
import c.example.speechtobsl.TestUtils;
import c.example.speechtobsl.entities.Image;
import c.example.speechtobsl.models.DatabaseModel;
import c.example.speechtobsl.models.SynonymsModel;
import c.example.speechtobsl.structure_converter.models.TagModel;

import static org.junit.Assert.*;

@RunWith(androidx.test.runner.AndroidJUnit4.class)
@MediumTest
public class ImageRetrieverControllerTest {

    private TestUtils utils;
    private SynonymsModel sModel;
    private DatabaseModel db;
    private ImageRetrieverController iController;

    private ArrayList<String> BSLSentence;

    private Image blg;
    private Image where;
    private Image hound;



    @Before
    public void setUp() throws Exception {
        this.utils = new TestUtils();

        this.iController = new ImageRetrieverController(ApplicationProvider.getApplicationContext());
        this.db = new DatabaseModel(ApplicationProvider.getApplicationContext());
        this.sModel = new SynonymsModel(new TagModel(this.utils.POSTags, this.utils.englishText));
        this.BSLSentence = new ArrayList<>(Arrays.asList("blg", "dog", "where"));
        this.where = new Image(new byte[]{}, "WHERE");
        this.hound = new Image(new byte[]{}, "DOG");
        this.blg = new Image(new byte[]{}, "BLG");
    }

    @Test
    public void getImageSentence1() {
        ArrayList<Image> emptySentence = iController.getImageSentence(new ArrayList<>(),this.utils.POSTags,this.utils.englishText);
        assertEquals(new ArrayList<>(), emptySentence);

        this.iController = new ImageRetrieverController(ApplicationProvider.getApplicationContext());
        ArrayList<Image> result = iController.getImageSentence(this.BSLSentence,this.utils.POSTags,this.utils.englishText);
        ArrayList<Image> expected = new ArrayList<>(Arrays.asList(this.blg,new Image(null, " "),this.blg,new Image(null, " "),this.blg,new Image(null, " "), new Image(null, "endWord"), this.hound,this.where));
        assertEquals(expected.size(),result.size());
        for(int i = 0; i < result.size(); i++) {
            System.out.println(i);
            assertEquals(expected.get(i).getDesc(),result.get(i).getDesc());
        }
    }
}
