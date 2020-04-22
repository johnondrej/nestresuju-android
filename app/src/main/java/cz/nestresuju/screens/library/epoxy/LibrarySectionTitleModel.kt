package cz.nestresuju.screens.library.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelLibrarySectionTitleBinding

/**
 * Epoxy model for showing title of a library section.
 */
@EpoxyModelClass
open class LibrarySectionTitleModel : EpoxyModelWithView<TextView>() {

    @EpoxyAttribute
    lateinit var title: String

    override fun buildView(parent: ViewGroup): TextView {
        return ModelLibrarySectionTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false).txtTitle
    }

    override fun bind(view: TextView) {
        super.bind(view)
        with(ModelLibrarySectionTitleBinding.bind(view)) {
            txtTitle.text = title
        }
    }
}