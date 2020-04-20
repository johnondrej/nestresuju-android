package cz.nestresuju.screens.about.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelAboutSectionBinding
import cz.nestresuju.views.about.AboutSectionView

/**
 * Epoxy model for view for navigation in "About app" section.
 */
@EpoxyModelClass
open class AboutAppSectionModel : EpoxyModelWithView<AboutSectionView>() {

    @EpoxyAttribute
    var title: Int = 0

    @EpoxyAttribute
    var icon: Int = 0

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onSectionClicked: () -> Unit

    override fun buildView(parent: ViewGroup): AboutSectionView {
        return ModelAboutSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false).sectionView
    }

    override fun bind(view: AboutSectionView) {
        super.bind(view)
        with(ModelAboutSectionBinding.bind(view).sectionView) {
            sectionText = context.getString(title)
            setSectionIcon(icon)
            setOnSectionClickedListener(onSectionClicked)
        }
    }
}