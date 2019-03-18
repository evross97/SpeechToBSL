package c.example.speechtobsl.outer_framework;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import c.example.speechtobsl.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class StartUpActivityTest {

    @Rule
    public ActivityScenarioRule<StartUpActivity> activityScenarioRule
            = new ActivityScenarioRule<>(StartUpActivity.class);


    @Test
    public void startMain() {
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.record_text)).check(matches(withText("Press the red button to start recording")));
    }
}
