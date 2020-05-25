package cz.nestresuju.screens.about.epoxy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelAboutResearchSubsectionBinding

/**
 * Epoxy model for subsection with text in research section.
 */
@EpoxyModelClass
open class ResearchSubsectionModel : EpoxyModelWithView<View>() {

    @EpoxyAttribute
    lateinit var headlineText: String

    @EpoxyAttribute
    var contentText: String? = null

    override fun buildView(parent: ViewGroup): View {
        return ModelAboutResearchSubsectionBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    }

    override fun bind(view: View) {
        super.bind(view)
        with(ModelAboutResearchSubsectionBinding.bind(view)) {
            txtHeadline.text = headlineText
            text.text = contentText?.let { HtmlCompat.fromHtml(it, FROM_HTML_MODE_COMPACT) }
        }
    }
}