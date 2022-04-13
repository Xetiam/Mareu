package com.example.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertTrue;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mareu.ui.add_reservation.AddReservationActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddReservationInstrumentedTest {
    @Rule
    public ActivityScenarioRule<AddReservationActivity> mActivityRule = new ActivityScenarioRule<>(AddReservationActivity.class);
    private AddReservationActivity mActivity;

    @Before
    public void setUp() {
        Intents.init();
        mActivityRule.getScenario().onActivity(activity -> mActivity = activity);
        assertThat(mActivity, notNullValue());
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void A_shouldEnabledCreatePart() {
        onView(withId(R.id.participants)).perform(ViewActions.typeText("participant1@mail.fr"));
        onView(withId(R.id.participantButton)).perform(click());
        onView(withId(R.id.participantsList)).check(matches(isEnabled()));
    }

    @Test
    public void B_shouldDisabledCreatePart() {
        onView(withId(R.id.participants)).perform(ViewActions.typeText("participant1@mail"));
        onView(withId(R.id.participantButton)).check(matches(isNotEnabled()));
    }

    @Test
    public void C_shouldDisableValidate() {
        onView(withId(R.id.name)).perform(ViewActions.typeText("Le "));
        onView(withId(R.id.participants)).perform(ViewActions.typeText("participant1@mail.fr"));
        onView(withId(R.id.participantButton)).perform(click());
        onView(withId(R.id.create)).check(matches(isNotEnabled()));
    }


    @Test
    public void D_shouldDeleteParticipant() {
        onView(withId(R.id.participants)).perform(ViewActions.typeText("participant1@mail.fr"));
        onView(withId(R.id.participantButton)).perform(click());
        onView(withId(100)).perform(click())
                .check(doesNotExist());
    }

    @Test
    public void E_shouldEnabledValidate() {
        onView(withId(R.id.name)).perform(ViewActions.typeText("Le nom de la reunion"));
        onView(withId(R.id.participants)).perform(ViewActions.typeText("participant1@mail.fr"));
        onView(withId(R.id.participantButton)).perform(click());
        onView(withId(R.id.participants)).perform(ViewActions.typeText("participant2@mail.fr"));
        onView(withId(R.id.participantButton)).perform(click());
        onView(withId(R.id.create)).check(matches(isEnabled()));
    }

    @Test
    public void F_shouldFinishActivity() {
        onView(withId(R.id.name)).perform(ViewActions.typeText("Le nom de la reunion"));
        onView(withId(R.id.participants)).perform(ViewActions.typeText("participant1@mail.fr"));
        onView(withId(R.id.participantButton)).perform(click());
        onView(withId(R.id.participants)).perform(ViewActions.typeText("participant2@mail.fr"));
        onView(withId(R.id.participantButton)).perform(click());
        onView(withId(R.id.participants)).perform(closeSoftKeyboard());
        onView(withId(R.id.create)).perform(scrollTo())
                .perform(click());
        assertTrue(mActivity.isFinishing());
    }
}
