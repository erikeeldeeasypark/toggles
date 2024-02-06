package se.eelde.toggles

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import dagger.Module
import dagger.Provides
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import se.eelde.toggles.di.GreetingFactory
import se.eelde.toggles.di.SingleInstanceClass
import se.eelde.toggles.di.SingleInstanceModule
import javax.inject.Inject
import javax.inject.Singleton
//
//@Module
//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [SingleInstanceModule::class]
//)
//class TestModule {
//    @Provides
//    @Singleton
//    fun provideSingleInstanceClass(): SingleInstanceClass =
//        SingleInstanceClass()
//
//    @Provides
//    @Singleton
//    fun provideGreetingFactory(): GreetingFactory =
//        object : GreetingFactory {
//            override fun createGreeting(): String = "Test greeting"
//        }
//}



@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentationTest {

    @get:Rule(order = 0)
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var greetingFactory: GreetingFactory

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun useAppContext() = runTest {
        composeTestRule.onAllNodesWithText("Applications")[0].assertExists()
        Thread.sleep(3000)
        assertThat("Test greeting").isEqualTo(greetingFactory.createGreeting())
        // composeTestRule.onNodeWithText("No Applications found.").assertExists()
    }
}
