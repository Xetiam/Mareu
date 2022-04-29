package com.example.mareu;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.core.IsNull.notNullValue;

import android.widget.DatePicker;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mareu.ui.list_reservation.ListReservationActivity;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListReservationInstrumentedTest {
    @Rule
    public final ActivityScenarioRule<ListReservationActivity> mActivityRule = new ActivityScenarioRule<>(ListReservationActivity.class);
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

    @Test
    public void A_shouldGotoAddReservationWhenClicked() {
        onView(withId(R.id.add_reservation)).perform(click());
        onView(withId(R.id.nameLyt)).check(matches(isDisplayed()));
    }

    @Test
    public void B_shouldFilterByDate() {
        generatingMeetings();
        onView(withId(R.id.menu_main_setting)).perform(click());
        onView(withText("Filtrer par date")).perform(click());
        onView(withId(R.id.popup_Date)).perform(PickerActions.setDate(2024, 5   , 15));
        onView(withText(endsWith("OK"))).perform(click());
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.reservationTitle))
                .check(matches(withText("Reunion 3 - 15/04/2024 - Peach")));
        destroyMeetings();
    }

    @Test
    public void C_shouldFilterByRoom() {
        generatingMeetings();
        onView(withId(R.id.menu_main_setting)).perform(click());
        onView(withText("Filtrer par salle")).perform(click());
        onView(withId(R.id.popup_Spinner)).perform(click());
        onView(withText("Luigi")).perform(click());
        onView(withText("Confirmer")).perform(click());
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.reservationTitle))
                .check(matches(withText("Reunion 1 - 15/04/2028 - Luigi")));
        destroyMeetings();
    }

    @Test
    public void D_shouldFilterByCreation() {
        generatingMeetings();
        onView(withId(R.id.menu_main_setting)).perform(click());
        onView(withText("Filtrer par salle")).perform(click());
        onView(withId(R.id.popup_Spinner)).perform(click());
        onView(withText("Luigi")).perform(click());
        onView(withText("Confirmer")).perform(click());
        onView(withId(R.id.menu_main_setting)).perform(click());
        onView(withText("Supprimer les filtres")).perform(click());
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(2, R.id.reservationTitle))
                .check(matches(withText("Reunion 3 - 15/04/2024 - Peach")));
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(1, R.id.reservationTitle))
                .check(matches(withText("Reunion 2 - 15/04/2026 - Mario")));
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.reservationTitle))
                .check(matches(withText("Reunion 1 - 15/04/2028 - Luigi")));
        destroyMeetings();
    }

    //ViewReservationDetail
    @Test
    public void E_shouldGoToDetail(){
        generatingMeeting("Reunion 1",1,2028, 5, 15);
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.reservationTitle))
                .perform(click());
        onView(withId(R.id.detail_reservation_color)).check(matches(isDisplayed()));
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.reservationTitle));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
    }

    @Test
    public void F_shouldFinishDetail(){
        generatingMeeting("Reunion 1",1,2028, 5, 15);
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.reservationTitle))
                .perform(click());
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.reservationTitle))
                .check(matches(withText("Reunion 1 - 15/04/2028 - Luigi")));
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
    }

    @Test
    public void G_shouldAddMyMailOnDetail(){
        generatingMeeting("Reunion 1",1,2028, 5, 15);
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.reservationTitle))
                .perform(click());
        onView(withId(R.id.participate_button)).perform(click());
        onView(withId(R.id.detail_participants_list)).check(matches(withText("\tparticipant1@mail.fr\n\tparticipant2@mail.fr\n\tXetiam@gmail.com\n")));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
    }
    @Test
    public void H_shouldDeleteMyMailOnDetail(){
        generatingMeeting("Reunion 1",1,2028, 5, 15);
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.reservationTitle))
                .perform(click());
        onView(withId(R.id.participate_button)).perform(click())
                .perform(click());
        onView(withId(R.id.detail_participants_list)).check(matches(withText("\tparticipant1@mail.fr\n\tparticipant2@mail.fr\n")));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
    }

    public void generatingMeetings() {
        generatingMeeting("Reunion 1",1,2028, 5, 15);
        generatingMeeting("Reunion 2",0,2026, 5, 15);
        generatingMeeting("Reunion 3",2,2024, 5, 15);
    }

    private void generatingMeeting(String name, int roomId, int year, int month, int day) {
        onView(withId(R.id.add_reservation)).perform(click());
        onView(withId(R.id.name)).perform(ViewActions.typeText(name));
        onView(withId(R.id.participants)).perform(ViewActions.typeText("participant1@mail.fr"));
        onView(withId(R.id.participantButton)).perform(click());
        onView(withId(R.id.participants)).perform(ViewActions.typeText("participant2@mail.fr"));
        onView(withId(R.id.participantButton)).perform(click());
        onView(withId(R.id.participants)).perform(closeSoftKeyboard());
        onView(withId(R.id.roomList))
                .perform(click());
        onData(anything()).atPosition(roomId).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month , day));
        onView(withId(R.id.create)).perform(scrollTo())
                .perform(click());
    }
    private void destroyMeetings() {
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
        onView(new RecyclerViewMatcher(R.id.container)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
    }
}
