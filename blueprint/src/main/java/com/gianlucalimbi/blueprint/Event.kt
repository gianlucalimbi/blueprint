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

package com.gianlucalimbi.blueprint

import java.util.concurrent.atomic.AtomicBoolean

/**
 * Wrapper of information that should only be consumed once.
 */
data class Event<T>(
  private val data: T
) {

  private val consumed = AtomicBoolean(false)

  var hasBeenConsumed
    get() = consumed.get()
    private set(value) {
      consumed.set(value)
    }

  fun withData(includeConsumed: Boolean = false, block: (T) -> Unit) {
    if (includeConsumed || !hasBeenConsumed) {
      hasBeenConsumed = true

      block(data)
    }
  }

}
