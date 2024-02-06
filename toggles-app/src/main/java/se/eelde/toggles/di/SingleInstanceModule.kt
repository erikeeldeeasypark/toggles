package se.eelde.toggles.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Singleton

class GreetingFactoryImpl(private val singleInstanceClass: SingleInstanceClass) : GreetingFactory {
    override fun createGreeting(): String = "Original greeting ${singleInstanceClass.getCount()}"
}

class SingleInstanceClass {
    fun getCount(): Int = counter.get()

    init {
        val currentCounter = counter.getAndIncrement()
        if (currentCounter > 0) {
            throw IllegalStateException("SingleInstanceClass should only be created once")
        }
    }

    companion object {
        var counter: AtomicInteger = AtomicInteger(0)
    }
}

interface GreetingFactory {
    fun createGreeting(): String
}

@Module
@InstallIn(SingletonComponent::class)
class SingleInstanceModule {
    @Provides
    @Singleton
    fun provideSingleInstanceClass(): SingleInstanceClass =
        SingleInstanceClass()

    @Provides
    @Singleton
    fun provideGreetingFactory(singleInstanceClass: SingleInstanceClass): GreetingFactory =
        GreetingFactoryImpl(singleInstanceClass)
}