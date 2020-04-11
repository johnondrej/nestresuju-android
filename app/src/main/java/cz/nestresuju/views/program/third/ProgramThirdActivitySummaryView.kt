package cz.nestresuju.views.program.third

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import cz.nestresuju.databinding.ViewActivitySummaryBinding

/**
 * View for showing activity with its duration.
 */
class ProgramThirdActivitySummaryView(context: Context, attributes: AttributeSet? = null) : LinearLayout(context, attributes) {

    private val binding = ViewActivitySummaryBinding.inflate(LayoutInflater.from(context), this, true)

    val txtName: TextView
        get() = binding.txtActivityName

    val txtDuration: TextView
        get() = binding.txtActivityDuration

    val txtDurationPercent: TextView
        get() = binding.txtActivityDurationPercent
}