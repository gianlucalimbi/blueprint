package com.gianlucalimbi.blueprint.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(viewModelClass: Class<T>): T {
    var creator: Provider<out ViewModel>? = creators[viewModelClass]

    if (creator == null) {
      for (creatorClass in creators.keys) {
        if (viewModelClass.isAssignableFrom(creatorClass)) {
          creator = creators[creatorClass]
          break
        }
      }
    }

    if (creator == null) {
      throw IllegalArgumentException("unknown model class $viewModelClass")
    }

    try {
      return creator.get() as T
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }

}
