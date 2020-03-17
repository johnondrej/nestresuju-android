package cz.nestresuju.views.diary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import cz.nestresuju.databinding.ViewDiaryInputLargeBinding
import cz.nestresuju.model.entities.domain.diary.StressLevel

/**
 * View for adding new entries to the diary.
 */
class DiaryNewEntryView(context: Context, attributes: AttributeSet) : CardView(context, attributes) {

    private val binding: ViewDiaryInputLargeBinding = ViewDiaryInputLargeBinding.inflate(LayoutInflater.from(context), this, true)

    fun setOnStressLevelSelectedListener(listener: (StressLevel) -> Unit) {
        binding.smiley1.setOnClickListener { listener(StressLevel.GREAT) }
        binding.smiley2.setOnClickListener { listener(StressLevel.GOOD) }
        binding.smiley3.setOnClickListener { listener(StressLevel.BAD) }
        binding.smiley4.setOnClickListener { listener(StressLevel.STRESSED) }
    }

    fun select(stressLevel: StressLevel) {
        binding.smiley1.setOptionSelected(stressLevel == StressLevel.GREAT)
        binding.smiley2.setOptionSelected(stressLevel == StressLevel.GOOD)
        binding.smiley3.setOptionSelected(stressLevel == StressLevel.BAD)
        binding.smiley4.setOptionSelected(stressLevel == StressLevel.STRESSED)
    }
}