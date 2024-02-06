package se.eelde.toggles

import android.content.Context
import androidx.startup.Initializer
import dagger.hilt.InstallIn
import dagger.hilt.android.EarlyEntryPoint
import dagger.hilt.android.EarlyEntryPoints
import dagger.hilt.components.SingletonComponent
import se.eelde.toggles.di.GreetingFactory
import javax.inject.Inject

@EarlyEntryPoint
@InstallIn(SingletonComponent::class)
interface InitializerEntryPoint {

    fun inject(workManagerInitializer: WorkManagerInitializer)

    companion object {
        fun resolve(context: Context): InitializerEntryPoint {
            checkNotNull(context.applicationContext)
            val appContext = context.applicationContext
            return EarlyEntryPoints.get(appContext, InitializerEntryPoint::class.java)
        }
    }
}

class WorkManagerInitializer : Initializer<GreetingFactory> {
    @Inject
    lateinit var greetingFactory: GreetingFactory
    override fun create(context: Context): GreetingFactory {
        InitializerEntryPoint.resolve(context).inject(this)
        assert(greetingFactory.createGreeting() == "Original greeting 1")
        return object : GreetingFactory {
            override fun createGreeting(): String = "Initializer greeting"
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // No dependencies on other libraries.
        return emptyList()
    }
}