package com.gianlucalimbi.blueprint

import androidx.lifecycle.Observer

class ResourceObserver<T> internal constructor(
    private val onSuccess: (data: T) -> Unit = { },
    private val onError: (error: Exception) -> Unit = { },
    private val onLoading: (data: T?) -> Unit = { },
    private val onChanged: (resource: Resource<T>?) -> Unit = { }
) : Observer<Resource<T>> {

  override fun onChanged(resource: Resource<T>?) {
    resource?.let {
      when (it) {
        is SuccessResource -> onSuccess.invoke(it.data)
        is ErrorResource -> onError.invoke(it.error)
        is LoadingResource -> onLoading.invoke(it.data)
      }
    }

    onChanged.invoke(resource)
  }

}
