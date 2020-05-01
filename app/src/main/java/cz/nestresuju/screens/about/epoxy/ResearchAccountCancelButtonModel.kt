package cz.nestresuju.screens.about.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelAccountCancelButtonBinding

/**
 * Epoxy model for button to remove user account.
 */
@EpoxyModelClass
open class ResearchAccountCancelButtonModel : EpoxyModelWithView<Button>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onButtonClicked: () -> Unit

    override fun buildView(parent: ViewGroup): Button {
        return ModelAccountCancelButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false).btnCancelAccount
    }

    override fun bind(view: Button) {
        super.bind(view)
        with(ModelAccountCancelButtonBinding.bind(view)) {
            btnCancelAccount.setOnClickListener {
                onButtonClicked()
            }
        }
    }
}