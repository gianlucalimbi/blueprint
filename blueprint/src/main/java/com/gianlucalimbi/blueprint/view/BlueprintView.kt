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
import com.gianlucalimbi.blueprint.observer.DisposableEventObserverBuilder
import com.gianlucalimbi.blueprint.observer.DisposableObserverBuilder
import com.gianlucalimbi.blueprint.observer.DisposableResourceObserverBuilder
import com.gianlucalimbi.blueprint.observer.EventObserver
import com.gianlucalimbi.blueprint.observer.EventObserverBuilder
import com.gianlucalimbi.blueprint.observer.ResourceObserver
import com.gianlucalimbi.blueprint.observer.ResourceObserverBuilder

interface BlueprintView {

  fun <T> observeLiveData(
    liveData: LiveData<T>,
    observer: Observer<T>
  )

  fun <T> observeLiveData(
    liveData: LiveData<T>,
    block: (T?) -> Unit
  )

  fun <T> observeResourceLiveData(
    liveData: LiveData<Resource<T>>,
    observer: ResourceObserver<T>
  )

  fun <T> observeResourceLiveData(
    liveData: LiveData<Resource<T>>,
    block: ResourceObserverBuilder<T>.() -> Unit
  )

  fun <T> observeEventLiveData(
    liveData: LiveData<Event<T>>,
    observer: EventObserver<T>
  )

  fun <T> observeEventLiveData(
    liveData: LiveData<Event<T>>,
    block: EventObserverBuilder<T>.() -> Unit
  )

  fun <T> observeLiveDataOnce(
    liveData: LiveData<T>,
    observer: Observer<T>
  )

  fun <T> observeLiveDataOnce(
    liveData: LiveData<T>,
    block: DisposableObserverBuilder<T>.() -> Unit
  )

  fun <T> observeResourceLiveDataOnce(
    liveData: LiveData<Resource<T>>,
    observer: ResourceObserver<T>
  )

  fun <T> observeResourceLiveDataOnce(
    liveData: LiveData<Resource<T>>,
    block: DisposableResourceObserverBuilder<T>.() -> Unit
  )

  fun <T> observeEventLiveDataOnce(
    liveData: LiveData<Event<T>>,
    observer: EventObserver<T>
  )

  fun <T> observeEventLiveDataOnce(
    liveData: LiveData<Event<T>>,
    block: DisposableEventObserverBuilder<T>.() -> Unit
  )

}
