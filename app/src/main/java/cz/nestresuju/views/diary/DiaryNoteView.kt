package cz.nestresuju.views.diary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import cz.nestresuju.databinding.ViewDiaryNoteBinding

/**
 * View for showing diary note entry.
 */
class DiaryNoteView(context: Context, attributes: AttributeSet) : CardView(context, attributes) {

    private val binding = ViewDiaryNoteBinding.inflate(LayoutInflater.from(context), this, true)

    fun setOnEditClickedListener(onEdit: () -> Unit) {
        binding.btnEdit.setOnClickListener { onEdit() }
    }

    fun setOnDeleteClickedListener(onDelete: () -> Unit) {
        binding.btnDelete.setOnClickListener { onDelete() }
    }
}