package com.example.utilsuser.qihang.four

import android.animation.Keyframe
import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R

class AnimateLayoutChangesActivity : AppCompatActivity() {
    var i = 0
    lateinit var linearLayoutContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animate_layout_changes)

        init()
    }

    private fun init() {
        val btnAdd = findViewById<Button>(R.id.add_btn)
        val btnRemove = findViewById<Button>(R.id.remove_btn)
        linearLayoutContainer = findViewById<LinearLayout>(R.id.linear_layout_container)

        val transition = LayoutTransition()
        //入场动画
        val animIn = ObjectAnimator.ofFloat(null, "rotationY", 0f, 360f, 0f)
        transition.setAnimator(LayoutTransition.APPEARING, animIn)

        //出场动画
        val animOut = ObjectAnimator.ofFloat(null, "rotation", 0f, 90f, 0f)
        transition.setAnimator(LayoutTransition.DISAPPEARING, animOut)

        val pvhLeft = PropertyValuesHolder.ofInt("left", 100, 100)
        val pvhTop = PropertyValuesHolder.ofInt("top", 20, 20)
        val pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f)
        val changeAppearAnimator = ObjectAnimator.ofPropertyValuesHolder(linearLayoutContainer,
            pvhLeft, pvhTop, pvhScaleX)
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING, changeAppearAnimator)

        val outLeft = PropertyValuesHolder.ofInt("left", 0, 0)
        val outTop = PropertyValuesHolder.ofInt("top", 0, 0)
        val frame0 = Keyframe.ofFloat(0f, 0f)
        val frame1 = Keyframe.ofFloat(0.1f, -20f)
        val frame2 = Keyframe.ofFloat(0.2f, 20f)
        val frame3 = Keyframe.ofFloat(0.3f, -20f)
        val frame4 = Keyframe.ofFloat(0.4f, 20f)
        val frame5 = Keyframe.ofFloat(0.5f, -20f)
        val frame6 = Keyframe.ofFloat(0.6f, 20f)
        val frame7 = Keyframe.ofFloat(0.7f, -20f)
        val frame8 = Keyframe.ofFloat(0.8f, 20f)
        val frame9 = Keyframe.ofFloat(0.9f, -20f)
        val frame10 = Keyframe.ofFloat(1f, 0f)
        val holder2 = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3
            , frame4, frame5, frame6, frame7, frame8, frame9, frame10)
        val changeDisappeearing = ObjectAnimator.ofPropertyValuesHolder(this, outLeft, outTop,
            holder2)
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeDisappeearing)

        transition.addTransitionListener(object : LayoutTransition.TransitionListener {
            override fun startTransition(
                transition: LayoutTransition?,
                container: ViewGroup?,
                view: View?,
                transitionType: Int
            ) {
                LogUtil.d("start: transitionType: $transitionType count: ${container?.childCount} view: ${view?.javaClass?.name}" )
            }

            override fun endTransition(
                transition: LayoutTransition?,
                container: ViewGroup?,
                view: View?,
                transitionType: Int
            ) {
                LogUtil.d("start: transitionType: $transitionType count: ${container?.childCount} view: ${view?.javaClass?.name}" )
            }

        })

        linearLayoutContainer.layoutTransition = transition

        btnAdd.setOnClickListener {
            addButtonView()
        }

        btnRemove.setOnClickListener {
            removeButtonView()
        }
    }

    private fun addButtonView() {
        i++;
        val button = Button(this)
        button.text = "button$i"
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        button.layoutParams = params
        linearLayoutContainer.addView(button, 0)
    }

    private fun removeButtonView() {
        if (i > 0) {
            linearLayoutContainer.removeViewAt(0)
        }
        i--;
    }
}