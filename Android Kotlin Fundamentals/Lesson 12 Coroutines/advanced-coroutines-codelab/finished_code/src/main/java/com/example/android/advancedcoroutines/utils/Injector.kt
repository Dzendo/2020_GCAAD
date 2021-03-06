package com.example.android.advancedcoroutines.utils

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.android.advancedcoroutines.NetworkService
import com.example.android.advancedcoroutines.ui.PlantListViewModelFactory
import com.example.android.advancedcoroutines.PlantRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
interface ViewModelFactoryProvider {
    fun providePlantListViewModelFactory(context: Context): PlantListViewModelFactory
}

@ExperimentalCoroutinesApi
@FlowPreview
val Injector: ViewModelFactoryProvider
    get() = currentInjector

@FlowPreview
@ExperimentalCoroutinesApi
private object DefaultViewModelProvider: ViewModelFactoryProvider {
    private fun getPlantRepository(context: Context): PlantRepository {
        return PlantRepository.getInstance(
            plantDao(context),
            plantService()
        )
    }

    private fun plantService() = NetworkService()

    private fun plantDao(context: Context) =
        AppDatabase.getInstance(context.applicationContext).plantDao()

    override fun providePlantListViewModelFactory(context: Context): PlantListViewModelFactory {
        val repository = getPlantRepository(context)
        return PlantListViewModelFactory(repository)
    }
}
@ExperimentalCoroutinesApi
private object Lock

@ExperimentalCoroutinesApi
@FlowPreview
@Volatile private var currentInjector: ViewModelFactoryProvider =
    DefaultViewModelProvider


@FlowPreview
@ExperimentalCoroutinesApi
@VisibleForTesting
private fun setInjectorForTesting(injector: ViewModelFactoryProvider?) {
    synchronized(Lock) {
        currentInjector = injector ?: DefaultViewModelProvider
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
@VisibleForTesting
private fun resetInjector() =
    setInjectorForTesting(null)