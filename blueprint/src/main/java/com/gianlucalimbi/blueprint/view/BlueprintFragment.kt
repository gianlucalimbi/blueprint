/*
 * Copyright 2019 S24 S.p.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gianlucalimbi.blueprint.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.gianlucalimbi.blueprint.Event
import com.gianlucalimbi.blueprint.Resource
import com.gianlucalimbi.blueprint.observeEventOnce
import com.gianlucalimbi.blueprint.observeOnce
import com.gianlucalimbi.blueprint.observeResourceOnce
import com.gianlucalimbi.blueprint.observer.DisposableEventObserverBuilder
import com.gianlucalimbi.blueprint.observer.DisposableObserverBuilder
import com.gianlucalimbi.blueprint.observer.DisposableResourceObserverBuilder
import com.gianlucalimbi.blueprint.observer.EventObserver
import com.gianlucalimbi.blueprint.observer.EventObserverBuilder
import com.gianlucalimbi.blueprint.observer.ResourceObserver
import com.gianlucalimbi.blueprint.observer.ResourceObserverBuilder
import com.gianlucalimbi.blueprint.observer.eventObserver
import com.gianlucalimbi.blueprint.observer.resourceObserver
import dagger.android.support.DaggerFragment

abstract class BlueprintFragment : DaggerFragment(),
                                   BlueprintView {

  override fun <T> observeLiveData(
    liveData: LiveData<T>,
    observer: Observer<T>
  ) {
    val owner = try {
      viewLifecycleOwner
    } catch (e: IllegalStateException) {
      return
    }

    liveData.observe(owner, observer)
  }

  override fun <T> observeLiveData(
    liveData: LiveData<T>,
    block: (T?) -> Unit
  ) {
    val owner = try {
      viewLifecycleOwner
    } catch (e: IllegalStateException) {
      return
    }

    liveData.observe(owner, Observer(block))
  }

  override fun <T> observeResourceLiveData(
    liveData: LiveData<Resource<T>>,
    observer: ResourceObserver<T>
  ) {
    val owner = try {
      viewLifecycleOwner
    } catch (e: IllegalStateException) {
      return
    }

    liveData.observe(owner, observer)
  }

  override fun <T> observeResourceLiveData(
    liveData: LiveData<Resource<T>>,
    block: ResourceObserverBuilder<T>.() -> Unit
  ) {
    val owner = try {
      viewLifecycleOwner
    } catch (e: IllegalStateException) {
      return
    }

    liveData.observe(owner, resourceObserver(block))
  }

  override fun <T> observeEventLiveData(
    liveData: LiveData<Event<T>>,
    observer: EventObserver<T>
  ) {
    val owner = try {
      viewLifecycleOwner
    } catch (e: IllegalStateException) {
      return
    }

    liveData.observe(owner, observer)
  }

  override fun <T> observeEventLiveData(
    liveData: LiveData<Event<T>>,
    block: EventObserverBuilder<T>.() -> Unit
  ) {
    val owner = try {
      viewLifecycleOwner
    } catch (e: IllegalStateException) {
      return
    }

    liveData.observe(owner, eventObserver(block))
  }

  override fun <T> observeLiveDataOnce(
    liveData: LiveData<T>,
    observer: Observer<T>
  ) {
    val owner = try {
      viewLifecycleOwner
    } catch (e: IllegalStateException) {
      return
    }

    liveData.observeOnce(owner, observer)
  }

  override fun <T> observeLiveDataOnce(
    liveData: LiveData<T>,
    block: DisposableObserverBuilder<T>.() -> Unit
  ) {
    val owner = try {
      viewLifecycleOwner
    } catch (e: IllegalStateException) {
      return
    }

    liveData.observeOnce(owner, block)
  }

  override fun <T> observeResourceLiveDataOnce(
    liveData: LiveData<Resource<T>>,
    observer: ResourceObserver<T>
  ) {
    val owner = try {
      viewLifecycleOwner
    } catch (e: IllegalStateException) {
      return
    }

    liveData.observeResourceOnce(owner, observer)
  }

  override fun <T> observeResourceLiveDataOnce(
    liveData: LiveData<Resource<T>>,
    block: DisposableResourceObserverBuilder<T>.() -> Unit
  ) {
    val owner = try {
      viewLifecycleOwner
    } catch (e: IllegalStateException) {
      return
    }

    liveData.observeResourceOnce(owner, block)
  }

  override fun <T> observeEventLiveDataOnce(
    liveData: LiveData<Event<T>>,
    observer: EventObserver<T>
  ) {
    val owner = try {
      viewLifecycleOwner
    } catch (e: IllegalStateException) {
      return
    }

    liveData.observeEventOnce(owner, observer)
  }

  override fun <T> observeEventLiveDataOnce(
    liveData: LiveData<Event<T>>,
    block: DisposableEventObserverBuilder<T>.() -> Unit
  ) {
    val owner = try {
      viewLifecycleOwner
    } catch (e: IllegalStateException) {
      return
    }

    liveData.observeEventOnce(owner, block)
  }

}
