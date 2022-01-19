package com.tokyonth.mlkitocr.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tokyonth.mlkitocr.databinding.ActivitySplashBinding
import com.tokyonth.mlkitocr.viewmodel.RecentDataViewModel
import com.tokyonth.module_common.Constants
import com.tokyonth.module_common.base.BaseActivity
import com.tokyonth.module_common.utils.SPUtils.get
import com.tokyonth.module_common.utils.SPUtils.set

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val viewModel: RecentDataViewModel by viewModels()

    override fun initData() {
        if (get(Constants.APP_FIRST_KEY, true)) {
            viewModel.insertData(Constants.APP_FIRST_TIP)
            set(Constants.APP_FIRST_KEY, false)
        }
    }

    override fun initView() {
        MaterialAlertDialogBuilder(this)
            .setTitle("声明")
            .setMessage(
                "本APP目前只使用了摄像头和储存权限, 不会联网, 请放心使用\n" +
                        "这是第一个测试版本, 很简陋, 还有些未完成的功能在计划中..."
            )
            .setPositiveButton(
                "确定"
            ) { _, _ ->
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .setCancelable(false)
            .create().show()
    }

}
