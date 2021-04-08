package com.sleeve.app

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.sleeve.app.databinding.UifMainBinding
import com.sleeve.net.NetCallback
import com.sleeve.net.NetRequestFactory
import com.sleeve.ui.load.LoadHeadBarUIF
import com.sleeve.ui.view.HeadBar
import okhttp3.ResponseBody

/**
 * Create by lzx on 2020/05/19.
 */
class MainUIF : LoadHeadBarUIF<UifMainBinding>(), View.OnClickListener {

    override fun initHeadBar(headBar: HeadBar) {
        headBar.setCenterTitle("首页")
    }

    override fun getViewBinding(inflater: LayoutInflater): UifMainBinding {
        return UifMainBinding.inflate(inflater)
    }

    override fun initView() {
        setSwipeBackEnable(false)

        mBinding.btnRequest.setOnClickListener(this)
        mBinding.btnBarColor.setOnClickListener(this)
        mBinding.btnToolbarColor.setOnClickListener(this)
        mBinding.btnReset.setOnClickListener(this)
        mBinding.btnGoPage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            mBinding.btnRequest -> {
                requestTest()
            }
            mBinding.btnBarColor -> {
                mStartBar?.setBackgroundColor(Color.BLACK)
            }
            mBinding.btnToolbarColor -> {
                setToolbarBackground(ColorDrawable(Color.RED))
            }
            mBinding.btnReset -> {
                val color = ContextCompat.getColor(_mActivity, R.color.colorPrimary)
                setToolbarBackground(ColorDrawable(color))
                mStartBar?.setBackgroundColor(color)
            }
            mBinding.btnGoPage -> {
                start(MainUIF())
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