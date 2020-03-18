package cz.nestresuju.views.diary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import cz.nestresuju.databinding.ViewDiaryStressLevelBinding

/**
 * View for showing stress level diary entry.
 */
class DiaryStressLevelView(context: Context, attributes: AttributeSet) : CardView(context, attributes) {

    private val binding = ViewDiaryStressLevelBinding.inflate(LayoutInflater.from(context), this, true)

    fun setOnEditClickedListener(onEdit: () -> Unit) {
        binding.btnEdit.setOnClickListener { onEdit() }
    }
}