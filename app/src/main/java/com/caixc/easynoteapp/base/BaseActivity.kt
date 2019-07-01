package com.caixc.easynoteapp.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.gyf.barlibrary.ImmersionBar

abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var immersionBar :ImmersionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        initImmersionBar()
        initView()
        initListener()
        refeshView()
        getData()

    }

     open fun initListener(){}

     fun initView(){}

    abstract fun getData()

    abstract fun refeshView()

    /**
     * 获取 InputMethodManager
     */
    private  val  imm: InputMethodManager by lazy {
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
            imm.hideSoftInputFromWindow(it.windowToken,2)
        }
    }

    abstract fun setLayout(): Int



}