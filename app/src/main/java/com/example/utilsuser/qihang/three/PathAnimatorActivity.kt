package com.example.utilsuser.qihang.three

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsuser.R
import kotlin.math.cos
import kotlin.math.sin

class PathAnimatorActivity : AppCompatActivity(), View.OnClickListener {
    private var mIsMenuOpen = false

    lateinit var mItemButton1: Button
    lateinit var mItemButton2: Button
    lateinit var mItemButton3: Button
    lateinit var mItemButton4: Button
    lateinit var mItemButton5: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path_animator)

        findViewById<Button>(R.id.menu).setOnClickListener(this)
        mItemButton1 = findViewById<Button?>(R.id.item1).apply {
            setOnClickListener(this@PathAnimatorActivity)
        }
        mItemButton2 = findViewById<Button?>(R.id.item2).apply {
            setOnClickListener(this@PathAnimatorActivity)
        }
        mItemButton3 = findViewById<Button?>(R.id.item3).apply {
            setOnClickListener(this@PathAnimatorActivity)
        }
        mItemButton4 = findViewById<Button?>(R.id.item4).apply {
            setOnClickListener(this@PathAnimatorActivity)
        }
        mItemButton5 = findViewById<Button?>(R.id.item5).apply {
            setOnClickListener(this@PathAnimatorActivity)
        }
    }

    override fun onClick(view: View) {
        if (! mIsMenuOpen) {
            mIsMenuOpen = true
            openMenu()
        } else {
            Toast.makeText(this, "你单击了" + view, Toast.LENGTH_SHORT).show()
            mIsMenuOpen = false
            closeMenu()
        }
    }

    private fun openMenu() {
        doAnimationOpen(mItemButton1, 0, 5, 600)
        doAnimationOpen(mItemButton2, 1, 5, 600)
        doAnimationOpen(mItemButton3, 2, 5, 600)
        doAnimationOpen(mItemButton4, 3, 5, 600)
        doAnimationOpen(mItemButton5, 4, 5, 600)
    }

    private fun doAnimationOpen(view: View, index: Int, total: Int, radius: Int) {
        if (view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
        }
        val degree = Math.toRadians(90.0) / (total - 1) * index
        val translationX = (- (radius * sin(degree)))
        val translationY = (- (radius * cos(degree)))
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0f, translationX.toFloat()),
                ObjectAnimator.ofFloat(view, "translationY", 0f, translationY.toFloat()),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
            )
            duration = 500
            start()
        }
    }

    private fun closeMenu() {
        doAnimationClose(mItemButton1, 0, 5, 600)
        doAnimationClose(mItemButton2, 1, 5, 600)
        doAnimationClose(mItemButton3, 2, 5, 600)
        doAnimationClose(mItemButton4, 3, 5, 600)
        doAnimationClose(mItemButton5, 4, 5, 600)
    }

    fun doAnimationClose(view: View, index: Int, total: Int, radius: Int) {
        if (view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
        }
        val degree = Math.PI * index / ((total - 1) * 2)
        val translationX = (- (radius * sin(degree)))
        val translationY = (- (radius * cos(degree)))
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "translationX", translationX.toFloat(), 0f),
                ObjectAnimator.ofFloat(view, "translationY", translationY.toFloat(), 0f),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
            )
            duration = 500
            start()
        }
    }


}