package cz.nestresuju.screens.program.overview.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.R
import cz.nestresuju.databinding.ModelProgramCardBinding

/**
 * Epoxy model for UI card leading to specific program.
 */
@EpoxyModelClass
open class ProgramCardModel : EpoxyModelWithView<CardView>() {

    @EpoxyAttribute
    lateinit var programName: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onProgramClicked: () -> Unit

    override fun buildView(parent: ViewGroup): CardView {
        return ModelProgramCardBinding.inflate(LayoutInflater.from(parent.context), parent, false).card
    }

    override fun bind(view: CardView) {
        super.bind(view)
        with(ModelProgramCardBinding.bind(view)) {
            txtProgramTitle.text = programName
            // TODO: show real program state when implemented
            txtProgramState.text = view.context.getString(R.string.program_state_open)
            card.setOnClickListener { onProgramClicked() }
        }
    }
}