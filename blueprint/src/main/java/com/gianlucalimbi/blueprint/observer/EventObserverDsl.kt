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

import androidx.lifecycle.Observer
import com.gianlucalimbi.blueprint.Event

@DslMarker
internal annotation class EventObserverDsl

@ResourceObserverDsl
class EventObserverBuilder<T> {

  private var withData: ((T) -> Unit)? = null
  private var onChanged: ((Event<T>?) -> Unit)? = null

  var includeConsumed: Boolean? = null

  fun withData(block: (T) -> Unit) {
    withData = block
  }

  fun onChanged(block: (Event<T>?) -> Unit) {
    onChanged = block
  }

  fun build() = EventObserver(withData, includeConsumed, onChanged)

}

fun <T> eventObserver(block: EventObserverBuilder<T>.() -> Unit): EventObserver<T> {
  return EventObserverBuilder<T>().apply(block).build()
}

fun <T> eventObserver(observer: Observer<T>): EventObserver<T> {
  return EventObserverBuilder<T>().apply {
    withData { observer.onChanged(it) }
  }.build()
}
