package cz.nestresuju.screens.library.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelLibrarySubsectionBinding
import cz.nestresuju.model.entities.domain.library.LibrarySection
import cz.nestresuju.views.library.LibrarySubsectionView

/**
 * Epoxy model for navigation between subsections in Library.
 */
@EpoxyModelClass
open class LibrarySubsectionModel : EpoxyModelWithView<LibrarySubsectionView>() {

    @EpoxyAttribute
    lateinit var subsection: LibrarySection

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onSubsectionClicked: (LibrarySection) -> Unit

    override fun buildView(parent: ViewGroup): LibrarySubsectionView {
        return ModelLibrarySubsectionBinding.inflate(LayoutInflater.from(parent.context), parent, false).subsectionView
    }

    override fun bind(view: LibrarySubsectionView) {
        super.bind(view)
        with(ModelLibrarySubsectionBinding.bind(view).subsectionView) {
            subsectionText = subsection.name
            setOnSectionClickedListener { onSubsectionClicked(subsection) }
        }
    }
}