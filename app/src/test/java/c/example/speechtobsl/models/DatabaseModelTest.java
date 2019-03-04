package c.example.speechtobsl.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import c.example.speechtobsl.entities.Image;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class DatabaseModelTest {

    @InjectMocks private DatabaseModel dModel; ///shouldn't be mocked
    @Mock private SQLiteDatabase db;
    @Mock private Cursor cursor;
    @Mock private Context context;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(cursor.moveToNext())
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(cursor.getColumnIndex("Description")).thenReturn(0);
        Mockito.when(cursor.getColumnIndexOrThrow("Image")).thenReturn(1);
        Mockito.when(cursor.getString(0)).thenReturn("Trial");
        Mockito.when(cursor.getBlob(1)).thenReturn(new byte[]{1,2,3});
        //Mockito.when(new DatabaseModel(null)).thenReturn(null);
        Mockito.when(db.rawQuery(any(String.class), any(String[].class))).thenReturn(null);


        //this.dModel = new DatabaseModel(context);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllImages() {
        ArrayList<Image> empty = this.dModel.getAllImages(new ArrayList<>());
        assertEquals(0, empty.size());
    }

    @Test
    public void getDBSignForWord() {
    }
}
