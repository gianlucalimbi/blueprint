package com.gianlucalimbi.blueprint

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dagger.android.support.DaggerDialogFragment

class BlueprintDialogFragment : DaggerDialogFragment(),
                                BlueprintView {

  override fun <T> observeLiveData(
    liveData: LiveData<T>,
    observer: Observer<T>
  ) {
    liveData.observe(this, observer)
  }

  override fun <T> observeLiveData(
    liveData: LiveData<T>,
    block: (T) -> Unit
  ) {
    liveData.observe(this, Observer(block))
  }

  override fun <T> observeResourceLiveData(
    liveData: LiveData<Resource<T>>,
    observer: ResourceObserver<T>
  ) {
    liveData.observe(this, observer)
  }

  override fun <T> observeResourceLiveData(
    liveData: LiveData<Resource<T>>,
    block: ResourceObserverBuilder<T>.() -> Unit
  ) {
    liveData.observe(this, resourceObserver(block))
  }

}
