package com.gianlucalimbi.blueprint

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

interface BlueprintView {

  fun <T> observeLiveData(liveData: LiveData<T>, observer: Observer<T>)

}
