package com.iammert.library.ui.multisearchviewlib.extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewTreeObserver
import com.iammert.library.ui.multisearchviewlib.helper.SimpleAnimationListener

fun ValueAnimator.endListener(onAnimationEnd: ()->Unit){
    addListener(object : SimpleAnimationListener(){
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            onAnimationEnd.invoke()
        }
    })
}

inline fun View.afterMeasured(crossinline f: View.(View) -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f(this@afterMeasured)
            }
        }
    })
}
