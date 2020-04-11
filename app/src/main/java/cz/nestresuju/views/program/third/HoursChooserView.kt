package cz.nestresuju.views.program.third

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.databinding.ViewHoursChooserBinding

/**
 * View for assigning time to a certain activity.
 */
class HoursChooserView(context: Context, attributes: AttributeSet? = null) : LinearLayout(context, attributes) {

    private val binding = ViewHoursChooserBinding.inflate(LayoutInflater.from(context), this, true)

    var activityText: String?
        set(value) {
            binding.txtActivity.text = value
        }
        get() = binding.txtActivity.text.toString()

    var btnText: String?
        set(value) {
            binding.btnChoose.text = value
        }
        get() = binding.btnChoose.text.toString()

    var removeEnabled: Boolean
        get() = binding.btnRemove.visible
        set(value) {
            binding.btnRemove.visible = value
        }

    init {
        if (attributes != null) {
            val attrs = context.obtainStyledAttributes(attributes, R.styleable.HoursChooserView)
            val text = attrs.getString(R.styleable.HoursChooserView_txt)

            activityText = text

            attrs.recycle()
        }
    }

    fun setOnChooseButtonClickedListener(onChoose: () -> Unit) {
        binding.btnChoose.setOnClickListener { onChoose() }
    }

    fun setOnRemoveButtonClickedListener(onRemove: () -> Unit) {
        binding.btnRemove.setOnClickListener { onRemove() }
    }
}