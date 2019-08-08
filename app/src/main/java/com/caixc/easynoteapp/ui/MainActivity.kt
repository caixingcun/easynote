package com.caixc.easynoteapp.ui

import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.base.BaseFragment
import com.caixc.easynoteapp.ui.fragment.InOutComeFragment
import com.caixc.easynoteapp.ui.fragment.MineFragment
import com.caixc.easynoteapp.ui.fragment.NoteFragment
import com.caixc.easynoteapp.ui.fragment.ZaiQuanFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_main

     var fragments: MutableList<BaseFragment> = mutableListOf()

    override fun initView() {
        fragments.add(NoteFragment())
        fragments.add(InOutComeFragment())
        fragments.add(ZaiQuanFragment())
        fragments.add(MineFragment())

        supportFragmentManager.beginTransaction()
            .add(R.id.fl, fragments[0])
            .add(R.id.fl, fragments[1])
            .add(R.id.fl, fragments[2])
            .add(R.id.fl, fragments[3])
            .hide(fragments[0])
            .hide(fragments[1])
            .hide(fragments[2])
            .hide(fragments[3])
            .show(fragments[0])
            .commit()

        navigation_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tab_note -> switchFragment(0)
                R.id.tab_account -> switchFragment(1)
                R.id.tab_money -> switchFragment(2)
                R.id.tab_mine -> switchFragment(3)
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
            .show(fragments[pos])
            .commit()

    }
}
