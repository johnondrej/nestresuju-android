package cz.nestresuju.screens.diary.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelDiaryInputLargeBinding
import cz.nestresuju.model.entities.domain.diary.StressLevel
import cz.nestresuju.screens.diary.DiaryChoiceInput
import cz.nestresuju.views.diary.input.DiaryInputView

/**
 * Epoxy model for large stress level input.
 */
@EpoxyModelClass
open class DiaryInputLargeModel : EpoxyModelWithView<DiaryInputView>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onStressLevelSelected: (StressLevel) -> Unit
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onAnswerChanged: (String) -> Unit
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onInputConfirmed: () -> Unit
    @EpoxyAttribute
    var answer: String? = null
    @EpoxyAttribute
    var input: DiaryChoiceInput? = null

    override fun buildView(parent: ViewGroup): DiaryInputView {
        return ModelDiaryInputLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false).diaryInput
    }

    override fun bind(view: DiaryInputView) {
        super.bind(view)
        with(ModelDiaryInputLargeBinding.bind(view).diaryInput) {
            setOnStressLevelSelectedListener(onStressLevelSelected)
            setOnConfirmationListener(onInputConfirmed)
            setOnAnswerChangedListener(onAnswerChanged)
            setInput(input)
            setAnswer(answer)
        }
    }
}