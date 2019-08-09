package com.caixc.easynoteapp.ui.activity

import android.text.TextUtils
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.LoginResultBean
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.service.LoginService
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.util.ConstUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_change_pwd.*
import kotlinx.android.synthetic.main.activity_login.btn
import kotlinx.android.synthetic.main.activity_login.et_account
import kotlinx.android.synthetic.main.activity_login.et_pwd

class ChangePwdActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_change_pwd

    override fun initListener() {

        btn.setOnClickListener {
            val account = et_account.text.toString().trim()
            val pwd = et_pwd.text.toString().trim()
            val pwdAgain = et_pwd_again.text.toString().trim()
            if (!emptyCheck(account, pwd, pwdAgain)) {
                return@setOnClickListener
            }
            changePwd(account,pwd,pwdAgain)
        }
    }

    private fun changePwd(account: String,pwd: String,pwdAgain: String) {
        mActivity.showDialog()
        RetrofitClient().getInstance(Urls.HOST)
            .create(LoginService::class.java)
            .changePwd(account,pwd,pwdAgain)
            .doOnSubscribe {
                mActivity.addDisposable(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<LoginResultBean>(mActivity) {
                override fun onNext(l: LoginResultBean) {
                    mActivity.hideDialog()
                    toast("修改成功")
                    finish()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                }
            })
    }

    private fun emptyCheck(account: String, pwd: String, pwdAgain: String): Boolean {
        if (!account.matches(Regex(ConstUtils.REGEX_MOBILE_SIMPLE))) {
            toast("手机号格式有误")
            return false
        }
        if (TextUtils.isEmpty(pwd)) {
            toast("密码不能为空")
            return false
        }
        if (TextUtils.isEmpty(pwdAgain)) {
            toast("再次密码不能为空")
            return false
        }
        return true

    }

}