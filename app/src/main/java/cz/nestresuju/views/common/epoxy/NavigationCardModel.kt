package cz.nestresuju.views.common.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.databinding.ModelNavigationCardBinding

/**
 * Epoxy model for UI card for navigation to other parts of the application.
 */
@EpoxyModelClass
open class NavigationCardModel : EpoxyModelWithView<CardView>() {

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    var stateText: String = ""

    @EpoxyAttribute
    var stateDescriptionText: String = ""

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onItemClicked: () -> Unit

    override fun buildView(parent: ViewGroup): CardView {
        return ModelNavigationCardBinding.inflate(LayoutInflater.from(parent.context), parent, false).card
    }

    override fun bind(view: CardView) {
        super.bind(view)
        with(ModelNavigationCardBinding.bind(view)) {
            txtTitle.text = title
            txtState.text = stateText
            txtStateDescription.visible = stateDescriptionText.isNotBlank()
            txtStateDescription.text = stateDescriptionText
            card.setOnClickListener { onItemClicked() }
        }
    }
}