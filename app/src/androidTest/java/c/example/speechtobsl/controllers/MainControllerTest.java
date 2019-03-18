package c.example.speechtobsl.controllers;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.Arrays;

import androidx.test.core.app.ApplicationProvider;
import c.example.speechtobsl.outer_framework.EndListener;
import c.example.speechtobsl.views.SignView;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(AndroidJUnit4.class)
public class MainControllerTest {

    private MainController mController;
    private ConverterController cController;

    private ArrayList<String> BSLSentence;

    private SignView spySView;
    private EndListener spyListener;

    //Most wonderful line in the world!
    static { System.setProperty("org.mockito.android.target", ApplicationProvider.getApplicationContext().getCacheDir().getPath()); }

    @Before
    public void setUp() {
        this.BSLSentence = new ArrayList<>(Arrays.asList("blg", "dog", "where"));

        this.cController = mock(ConverterController.class);
        this.mController = new MainController(ApplicationProvider.getApplicationContext(), null);
        this.mController.cController = this.cController;
        Mockito.when(cController.convertSentence("where is the blg dog")).thenReturn(this.BSLSentence);
        Mockito.when(cController.convertSentence("")).thenReturn(new ArrayList<>());

        SignView sView = mock(SignView.class);
        this.spySView = Mockito.spy(sView);
        this.mController.signView = this.spySView;

        EndListener listener = mock(EndListener.class);
        this.spyListener = Mockito.spy(listener);
        this.mController.listener = this.spyListener;
    }

    @Test
    public void getBSL() {
        this.mController.getBSL("");
        Mockito.verify(this.spySView, times(0)).showSequence(new ArrayList<>());
        Mockito.verify(this.spyListener, times(1)).onFailure();

        this.mController.getBSL("where is the blg dog");
        Mockito.verify(this.spySView, times(1)).showSequence(any(ArrayList.class));
        Mockito.verify(this.spyListener, times(1)).onSuccess();
    }

    @Test
    public void replaySequence() {
        this.mController.replaySequence();
        Mockito.verify(this.spySView, times(1)).replaySequence();
    }

    @Test
    public void updateSpeed() {
        this.mController.updateSpeed(1);
        Mockito.verify(this.spySView, times(1)).speedUpdate(1);
    }


}
