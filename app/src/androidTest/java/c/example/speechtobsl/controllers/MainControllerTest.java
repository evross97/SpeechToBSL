package c.example.speechtobsl.controllers;

import androidx.test.ext.junit.runners.AndroidJUnit4;

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
import c.example.speechtobsl.outer_framework.SuccessListener;
import c.example.speechtobsl.views.SignView;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(AndroidJUnit4.class)
public class MainControllerTest {

    private TestUtils utils;
    private MainController mController;
    private ConverterController cController;

    private ArrayList<String> englishSentence;
    private ArrayList<String> BSLSentence;
    private Image where;
    private Image hound;
    private Image blg;

    private SignView sView;
    private SignView spySView;
    private SuccessListener listener;

    //Most wonderful line in the world!
    static { System.setProperty("org.mockito.android.target", ApplicationProvider.getApplicationContext().getCacheDir().getPath()); }

    @Before
    public void setUp() throws Exception {
        this.BSLSentence = new ArrayList<>(Arrays.asList("blg", "dog", "where"));
        this.where = new Image(new byte[]{}, "WHERE");
        this.hound = new Image(new byte[]{}, "DOG");
        this.blg = new Image(new byte[]{}, "BLG");

        //MockitoAnnotations.initMocks(this);
        this.cController = mock(ConverterController.class);
        this.mController = new MainController(ApplicationProvider.getApplicationContext(), null);
        this.mController.cController = this.cController;
        Mockito.when(cController.convertSentence("where is the blg dog")).thenReturn(this.BSLSentence);
        Mockito.when(cController.convertSentence("")).thenReturn(new ArrayList<>());

        this.sView = mock(SignView.class);
        spySView = Mockito.spy(sView);
        this.mController.signView = spySView;

        this.listener = mock(SuccessListener.class);
        this.mController.listener = this.listener;
    }

    @Test
    public void getBSL() {
        //ALMOST GOT IT!!!
        this.mController.getBSL("");
        Mockito.verify(spySView, times(1)).showSequence(new ArrayList<>());

        this.mController.getBSL("where is the blg dog");
        Mockito.verify(spySView, times(1)).showSequence(any(ArrayList.class));


    }
}
