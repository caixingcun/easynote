package com.caixc.easynoteapp.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.gyf.barlibrary.ImmersionBar

abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var immersionBar: ImmersionBar
     lateinit var mActivity : BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        initImmersionBar()
        mActivity = this
        initView()
        initListener()
        refeshView()
        getData()

    }

    open fun initListener() {}

    fun initView() {}

    abstract fun getData()

    abstract fun refeshView()

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