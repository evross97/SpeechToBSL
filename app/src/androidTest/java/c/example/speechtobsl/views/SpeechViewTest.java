package c.example.speechtobsl.views;

import android.os.Bundle;
import android.speech.SpeechRecognizer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

import androidx.test.annotation.UiThreadTest;
import androidx.test.core.app.ApplicationProvider;
import c.example.speechtobsl.controllers.MainController;
import c.example.speechtobsl.outer_framework.EndListener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class SpeechViewTest {

    static { System.setProperty("org.mockito.android.target", ApplicationProvider.getApplicationContext().getCacheDir().getPath()); }

    private SpeechView sView;
    private EndListener spyListener;
    private MainController spyController;
    private SpeechRecognizer spySpeech;

    @Before
    @UiThreadTest
    public void setUp() {
        EndListener listener = mock(EndListener.class);
        this.spyListener = Mockito.spy(listener);

        this.sView = new SpeechView(ApplicationProvider.getApplicationContext(),spyListener);

        MainController mController = mock(MainController.class);
        this.spyController = Mockito.spy(mController);
        this.sView.mController = this.spyController;

        SpeechRecognizer speech = mock(SpeechRecognizer.class);
        this.spySpeech = Mockito.spy(speech);
        this.sView.speech = this.spySpeech;
    }

    @Test
    public void replaySequence() {
        this.sView.replaySequence();
        Mockito.verify(this.spyController, times(1)).replaySequence();
    }

    @Test
    public void updateSpeed() {
        this.sView.updateSpeed(0);
        Mockito.verify(this.spyController, times(1)).updateSpeed(0);

        this.sView.updateSpeed(2);
        Mockito.verify(this.spyController, times(1)).updateSpeed(2);
    }

    @Test
    public void startListening() {
        this.sView.startListening();
        Mockito.verify(this.spySpeech, times(1)).startListening(any());
    }

    @Test
    public void stopListening() {
        this.sView.stopListening();
        Mockito.verify(this.spySpeech, times(1)).stopListening();
    }

    @Test
    public void getErrorText() {
        String clientFailure = this.sView.getErrorText(5);
        assertEquals("Client side error", clientFailure);

        String networkFailure = this.sView.getErrorText(2);
        assertEquals("Network error", networkFailure);

        String noMatchFailure = this.sView.getErrorText(7);
        assertEquals("No match", noMatchFailure);

        String otherFailure = this.sView.getErrorText(16);
        assertEquals("Didn't understand, please try again.", otherFailure);
    }

    @Test
    public void onError() {
        this.sView.onError(4);
        Mockito.verify(this.spyListener, times(1)).onFailure();
    }

    @Test
    public void onResults() {
        Bundle mockBundle1 = new Bundle();
        mockBundle1.putStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION, new ArrayList<>(Arrays.asList("")));

        this.sView.onResults(mockBundle1);
        Mockito.verify(this.spyController, times(1)).getBSL("");

        Bundle mockBundle2 = new Bundle();
        mockBundle2.putStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION, new ArrayList<>(Arrays.asList("example speech")));
        this.sView.onResults(mockBundle2);
        Mockito.verify(this.spyController, times(1)).getBSL("example speech");
    }
}
