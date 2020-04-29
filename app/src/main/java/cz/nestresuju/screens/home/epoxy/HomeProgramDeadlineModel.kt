package cz.nestresuju.screens.home.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.R
import cz.nestresuju.databinding.ModelProgramDeadlineBinding

/**
 * Epoxy model for showing info about research deadline date.
 */
@EpoxyModelClass
open class HomeProgramDeadlineModel : EpoxyModelWithView<TextView>() {

    @EpoxyAttribute
    var daysUntilDeadline: Int = -1

    override fun buildView(parent: ViewGroup): TextView {
        return ModelProgramDeadlineBinding.inflate(LayoutInflater.from(parent.context), parent, false).txtDeadline
    }

    override fun bind(view: TextView) {
        super.bind(view)
        view.text = if (daysUntilDeadline >= 0) {
            view.context.resources.getQuantityString(R.plurals.program_deadline_format, daysUntilDeadline, daysUntilDeadline)
        } else null
    }
}