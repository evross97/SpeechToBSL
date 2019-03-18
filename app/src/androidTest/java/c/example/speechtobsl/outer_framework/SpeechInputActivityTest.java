package c.example.speechtobsl.outer_framework;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.annotation.UiThreadTest;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import c.example.speechtobsl.R;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.*;

public class SpeechInputActivityTest {

    @Rule
    public ActivityTestRule<SpeechInputActivity> activityScenarioRule
            = new ActivityTestRule<>(SpeechInputActivity.class);

    private SpeechInputActivity activity;

    @Before
    public void setUp() {
        this.activity = this.activityScenarioRule.getActivity();
    }
    @Test
    public void onRecord() {
        onView(withId(R.id.record_button)).perform(click());
        onView(withId(R.id.record_text)).check(matches(withText("Press the button again to stop recording")));
    }

    @Test
    public void onSuccess() {
        Intents.init();
        activityScenarioRule.getActivity().onSuccess();
        activityScenarioRule.launchActivity(new Intent());
        intended(hasComponent(SignViewerActivity.class.getName()));
        Intents.release();
    }

    @Test
    @UiThreadTest
    public void onFailure() {
        activityScenarioRule.getActivity().onFailure();
        assertTrue(activityScenarioRule.getActivity().isFinishing());
    }

    @Test
    public void onOptionsItemSelected() {
        Intents.init();
        activityScenarioRule.getActivity().onOptionsItemSelected(new MenuItem() {
            @Override
            public int getItemId() {
                return R.id.action_settings;
            }

            @Override
            public int getGroupId() {
                return 0;
            }

            @Override
            public int getOrder() {
                return 0;
            }

            @Override
            public MenuItem setTitle(CharSequence charSequence) {
                return null;
            }

            @Override
            public MenuItem setTitle(int i) {
                return null;
            }

            @Override
            public CharSequence getTitle() {
                return null;
            }

            @Override
            public MenuItem setTitleCondensed(CharSequence charSequence) {
                return null;
            }

            @Override
            public CharSequence getTitleCondensed() {
                return null;
            }

            @Override
            public MenuItem setIcon(Drawable drawable) {
                return null;
            }

            @Override
            public MenuItem setIcon(int i) {
                return null;
            }

            @Override
            public Drawable getIcon() {
                return null;
            }

            @Override
            public MenuItem setIntent(Intent intent) {
                return null;
            }

            @Override
            public Intent getIntent() {
                return null;
            }

            @Override
            public MenuItem setShortcut(char c, char c1) {
                return null;
            }

            @Override
            public MenuItem setNumericShortcut(char c) {
                return null;
            }

            @Override
            public char getNumericShortcut() {
                return 0;
            }

            @Override
            public MenuItem setAlphabeticShortcut(char c) {
                return null;
            }

            @Override
            public char getAlphabeticShortcut() {
                return 0;
            }

            @Override
            public MenuItem setCheckable(boolean b) {
                return null;
            }

            @Override
            public boolean isCheckable() {
                return false;
            }

            @Override
            public MenuItem setChecked(boolean b) {
                return null;
            }

            @Override
            public boolean isChecked() {
                return false;
            }

            @Override
            public MenuItem setVisible(boolean b) {
                return null;
            }

            @Override
            public boolean isVisible() {
                return false;
            }

            @Override
            public MenuItem setEnabled(boolean b) {
                return null;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public boolean hasSubMenu() {
                return false;
            }

            @Override
            public SubMenu getSubMenu() {
                return null;
            }

            @Override
            public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
                return null;
            }

            @Override
            public ContextMenu.ContextMenuInfo getMenuInfo() {
                return null;
            }

            @Override
            public void setShowAsAction(int i) {

            }

            @Override
            public MenuItem setShowAsActionFlags(int i) {
                return null;
            }

            @Override
            public MenuItem setActionView(View view) {
                return null;
            }

            @Override
            public MenuItem setActionView(int i) {
                return null;
            }

            @Override
            public View getActionView() {
                return null;
            }

            @Override
            public MenuItem setActionProvider(ActionProvider actionProvider) {
                return null;
            }

            @Override
            public ActionProvider getActionProvider() {
                return null;
            }

            @Override
            public boolean expandActionView() {
                return false;
            }

            @Override
            public boolean collapseActionView() {
                return false;
            }

            @Override
            public boolean isActionViewExpanded() {
                return false;
            }

            @Override
            public MenuItem setOnActionExpandListener(OnActionExpandListener onActionExpandListener) {
                return null;
            }
        });
        activityScenarioRule.launchActivity(new Intent());
        intended(hasComponent(SettingsActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void onActivityResult() {
        //Settings
        activityScenarioRule.getActivity().onActivityResult(1,0, new Intent());
        assertEquals(2,(int)this.activity.speed);
        assertTrue(this.activity.showText);

        //Cancelled
        activityScenarioRule.getActivity().onActivityResult(2,Activity.RESULT_CANCELED, new Intent());
        onView(withId(R.id.record_text)).check(matches(withText("Press the red button to start recording")));

        //Replay
        activityScenarioRule.getActivity().onActivityResult(2,Activity.RESULT_OK, new Intent());
        Intents.init();
        activityScenarioRule.getActivity().onSuccess();
        activityScenarioRule.launchActivity(new Intent());
        intended(hasComponent(SignViewerActivity.class.getName()));
        Intents.release();
    }
}
