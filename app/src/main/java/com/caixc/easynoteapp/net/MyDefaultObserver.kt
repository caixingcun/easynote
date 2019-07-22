package com.caixc.easynoteapp.net

import android.widget.Toast
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.base.BaseRxActivity
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class MyDefaultObserver<T> : Observer<T> {
    lateinit var mActivity: BaseRxActivity

    constructor(activity: BaseRxActivity){
        mActivity = activity
    }

    override fun onSubscribe(d: Disposable) {
        mActivity.addDisposable(d)
    }

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {
        val retrofitException = RetrofitException.retrofitException(e)
        var code = retrofitException.code
        var message = retrofitException.message
        Toast.makeText(mActivity,"code${code}message$message",Toast.LENGTH_LONG).show()
    }
}