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

package com.gianlucalimbi.blueprint.observer

import androidx.lifecycle.LiveData
import com.gianlucalimbi.blueprint.Event

@DslMarker
internal annotation class DisposableEventObserverDsl

@DisposableEventObserverDsl
class DisposableEventObserverBuilder<T>(
  private val liveData: LiveData<Event<T>>
) {

  private var disposeWhen: ((Event<T>?) -> Boolean)? = { event ->
    event != null && event.hasBeenConsumed
  }

  private var disposeAction: ((LiveData<Event<T>>, EventObserver<T>) -> Unit)? = { liveData, observer ->
    liveData.removeObserver(observer)
  }

  private var withData: ((T) -> Unit)? = null
  private var onChanged: ((Event<T>?) -> Unit)? = null

  var includeConsumed: Boolean? = null

  fun disposeWhen(block: (Event<T>?) -> Boolean) {
    disposeWhen = block
  }

  fun disposeAction(block: (LiveData<Event<T>>, EventObserver<T>) -> Unit) {
    disposeAction = block
  }

  fun withData(block: (T) -> Unit) {
    withData = block
  }

  fun onChanged(block: (Event<T>?) -> Unit) {
    onChanged = block
  }

  fun build() = DisposableEventObserver(
      liveData,
      disposeWhen,
      disposeAction,
      withData,
      includeConsumed,
      onChanged
  )

}

fun <T> disposableEventObserver(
  liveData: LiveData<Event<T>>,
  block: DisposableEventObserverBuilder<T>.() -> Unit
): DisposableEventObserver<T> {
  return DisposableEventObserverBuilder(liveData).apply(block).build()
}

fun <T> disposableEventObserver(
  liveData: LiveData<Event<T>>,
  observer: EventObserver<T>
): DisposableEventObserver<T> {
  return DisposableEventObserverBuilder(liveData).apply {
    onChanged { observer.onChanged(it) }
  }.build()
}
