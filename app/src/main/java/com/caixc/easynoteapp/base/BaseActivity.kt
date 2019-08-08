package com.caixc.easynoteapp.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.widget.CustomProgress
import com.gyf.barlibrary.ImmersionBar
import com.caixc.easynoteapp.util.ActivityController
import com.caixc.easynoteapp.util.LogUtils


abstract class BaseActivity : BaseRxActivity() {
    protected lateinit var immersionBar: ImmersionBar
    lateinit var mActivity: BaseActivity

     private var progress: CustomProgress? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        LogUtils.debug("当前界面："+javaClass.canonicalName)
        initImmersionBar()
        mActivity = this
        ActivityController.addActivity(mActivity)
        initView()
        initListener()
        refreshView()
        getData()
    }

    open fun initListener() {}

    open fun initView(){}

    open fun getData(){}

    open fun refreshView(){}

    fun showDialog() {
        if (progress == null) {
            progress = CustomProgress.build(mActivity,"请稍等")
        }
        progress!!.show()
    }

    fun hideDialog() {
        if (progress != null && progress!!.isShowing) {
            progress!!.dismiss()
            progress = null
        }
    }

    /**
     * 获取 InputMethodManager
     */
    private val imm: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun initImmersionBar() {
        immersionBar = ImmersionBar.with(this)
        immersionBar.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        immersionBar.destroy()
        ActivityController.removeActivity(mActivity)
    }

    override fun finish() {
        if (!isFinishing) {
            super.finish()
            hideSoftKeyBoard()
        }

    }

    /**
     * 隐藏软键盘
     */
    private fun hideSoftKeyBoard() {
        currentFocus?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 2)
        }
    }

    abstract fun setLayout(): Int

    fun Context.toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}