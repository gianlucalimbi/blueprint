package com.gianlucalimbi.blueprint

sealed class Resource<T>(
    val status: Status,
    open val data: T? = null,
    open val error: Exception? = null
) {

  companion object {

    @JvmStatic
    fun <T> success(data: T): Resource<T> = SuccessResource(data)

    @JvmStatic
    fun <T> error(error: Exception): Resource<T> = ErrorResource(error)

    @JvmStatic
    fun <T> loading(data: T? = null): Resource<T> = LoadingResource(data)

  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }

    if (other == null || this::class != other::class) {
      return false
    }

    val resource = other as Resource<*>

    return status == resource.status &&
           data == resource.data &&
           error == resource.error
  }

  override fun toString() = "Resource { status=$status, data=$data, error=$error }"

  override fun hashCode(): Int {
    var result = 31 * status.hashCode()
    result = 31 * result + (data?.hashCode() ?: 0)
    result = 31 * result + (error?.hashCode() ?: 0)

    return result
  }

  enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
  }

}

class SuccessResource<T>(
    override val data: T
) : Resource<T>(Resource.Status.SUCCESS, data = data, error = null)

class ErrorResource<T>(
    override val error: Exception
) : Resource<T>(Resource.Status.ERROR, data = null, error = error)

class LoadingResource<T>(
    override val data: T?
) : Resource<T>(Resource.Status.LOADING, data = data, error = null)
