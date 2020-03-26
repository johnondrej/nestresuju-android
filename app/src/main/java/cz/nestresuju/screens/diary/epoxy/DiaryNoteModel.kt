package cz.nestresuju.screens.diary.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelDiaryNoteEntryBinding
import cz.nestresuju.model.entities.domain.DiaryEntry
import cz.nestresuju.views.diary.DiaryNoteView

/**
 * Epoxy model for note entry view.
 */
@EpoxyModelClass
open class DiaryNoteModel : EpoxyModelWithView<DiaryNoteView>() {

    @EpoxyAttribute
    lateinit var noteEntry: DiaryEntry.NoteEntry
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onEditClicked: (DiaryEntry.NoteEntry) -> Unit
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onDeleteClicked: (DiaryEntry.NoteEntry) -> Unit

    override fun buildView(parent: ViewGroup): DiaryNoteView {
        return ModelDiaryNoteEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false).noteView
    }

    override fun bind(view: DiaryNoteView) {
        super.bind(view)
        with(ModelDiaryNoteEntryBinding.bind(view).noteView) {
            setEntry(noteEntry)
            setOnEditClickedListener { onEditClicked(noteEntry) }
            setOnDeleteClickedListener { onDeleteClicked(noteEntry) }
        }
    }
}