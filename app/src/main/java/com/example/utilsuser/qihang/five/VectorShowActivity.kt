package com.example.utilsuser.qihang.five

import android.graphics.Paint.FontMetrics
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.example.utilsuser.R

class VectorShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vector_show)

        val ivAnimatedVector = findViewById<ImageView>(R.id.iv_animated_vector)
        val avdc = AnimatedVectorDrawableCompat.create(this, R.drawable.line_animated_vector)
        ivAnimatedVector.setImageDrawable(avdc)

        avdc?.start()

//        ((ivAnimatedVector.drawable) as Animatable).start()



        val imageView:ImageView = findViewById<ImageView>(R.id.anim_img)
        imageView.focusable = 1
        imageView.isFocusableInTouchMode = true
        imageView.requestFocus()
        imageView.requestFocusFromTouch()

        val editText = findViewById<EditText>(R.id.edit)
        editText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val avdc = AnimatedVectorDrawableCompat.create(this@VectorShowActivity, R.drawable.animated_vector_search)
                imageView.setImageDrawable(avdc)
                (imageView.drawable as Animatable).start()
            }
        }


    }
}