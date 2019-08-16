package com.caixc.easynoteapp.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType

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

    fun takePhoto() {
        PictureSelector.create(this)
            .openCamera(PictureMimeType.ofImage())
            .compress(true)
            .minimumCompressSize(300)
            .forResult(PictureConfig.REQUEST_CAMERA)
    }

    fun chooseImage(imageSize: Int) {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .maxSelectNum(imageSize)
            .compress(true)
            .minimumCompressSize(300)
            .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    fun Context.toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}