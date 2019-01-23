package com.gianlucalimbi.blueprint

import java.lang.ref.WeakReference

class BasePresenter<T : BlueprintView>(
    view: T
) : BlueprintPresenter<T> {

  override val viewReference = WeakReference(view)

}
