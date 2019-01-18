package com.gianlucalimbi.blueprint

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dagger.android.support.DaggerFragment

abstract class BlueprintFragment : DaggerFragment(),
                                   BlueprintView {

  override fun <T> observeLiveData(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.observe(this, observer)
  }

}
