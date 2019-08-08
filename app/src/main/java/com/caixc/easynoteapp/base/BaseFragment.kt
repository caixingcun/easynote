package com.caixc.easynoteapp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(context, setLayout(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initListener()
        getData()
    }

    open fun getData() {}

    open fun initListener(){}

    abstract fun initView()

    abstract fun setLayout(): Int

    fun getBaseActivity(): BaseActivity {
        return activity as BaseActivity
    }
}