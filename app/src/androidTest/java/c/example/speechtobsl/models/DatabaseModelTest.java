package c.example.speechtobsl.models;

import android.support.test.filters.MediumTest;

import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.Arrays;


import androidx.test.core.app.ApplicationProvider;
import c.example.speechtobsl.entities.Image;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class DatabaseModelTest {

    private DatabaseModel dModel;

    @Before
    public void setUp() {
        this.dModel = new DatabaseModel(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void getAllImages() {
        ArrayList<Image> empty = this.dModel.getAllImages(new ArrayList<>());
        assertEquals(0, empty.size());

        ArrayList<Image> boy = this.dModel.getAllImages(new ArrayList<>(Arrays.asList("boy")));
        assertEquals(1, boy.size());

        ArrayList<Image> people = this.dModel.getAllImages(new ArrayList<>(Arrays.asList("boy", "girl", "lad")));
        assertEquals(2, people.size());
    }

    @Test
    public void getDBSignForWord() {
        ArrayList<Image> people = this.dModel.getAllImages(new ArrayList<>(Arrays.asList("boy", "girl", "lad")));
        Image boy = this.dModel.getDBSignForWord(people, "boy");
        assertEquals("BOY", boy.getDesc());

        Image lad = this.dModel.getDBSignForWord(people, "lad");
        assertEquals(null, lad.getImage());
    }
}
