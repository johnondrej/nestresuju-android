package cz.nestresuju.views.diary

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cz.nestresuju.R

/**
 * Class representing input for one stress level in diary.
 */
class DiaryStressLevelView(context: Context, attributes: AttributeSet) : LinearLayout(context, attributes) {

    private val imgSmiley: ImageView
    private val txtDescription: TextView?

    init {
        val attrs = context.obtainStyledAttributes(attributes, R.styleable.DiaryStressLevelView)
        val mode = attrs.getInt(R.styleable.DiaryStressLevelView_diaryInputMode, 0)
        when (mode) {
            0 -> View.inflate(context, R.layout.view_diary_stress_level_large, this)
            1 -> View.inflate(context, R.layout.view_diary_stress_level_small, this)
        }

        imgSmiley = findViewById(R.id.img_smiley)
        txtDescription = if (mode == 0) findViewById(R.id.txt_smiley) else null

        imgSmiley.setImageResource(attrs.getResourceId(R.styleable.DiaryStressLevelView_image, R.drawable.ic_diary_smiley_1))
        txtDescription?.setText(attrs.getResourceId(R.styleable.DiaryStressLevelView_text, R.string.diary_stress_level_1))

        attrs.recycle()
    }

    fun setOptionSelected(isSelected: Boolean) {
        imgSmiley.setBackgroundResource(
            if (!isSelected) R.drawable.bg_diary_stress_level else R.drawable.bg_diary_stress_level_selected
        )
    }
}