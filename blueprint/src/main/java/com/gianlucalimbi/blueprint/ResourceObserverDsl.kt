package com.gianlucalimbi.blueprint

@DslMarker
internal annotation class ResourceObserverDsl

@ResourceObserverDsl
class ResourceObserverBuilder<T> internal constructor() {

  private var onSuccess: (data: T) -> Unit = { }
  private var onError: (error: Exception) -> Unit = { }
  private var onLoading: (data: T?) -> Unit = { }
  private var onChanged: (resource: Resource<T>?) -> Unit = { }

  fun onSuccess(block: (data: T) -> Unit) {
    onSuccess = block
  }

  fun onError(block: (data: Exception) -> Unit) {
    onError = block
  }

  fun onLoading(block: (data: T?) -> Unit) {
    onLoading = block
  }

  fun onChanged(block: (resource: Resource<T>?) -> Unit) {
    onChanged = block
  }

  fun build() = ResourceObserver(onSuccess, onError, onLoading, onChanged)

}

fun <T> resourceObserver(block: ResourceObserverBuilder<T>.() -> Unit): ResourceObserver<T> {
  return ResourceObserverBuilder<T>().apply(block).build()
}
