package com.tokyonth.mlkitocr.activity

import android.content.Intent
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tokyonth.mlkitocr.adapter.OcrRecentAdapter
import com.tokyonth.mlkitocr.databinding.ActivityMainBinding
import com.tokyonth.mlkitocr.view.GridItemDecoration
import com.tokyonth.mlkitocr.viewmodel.RecentDataViewModel
import com.tokyonth.mlkitocr.widget.TextSheetDialog
import com.tokyonth.module_common.base.BaseActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.iammert.library.ui.multisearchviewlib.MultiSearchViewListener
import com.tokyonth.mlkitocr.R
import com.tokyonth.module_common.Constants
import com.tokyonth.module_common.db.OcrRecent
import com.tokyonth.module_common.utils.SPUtils.get

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: RecentDataViewModel by viewModels()

    private lateinit var recentAdapter: OcrRecentAdapter

    private lateinit var textSheetDialog: TextSheetDialog

    private lateinit var animControl: LayoutAnimationController

    private var isAddedSnackBarCallBack = false

    override fun initData() {
        if (get(Constants.APP_FIRST_KEY, true)) {
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
            return
        }

        recentAdapter = OcrRecentAdapter()
        textSheetDialog = TextSheetDialog(this)
        viewModel.dataResultLiveData.observe(this) {
            recentAdapter.setData(it)
        }
    }

    override fun initView() {
        vb.rvOcrRecent.apply {
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            adapter = recentAdapter.apply {
                setDataClick { position, recent, isClickLong ->
                    if (isClickLong) {
                        showDelSnack(recent, position)
                    } else {
                        textSheetDialog.apply {
                            setOcrText(recent.content)
                        }.show()
                    }
                }
            }
            addItemDecoration(GridItemDecoration(24))
        }
        initAnim()

        vb.fabCamera.setOnClickListener {
            startActivity(Intent(this, AnalyzeActivity::class.java))
        }
        vb.multiSearchView.setSearchViewListener(object : MultiSearchViewListener {
            override fun onTextChanged(index: Int, s: CharSequence) {

            }

            override fun onSearchComplete(index: Int, s: CharSequence) {

            }

            override fun onSearchItemRemoved(index: Int) {

            }

            override fun onItemSelected(index: Int, s: CharSequence) {

            }
        })
    }

    private fun initAnim() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.anim_rv_list)
        animControl = LayoutAnimationController(anim).apply {
            order = LayoutAnimationController.ORDER_NORMAL
            delay = 0.5F
        }
        DefaultItemAnimator().apply {
            addDuration = 500
            removeDuration = 500
            vb.rvOcrRecent.itemAnimator = this
        }
    }

    private fun showDelSnack(recent: OcrRecent, position: Int) {
        var isRevoke = false
        recentAdapter.removeData(position)

        val snackBar = Snackbar.make(vb.root, "内容已删除", Snackbar.LENGTH_SHORT)
            .setAction("撤销") {
                isRevoke = true
                recentAdapter.insertData(position, recent)
            }

        if (!isAddedSnackBarCallBack) {
            val callBack = object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    if (!isRevoke)
                        viewModel.removeData(recent)
                }
            }
            snackBar.addCallback(callBack)
            isAddedSnackBarCallBack = true
        }
        snackBar.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllData()
        vb.rvOcrRecent.layoutAnimation = animControl
    }

}
