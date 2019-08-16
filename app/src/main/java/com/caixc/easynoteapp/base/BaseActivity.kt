package com.caixc.easynoteapp.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.caixc.easynoteapp.widget.CustomProgress
import com.gyf.barlibrary.ImmersionBar
import com.caixc.easynoteapp.util.ActivityController
import com.caixc.easynoteapp.util.LogUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType


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

    fun formatJson(jsonStr: String?): String {
        if (null == jsonStr || "" == jsonStr) {
            return ""
        }
        val sb = StringBuilder()
        var last = '\u0000'
        var current = '\u0000'
        var indent = 0
        var isInQuotationMarks = false
        for (i in 0 until jsonStr.length) {
            last = current
            current = jsonStr[i]
            when (current) {
                '"' -> {
                    if (last != '\\') {
                        isInQuotationMarks = !isInQuotationMarks
                    }
                    sb.append(current)
                }

                '{', '[' -> {
                    sb.append(current)
                    if (!isInQuotationMarks) {
                        sb.append('\n')
                        indent++
                        addIndentBlank(sb, indent)
                    }
                }

                '}', ']' -> {
                    if (!isInQuotationMarks) {
                        sb.append('\n')
                        indent--
                        addIndentBlank(sb, indent)
                    }
                    sb.append(current)
                }

                ',' -> {
                    sb.append(current)
                    if (last != '\\' && !isInQuotationMarks) {
                        sb.append('\n')
                        addIndentBlank(sb, indent)
                    }
                }

                else -> sb.append(current)
            }
        }
        return sb.toString()
    }

    private fun addIndentBlank(sb: StringBuilder, indent: Int) {
        for (i in 0 until indent) {
            sb.append('\t')
        }
    }

    fun takePhoto() {
        PictureSelector.create(mActivity)
            .openCamera(PictureMimeType.ofImage())
            .compress(true)
            .minimumCompressSize(300)
            .forResult(PictureConfig.REQUEST_CAMERA)
    }

    fun chooseImage(imageSize: Int) {
        PictureSelector.create(mActivity)
            .openGallery(PictureMimeType.ofImage())
            .maxSelectNum(imageSize)
            .compress(true)
            .minimumCompressSize(300)
            .forResult(PictureConfig.CHOOSE_REQUEST)
    }
}