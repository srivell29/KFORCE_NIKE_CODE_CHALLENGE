package com.example.dictionary.dictionaryviews


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.dictionary.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class DictionaryActivityTest {
    @get:Rule
     val dictionaryActivityRule = ActivityTestRule<DictionaryActivity>(DictionaryActivity::class.java)

    /**
     * Check Progress bar and Error UI based on success API response
     */
    @Test
    fun performSearch_Check_Progress_Error() {
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.et_search)).perform(typeText("test"), closeSoftKeyboard())
        Thread.sleep(1500)
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.groupError)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        assert(getSearchCount()>0)
    }

    /**
     * Check Progress bar and Error UI based on error API response
     */
    @Test
    fun performDummySearch_Check_Progress_Error() {
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.et_search)).perform(typeText("cscscscsdcsdcsc"), closeSoftKeyboard())
        Thread.sleep(1500)
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.groupError)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        assert(getSearchCount()>0)
    }

    private fun getSearchCount(): Int {
        val recyclerView = dictionaryActivityRule.activity.findViewById(R.id.rv_SearchSuggestions) as RecyclerView
        return recyclerView.adapter!!.itemCount
    }
}
