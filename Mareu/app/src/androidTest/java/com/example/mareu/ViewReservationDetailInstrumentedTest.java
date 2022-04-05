package com.example.mareu;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mareu.ui.list_reservation.ListReservationActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

public class ViewReservationDetailInstrumentedTest {
    @Rule
    public ActivityScenarioRule<ListReservationActivity> mActivityRule = new ActivityScenarioRule<>(ListReservationActivity.class);
    private ListReservationActivity mActivity;


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
}
