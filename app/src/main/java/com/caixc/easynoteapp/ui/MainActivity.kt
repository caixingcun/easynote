package com.caixc.easynoteapp.ui

import android.content.Intent
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.base.BaseFragment
import com.caixc.easynoteapp.bean.TemplatureBean
import com.caixc.easynoteapp.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_main

     var fragments: MutableList<BaseFragment> = mutableListOf()

    override fun initView() {
        fragments.add(NoteFragment())
        fragments.add(InOutComeFragment())
        fragments.add(TemplatureFragment())
        fragments.add(ZaiQuanFragment())
        fragments.add(MineFragment())

        supportFragmentManager.beginTransaction()
            .add(R.id.fl, fragments[0])
            .add(R.id.fl, fragments[1])
            .add(R.id.fl, fragments[2])
            .add(R.id.fl, fragments[3])
            .add(R.id.fl, fragments[4])
            .hide(fragments[0])
            .hide(fragments[1])
            .hide(fragments[2])
            .hide(fragments[3])
            .hide(fragments[4])
            .show(fragments[0])
            .commit()

        navigation_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tab_note -> switchFragment(0)
                R.id.tab_account -> switchFragment(1)
                R.id.tab_temp -> switchFragment(2)
                R.id.tab_money -> switchFragment(3)
                R.id.tab_mine -> switchFragment(4)
            }
            true
        }

    }


    private fun switchFragment(pos: Int) {
        supportFragmentManager.beginTransaction()
            .hide(fragments[0])
            .hide(fragments[1])
            .hide(fragments[2])
            .hide(fragments[3])
            .hide(fragments[4])
            .show(fragments[pos])
            .commit()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
