package com.gianlucalimbi.blueprint

import java.lang.ref.WeakReference

interface BlueprintPresenter<T : BlueprintView> {

  val viewReference: WeakReference<T>

  val view: T?
    get() = viewReference.get()

}
