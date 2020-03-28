package cz.nestresuju.views.diary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import cz.nestresuju.databinding.ViewDiaryStressLevelBinding
import cz.nestresuju.model.entities.domain.diary.DiaryEntry
import cz.nestresuju.model.entities.domain.diary.StressLevel

/**
 * View for showing stress level diary entry.
 */
class DiaryStressLevelView(context: Context, attributes: AttributeSet) : CardView(context, attributes) {

    private val binding = ViewDiaryStressLevelBinding.inflate(LayoutInflater.from(context), this, true)

    fun setOnEditClickedListener(onEdit: () -> Unit) {
        binding.btnEdit.setOnClickListener { onEdit() }
    }

    fun setEntry(stressLevelEntry: DiaryEntry.StressLevelEntry) {
        selectStressLevel(stressLevelEntry.stressLevel)
        with(binding) {
            txtQuestion.text = stressLevelEntry.question.text
            txtAnswer.text = stressLevelEntry.answer
        }
    }

    private fun selectStressLevel(stressLevel: StressLevel) {
        with(binding) {
            smiley1.setOptionSelected(stressLevel == StressLevel.STRESSED)
            smiley2.setOptionSelected(stressLevel == StressLevel.BAD)
            smiley3.setOptionSelected(stressLevel == StressLevel.GOOD)
            smiley4.setOptionSelected(stressLevel == StressLevel.GREAT)
        }
    }
}