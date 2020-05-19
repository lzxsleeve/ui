package com.sleeve.app

import android.app.Application
import android.content.Context
import androidx.annotation.CallSuper
import com.sleeve.net.NetConfig
import com.sleeve.util.UtilConfig
import me.yokeyword.fragmentation.Fragmentation

/**
 * Create by admin on 2019/9/24
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        UtilConfig.init(this)
        init(this)
    }

    @CallSuper
    open fun init(context: Application) {

        Fragmentation.builder()
            .stackViewMode(Fragmentation.BUBBLE)
            .debug(BuildConfig.DEBUG)
            .install()

        NetConfig.init()

    }

}
