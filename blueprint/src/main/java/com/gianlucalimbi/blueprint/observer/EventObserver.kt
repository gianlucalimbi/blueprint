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

open class EventObserver<T>(
  private val withData: ((T) -> Unit)?,
  private val includeConsumed: Boolean?,
  private val onChanged: ((Event<T>?) -> Unit)?
) : Observer<Event<T>> {

  override fun onChanged(event: Event<T>?) {
    onChanged?.invoke(event)

    event?.withData(includeConsumed ?: false) { withData?.invoke(it) }
  }

}
