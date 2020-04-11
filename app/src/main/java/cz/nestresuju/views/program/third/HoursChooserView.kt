package cz.nestresuju.views.program.third

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cz.nestresuju.R
import cz.nestresuju.databinding.ViewHoursChooserBinding

/**
 * View for assigning time to a certain activity.
 */
class HoursChooserView(context: Context, attributes: AttributeSet) : LinearLayout(context, attributes) {

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

    init {
        val attrs = context.obtainStyledAttributes(attributes, R.styleable.HoursChooserView)
        val text = attrs.getString(R.styleable.HoursChooserView_txt)

        activityText = text

        attrs.recycle()
    }

    fun setOnChooseButtonClickedListener(onChoose: () -> Unit) {
        binding.btnChoose.setOnClickListener { onChoose() }
    }
}