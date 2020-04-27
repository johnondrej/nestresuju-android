package cz.nestresuju.screens.about.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelAboutResearchTextBinding

/**
 * Epoxy model for text in research section.
 */
@EpoxyModelClass
open class ResearchTextModel : EpoxyModelWithView<TextView>() {

    @EpoxyAttribute
    lateinit var researchText: String

    override fun buildView(parent: ViewGroup): TextView {
        return ModelAboutResearchTextBinding.inflate(LayoutInflater.from(parent.context), parent, false).text
    }

    override fun bind(view: TextView) {
        super.bind(view)
        with(ModelAboutResearchTextBinding.bind(view)) {
            text.text = HtmlCompat.fromHtml(researchText, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
    }
}