import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import com.dicoding.courseschedule.ui.home.HomeActivity
import org.junit.*
import org.junit.runner.*

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @Test
    fun toAddCourseActivity() {
        ActivityScenario.launch(HomeActivity::class.java)
        Espresso.onView(withId(R.id.action_add)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Intents.init()
        Espresso.onView(withId(R.id.action_add)).perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent((AddCourseActivity::class.java.name)))
        Intents.release()
    }

}
