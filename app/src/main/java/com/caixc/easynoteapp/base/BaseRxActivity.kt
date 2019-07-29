package com.caixc.easynoteapp.base

import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseRxActivity : AppCompatActivity() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addDisposable(d: Disposable) {
        compositeDisposable.add(d)
    }

    private fun dispose() {
        compositeDisposable?.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        dispose()
    }


}
