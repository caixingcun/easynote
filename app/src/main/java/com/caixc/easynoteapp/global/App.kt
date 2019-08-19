package com.caixc.easynoteapp.global

import android.app.Application
import com.caixc.easynoteapp.base.Preference
import com.qq.wx.voice.recognizer.VoiceRecognizer
import com.qq.wx.voice.recognizer.VoiceRecognizerListener
import com.qq.wx.voice.recognizer.VoiceRecognizerResult
import com.qq.wx.voice.recognizer.VoiceRecordState
import com.qq.wx.voice.synthesizer.SpeechSynthesizer
import com.qq.wx.voice.synthesizer.SpeechSynthesizerListener
import com.qq.wx.voice.synthesizer.SpeechSynthesizerResult
import com.qq.wx.voice.synthesizer.SpeechSynthesizerState

class App : Application() {


    override fun onCreate() {
        super.onCreate()
//        初始化sp
        Preference.setContext(applicationContext)

        val shareInstance = SpeechSynthesizer.shareInstance()
        shareInstance.setListener(object:SpeechSynthesizerListener{
            override fun onGetVoiceRecordState(p0: SpeechSynthesizerState?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onGetResult(p0: SpeechSynthesizerResult?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onGetError(p0: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        shareInstance.setFormat(0)
        shareInstance.setVolume(1.0f)
        shareInstance.init(this, "key")

    }

    companion object {
      const val debug = true
    }

}
