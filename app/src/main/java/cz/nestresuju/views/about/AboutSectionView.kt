package cz.nestresuju.views.about

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import cz.nestresuju.databinding.ViewAboutSectionBinding

/**
 * View for navigation between sections in "About app".
 */
class AboutSectionView(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes) {

    private val binding = ViewAboutSectionBinding.inflate(LayoutInflater.from(context), this, true)

    var sectionText: String
        get() = binding.txtSection.text.toString()
        set(value) {
            binding.txtSection.text = value
        }

    fun setSectionIcon(@DrawableRes imgRes: Int) {
        binding.imgSectionIcon.setImageResource(imgRes)
    }

    fun setOnSectionClickedListener(onSectionClicked: () -> Unit) {
        with(binding) {
            root.setOnClickListener { onSectionClicked() }
            imgArrow.setOnClickListener { onSectionClicked() }
        }
    }
}