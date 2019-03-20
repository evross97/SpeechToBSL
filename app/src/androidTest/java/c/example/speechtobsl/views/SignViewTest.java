package c.example.speechtobsl.views;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.rule.ActivityTestRule;
import c.example.speechtobsl.TestUtils;
import c.example.speechtobsl.entities.Image;
import c.example.speechtobsl.outer_framework.SignViewerActivity;

import static org.junit.Assert.*;

public class SignViewTest {

    private SignView sView;

    private TestUtils utils;
    private ArrayList<Image> imageSentence;

    @Rule
    public ActivityTestRule<SignViewerActivity> testRule
            = new ActivityTestRule<>(SignViewerActivity.class);

    @Before
    public void setUp() {
        this.utils = new TestUtils();
        this.sView = new SignView(ApplicationProvider.getApplicationContext());
        this.imageSentence = new ArrayList<>(Arrays.asList(this.utils.blg));
    }

    @Test
    public void speedUpdate() {
        this.sView.speedUpdate(0);
        assertEquals(2000, (int)sView.delayTime);
        sView.speedUpdate(1);
        assertEquals(1500, (int)sView.delayTime);
        sView.speedUpdate(2);
        assertEquals(1000, (int)sView.delayTime);
        sView.speedUpdate(5);
        assertEquals(1500, (int)sView.delayTime);

    }

    @Test
    public void showSequence() throws InterruptedException{
        testRule.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                sView.speedUpdate(2);
                sView.showSequence(imageSentence);
            }
        });
        Thread.sleep(3000);
        assertEquals(2, (int)this.sView.currentImageIndex);
    }

    @Test
    public void replaySequence() throws InterruptedException{
        testRule.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                sView.replaySequence();
            }
        });
        Thread.sleep(3000);
        assertEquals(0, (int)this.sView.currentImageIndex);

        testRule.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                sView.speedUpdate(2);
                sView.showSequence(imageSentence);
            }
        });
        Thread.sleep(3000);
        testRule.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                sView.replaySequence();
            }
        });
        Thread.sleep(3000);
        assertEquals(2, (int)this.sView.currentImageIndex);
    }
}
