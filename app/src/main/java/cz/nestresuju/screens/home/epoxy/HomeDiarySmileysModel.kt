package cz.nestresuju.screens.home.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelDiaryInputLargeBinding
import cz.nestresuju.databinding.ModelHomeDiarySmileysBinding
import cz.nestresuju.views.diary.input.DiaryInputView

/**
 * Epoxy model for "fake" stress level input shown on home screen that takes you to the diary.
 */
@EpoxyModelClass
open class HomeDiarySmileysModel : EpoxyModelWithView<DiaryInputView>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onItemClicked: () -> Unit

    override fun buildView(parent: ViewGroup): DiaryInputView {
        return ModelHomeDiarySmileysBinding.inflate(LayoutInflater.from(parent.context), parent, false).diaryInput
    }

    override fun bind(view: DiaryInputView) {
        super.bind(view)
        with(ModelDiaryInputLargeBinding.bind(view).diaryInput) {
            setOnClickListener { onItemClicked() }
            setOnStressLevelSelectedListener { onItemClicked() }
        }
    }
}