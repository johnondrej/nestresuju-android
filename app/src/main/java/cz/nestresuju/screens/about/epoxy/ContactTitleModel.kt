package cz.nestresuju.screens.about.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelContactCategoryTitleBinding

/**
 * Epoxy model for displaying header for contacts category.
 */
@EpoxyModelClass
open class ContactTitleModel : EpoxyModelWithView<TextView>() {

    @EpoxyAttribute
    lateinit var categoryTitle: String

    override fun buildView(parent: ViewGroup): TextView {
        return ModelContactCategoryTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false).txtCategoryTitle
    }

    override fun bind(view: TextView) {
        super.bind(view)
        with(ModelContactCategoryTitleBinding.bind(view)) {
            txtCategoryTitle.text = categoryTitle
        }
    }
}