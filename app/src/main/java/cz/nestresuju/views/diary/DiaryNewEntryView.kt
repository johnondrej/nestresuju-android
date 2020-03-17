package cz.nestresuju.views.diary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.cardview.widget.CardView
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.databinding.ViewDiaryInputLargeBinding
import cz.nestresuju.model.entities.domain.diary.StressLevel

/**
 * View for adding new entries to the diary.
 */
class DiaryNewEntryView(context: Context, attributes: AttributeSet) : CardView(context, attributes) {

    private val binding: ViewDiaryInputLargeBinding = ViewDiaryInputLargeBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.editAnswer.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.btnConfirm.performClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    fun setOnConfirmationListener(listener: (String) -> Unit) {
        binding.btnConfirm.setOnClickListener { listener(binding.editAnswer.text.toString()) }
    }

    fun setOnStressLevelSelectedListener(listener: (StressLevel) -> Unit) {
        with(binding) {
            smiley1.setOnClickListener { listener(StressLevel.GREAT) }
            smiley2.setOnClickListener { listener(StressLevel.GOOD) }
            smiley3.setOnClickListener { listener(StressLevel.BAD) }
            smiley4.setOnClickListener { listener(StressLevel.STRESSED) }
        }
    }

    fun select(stressLevel: StressLevel, question: String) {
        with(binding) {
            smiley1.setOptionSelected(stressLevel == StressLevel.GREAT)
            smiley2.setOptionSelected(stressLevel == StressLevel.GOOD)
            smiley3.setOptionSelected(stressLevel == StressLevel.BAD)
            smiley4.setOptionSelected(stressLevel == StressLevel.STRESSED)

            layoutAnswer.visible = true
            txtQuestion.text = question
        }
    }

    fun clearAnswer() {
        binding.editAnswer.text.clear()
    }
}