package cz.nestresuju.screens.library.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelLibraryContentBinding

/**
 * Epoxy model for displaying content in library section.
 */
@EpoxyModelClass
open class LibraryContentModel : EpoxyModelWithView<TextView>() {

    @EpoxyAttribute
    lateinit var content: String

    override fun buildView(parent: ViewGroup): TextView {
        return ModelLibraryContentBinding.inflate(LayoutInflater.from(parent.context), parent, false).txtContent
    }

    override fun bind(view: TextView) {
        super.bind(view)
        with(ModelLibraryContentBinding.bind(view)) {
            txtContent.text = content
        }
    }
}