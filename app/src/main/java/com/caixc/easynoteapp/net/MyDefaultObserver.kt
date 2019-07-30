package com.caixc.easynoteapp.net

import android.content.Intent
import android.provider.SyncStateContract
import android.widget.Toast
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.base.BaseRxActivity
import com.caixc.easynoteapp.base.Preference
import com.caixc.easynoteapp.global.Constants
import com.caixc.easynoteapp.ui.LoginActivity
import com.caixc.easynoteapp.util.ActivityController
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class MyDefaultObserver<T> : Observer<T> {
    var mActivity: BaseActivity

    constructor(activity: BaseActivity){
        mActivity = activity
    }

    override fun onSubscribe(d: Disposable) {
        mActivity.addDisposable(d)
    }

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {
        mActivity.hideDialog()
        val retrofitException = RetrofitException.retrofitException(e)
        var code = retrofitException.code
        var message = retrofitException.message
        Toast.makeText(mActivity,"code$code \r\n message$message",Toast.LENGTH_LONG).show()
        if (code == 401) {
            ActivityController.removeAll()
            Preference().setValue(Constants.TOKEN,"")
            mActivity.startActivity(Intent(mActivity,LoginActivity::class.java))
        }
    }
}