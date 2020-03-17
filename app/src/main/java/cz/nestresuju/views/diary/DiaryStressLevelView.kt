package cz.nestresuju.views.diary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cz.nestresuju.R
import cz.nestresuju.databinding.ViewDiaryAnswerLargeBinding

/**
 * Class representing input for one stress level in diary.
 */
class DiaryStressLevelView(context: Context, attributes: AttributeSet) : LinearLayout(context, attributes) {

    private val binding: ViewDiaryAnswerLargeBinding = ViewDiaryAnswerLargeBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val attrs = context.obtainStyledAttributes(attributes, R.styleable.DiaryStressLevelView)
        binding.imgSmiley.setImageResource(attrs.getResourceId(R.styleable.DiaryStressLevelView_image, R.drawable.ic_diary_smiley_1))
        binding.txtSmiley.setText(attrs.getResourceId(R.styleable.DiaryStressLevelView_text, R.string.diary_stress_level_1))

        attrs.recycle()
    }

    fun setOptionSelected(isSelected: Boolean) {
        binding.imgSmiley.setBackgroundResource(
            if (!isSelected) R.drawable.bg_diary_stress_level else R.drawable.bg_diary_stress_level_selected
        )
    }
}