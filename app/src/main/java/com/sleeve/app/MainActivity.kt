package com.sleeve.app

import android.content.Intent
import android.os.Bundle
import com.sleeve.ui.base.BaseUIA

class MainActivity : BaseUIA() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addContentView()

        if (findFragment(MainUIF::class.java) == null) {
            loadFragment(MainUIF())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        fragments.forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }
}
