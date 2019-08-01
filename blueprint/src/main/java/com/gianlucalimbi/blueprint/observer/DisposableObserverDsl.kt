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
import androidx.lifecycle.Observer

@DslMarker
internal annotation class DisposableObserverDsl

@DisposableObserverDsl
class DisposableObserverBuilder<T>(
  private val liveData: LiveData<T>
) {

  private var disposeWhen: ((T?) -> Boolean)? = { data ->
    data != null
  }

  private var disposeAction: ((LiveData<T>, Observer<T>) -> Unit)? = { liveData, observer ->
    liveData.removeObserver(observer)
  }

  private var onChanged: ((T?) -> Unit)? = null

  fun disposeWhen(block: (T?) -> Boolean) {
    disposeWhen = block
  }

  fun disposeAction(block: (LiveData<T>, Observer<T>) -> Unit) {
    disposeAction = block
  }

  fun onChanged(block: (T?) -> Unit) {
    onChanged = block
  }

  fun build() = DisposableObserver(
      liveData,
      disposeWhen,
      disposeAction,
      onChanged
  )

}

fun <T> disposableObserver(
  liveData: LiveData<T>,
  block: DisposableObserverBuilder<T>.() -> Unit
): DisposableObserver<T> {
  return DisposableObserverBuilder(liveData).apply(block).build()
}

fun <T> disposableObserver(
  liveData: LiveData<T>,
  observer: Observer<T>
): DisposableObserver<T> {
  return DisposableObserverBuilder(liveData).apply {
    onChanged { observer.onChanged(it) }
  }.build()
}
