package com.sleeve.app

import android.util.Log
import android.view.View
import com.sleeve.net.NetCallback
import com.sleeve.net.NetRequestFactory
import com.sleeve.ui.load.LoadHeadBarUIF
import com.sleeve.ui.view.HeadBar
import kotlinx.android.synthetic.main.uif_main.*
import okhttp3.ResponseBody

/**
 * 说明
 *
 *
 * Create by lzx on 2020/05/19.
 */
class MainUIF : LoadHeadBarUIF(), View.OnClickListener {
    override fun initHeadBar(headBar: HeadBar) {
        headBar.setCenterTitle("首页")
    }

    override fun getContentLayout(): Int {
        return R.layout.uif_main
    }

    override fun initView() {
        setSwipeBackEnable(false)
        main_request.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            main_request -> {
                requestTest()
            }
        }
    }

    private fun requestTest() {
        loading(false)

        val map = hashMapOf<String, String>()
        map["type"] = "shunfeng"
        map["postid"] = "SF1020054157176"

        NetRequestFactory.createBodyPost("https://www.kuaidi100.com/query", map)
            .subscribe(addDisposable(object : NetCallback<ResponseBody>(this) {
                override fun onSuccess(t: ResponseBody) {
                    Log.i("HomeUIF", t.string())
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    e.printStackTrace()
                }
            }))
    }
}