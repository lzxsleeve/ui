package com.sleeve.app

import android.content.Intent
import android.os.Bundle
import com.sleeve.ui.base.BaseUIA
import com.sleeve.ui.statusBar.StatusBarUtil

class MainActivity : BaseUIA() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addContentView()

        if (findFragment(MainUIF::class.java) == null) {
            loadFragment(MainUIF())
        }

        // 隐藏状态栏
        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
        StatusBarUtil.setTranslucentStatus(this)
        StatusBarUtil.setStatusBarDarkTheme(this, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        fragments.forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }
}
