package c.example.speechtobsl.views;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.test.annotation.UiThreadTest;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.intercepting.SingleActivityFactory;
import c.example.speechtobsl.TestUtils;
import c.example.speechtobsl.entities.Image;
import c.example.speechtobsl.outer_framework.SignViewerActivity;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import static org.junit.Assert.*;

public class SignViewTest {

    static { System.setProperty("org.mockito.android.target", ApplicationProvider.getApplicationContext().getCacheDir().getPath()); }

    private SignView sView;
    private SignViewerActivity spySignViewer;

    private TestUtils utils;
    private ArrayList<Image> imageSentence;

    /**SingleActivityFactory<SignViewerActivity> activityFactory = new SingleActivityFactory<SignViewerActivity>(SignViewerActivity.class) {
        @Override
        protected SignViewerActivity create(Intent intent) {
            spySignViewer = spy(getActivityClassToIntercept());
            return spySignViewer;
        }
    };
    @Rule
    public ActivityTestRule<SignViewerActivity> testRule = new ActivityTestRule<>(activityFactory, true, true);**/


    @Before
    @UiThreadTest
    public void setUp() {
        this.utils = new TestUtils();
        this.sView = new SignView(ApplicationProvider.getApplicationContext());

        this.imageSentence = new ArrayList<>(Arrays.asList(this.utils.blg));
    }

    @After
    public void tearDown() {
        //this.spySignViewer.finish();
    }

    @Test
    @UiThreadTest
    public void showSequence() throws InterruptedException{
        /**System.out.println("IMAGE SENTENCE SIZE:" + imageSentence.size());
        sView.speedUpdate(2);
        sView.showSequence(imageSentence);
        Thread.sleep(2000);
        Mockito.verify(spySignViewer).setDesc("BLG");
        Mockito.verify(spySignViewer).setImage(new byte[]{});
        Mockito.verify(spySignViewer).setImageBackground(android.R.color.white);

        sView.showSequence(new ArrayList<>());
        Thread.sleep(2000);
        Mockito.verify(spySignViewer, never()).setDesc(any());
        Mockito.verify(spySignViewer, never()).setImage(any());
        Mockito.verify(spySignViewer, never()).setImageBackground(any());**/

        /**sView.speedUpdate(2);
        sView.showSequence(imageSentence);
        verify(this.sView., timeout(6000).times(1)).equals(2);
        //assertEquals(2,(int)this.sView.currentImageIndex);

        sView.showSequence(new ArrayList<>());
        Thread.sleep(6000);
        assertEquals(1,(int)this.sView.currentImageIndex);**/
    }

    @Test
    @UiThreadTest
    public void replaySequence() throws InterruptedException{
        /**System.out.println(imageSentence.size());
        sView.speedUpdate(2);
        sView.replaySequence();
        Thread.sleep(2000);
        Mockito.verify(spySignViewer, never()).setDesc(any());
        Mockito.verify(spySignViewer, never()).setImage(any());
        Mockito.verify(spySignViewer, never()).setImageBackground(android.R.color.white);

        sView.BSLImages = this.imageSentence;
        sView.replaySequence();
        Thread.sleep(2000);
        Mockito.verify(spySignViewer).setDesc("BLG");
        Mockito.verify(spySignViewer).setImage(new byte[]{});
        Mockito.verify(spySignViewer).setImageBackground(android.R.color.white);**/
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
}
