package cz.nestresuju.screens.diary.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelDiaryStressEntryBinding
import cz.nestresuju.model.entities.domain.diary.DiaryEntry
import cz.nestresuju.views.diary.DiaryStressLevelView

/**
 * Epoxy model for stress level entry view.
 */
@EpoxyModelClass
open class DiaryStressLevelModel : EpoxyModelWithView<DiaryStressLevelView>() {

    @EpoxyAttribute
    lateinit var stressLevelEntry: DiaryEntry.StressLevelEntry
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onEditClicked: (DiaryEntry.StressLevelEntry) -> Unit

    override fun buildView(parent: ViewGroup): DiaryStressLevelView {
        return ModelDiaryStressEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false).stressLevelView
    }

    override fun bind(view: DiaryStressLevelView) {
        super.bind(view)
        with(ModelDiaryStressEntryBinding.bind(view).stressLevelView) {
            setEntry(stressLevelEntry)
            setOnEditClickedListener { onEditClicked(stressLevelEntry) }
        }
    }
}