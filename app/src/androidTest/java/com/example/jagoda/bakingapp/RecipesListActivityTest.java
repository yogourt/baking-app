package com.example.jagoda.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.jagoda.bakingapp.view.recipeList.RecipeListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.logging.Handler;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(JUnit4.class)
public class RecipesListActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> activityTestRule =
            new ActivityTestRule<RecipeListActivity>(RecipeListActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(activityTestRule.getActivity().getIdlingResource());
    }

    @Test
    public void clickRecipeListItem_OpensStepListActivity() {
        onView(withId(R.id.recipes_list)).perform(RecyclerViewActions.
                actionOnItemAtPosition(0, click()));

        onView(withId(R.id.ingredients_list_view)).check(matches(isDisplayed()));
        onView(withId(R.id.steps_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void clickStepListItem_OpensStepDetailsActivity() {
        onView(withId(R.id.recipes_list)).perform(RecyclerViewActions.
                actionOnItemAtPosition(0, click()));

        onView(withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.
                actionOnItemAtPosition(0, click()));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.step_detailed_desc_text_view)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(activityTestRule.getActivity().getIdlingResource());
    }

}
