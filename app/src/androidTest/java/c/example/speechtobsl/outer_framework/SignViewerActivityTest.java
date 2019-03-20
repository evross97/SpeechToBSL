package c.example.speechtobsl.outer_framework;

import android.content.Intent;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import c.example.speechtobsl.R;

import static android.app.Activity.RESULT_CANCELED;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

public class SignViewerActivityTest {

    @Rule
    public ActivityTestRule<SignViewerActivity> activityScenarioRule
            = new ActivityTestRule<>(SignViewerActivity.class);

    @Rule
    public ActivityTestRule<SpeechInputActivity> firstRule
            = new ActivityTestRule<>(SpeechInputActivity.class);

    @Test
    public void replay() {
        activityScenarioRule.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                activityScenarioRule.getActivity().signsFinished();
            }
        });
        Intents.init();
        //Replay
        onView(withId(R.id.replay_button)).check(matches(isDisplayed())).perform(click());
        assertEquals(true, activityScenarioRule.getActivityResult().getResultData().getBooleanExtra("replay", false));
        Intents.release();
    }

    @Test
    public void back() {
        firstRule.launchActivity(new Intent());
        activityScenarioRule.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                activityScenarioRule.getActivity().signsFinished();
            }
        });
        Intents.init();
        //Back
        Espresso.pressBack();
        Espresso.pressBack();
        assertEquals(RESULT_CANCELED, activityScenarioRule.getActivityResult().getResultCode());
        Intents.release();
    }
}
