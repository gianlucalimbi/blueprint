package com.gianlucalimbi.blueprint

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

interface BlueprintView {

  fun <T> observeLiveData(
      liveData: LiveData<T>,
      observer: Observer<T>
  )

  fun <T> observeLiveData(
      liveData: LiveData<T>,
      block: (T) -> Unit
  )

  fun <T> observeResourceLiveData(
      liveData: LiveData<Resource<T>>,
      observer: ResourceObserver<T>
  )

  fun <T> observeResourceLiveData(
      liveData: LiveData<Resource<T>>,
      block: ResourceObserverBuilder<T>.() -> Unit
  )

}
