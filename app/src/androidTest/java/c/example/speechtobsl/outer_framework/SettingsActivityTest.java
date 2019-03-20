package c.example.speechtobsl.outer_framework;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import c.example.speechtobsl.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.*;

public class SettingsActivityTest {

    @Rule
    public ActivityTestRule<SettingsActivity> activityScenarioRule
            = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void initialSetup() {
        onView(withId(R.id.written_description)).check(matches(withText("Show written BSL under sign:")));
        onView(withId(R.id.speed_description)).check(matches(withText("Speed of BSL sequence:")));
        onView(withId(R.id.written_switch)).check(matches(isChecked()));
        onView(withId(R.id.speed_bar)).check(matches(isEnabled()));
    }

    @Test
    public void onSwitch() {
        Intents.init();
        //Change switch
        onView(withId(R.id.written_switch)).perform(click());
        onView(withId(R.id.button2)).perform(click());
        assertEquals(false, activityScenarioRule.getActivityResult().getResultData().getBooleanExtra("showText", true));
        Intents.release();
    }

    @Test
    public void onSpeed() {
        Intents.init();
        //Change speed
        activityScenarioRule.getActivity().speed.setProgress(2);
        onView(withId(R.id.button2)).perform(click());
        assertEquals(2, activityScenarioRule.getActivityResult().getResultData().getIntExtra("speed", 0));
        Intents.release();
    }
}
