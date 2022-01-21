package ru.kkuzmichev.simpleappforespresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)

public class simpleTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResources(){
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource);
    }

    @After
    public void unregisterIdlingResources(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource);
    }


    @Test
    public void textIsVisible() {
        ViewInteraction mainText = onView(
                withId(R.id.text_home)
        );
        mainText.check(
                matches(
                        withText("This is home fragment")
                )
        );
    }

    @Test
    public void intentsCheck () {
        ViewInteraction settings = onView(withParent(isAssignableFrom(ActionMenuView.class)));
        ViewInteraction settingsItem = onView(allOf(withId(R.id.title), withText("Settings")));

        settings.check(matches(isDisplayed()));
        settings.perform(click());

        settingsItem.check(matches(isDisplayed()));

        Intents.init();
        settingsItem.perform(click());
        intended(allOf(
                hasData("http://google.com"),
                hasAction(Intent.ACTION_VIEW)
        ));
        Intents.release();
    }


    @Test
    public void idlingsWork() {

        ViewInteraction menu = onView(withParent(isAssignableFrom(AppCompatImageButton.class)));
        menu.check(matches(isDisplayed()));
        menu.perform(click());
        ViewInteraction gallery = onView(withId(R.id.nav_gallery));
        ViewInteraction recycleList = onView(withId(R.id.recycle_view));
        ViewInteraction galleryItem = onView(allOf(withId(R.id.item_number), withText("7")));

        gallery.perform(click());

        gallery.check(matches(isDisplayed()));
        gallery.perform(click());
        galleryItem.check(
                matches(isDisplayed()));
    }
}
