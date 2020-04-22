package cz.nestresuju.views.library

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import cz.nestresuju.databinding.ViewLibrarySubsectionBinding

/**
 * View for navigation between library subsections.
 */
class LibrarySubsectionView(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes) {

    private val binding = ViewLibrarySubsectionBinding.inflate(LayoutInflater.from(context), this, true)

    var subsectionText: String?
        get() = binding.txtSubsection.text.toString()
        set(value) {
            binding.txtSubsection.text = value
        }

    fun setOnSectionClickedListener(onSectionClicked: () -> Unit) {
        with(binding) {
            root.setOnClickListener { onSectionClicked() }
            imgArrow.setOnClickListener { onSectionClicked() }
        }
    }
}