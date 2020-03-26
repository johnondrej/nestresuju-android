package cz.nestresuju.screens.diary.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelDiaryInputSmallBinding
import cz.nestresuju.model.entities.domain.StressLevel
import cz.nestresuju.views.diary.input.DiaryInputView

/**
 * Epoxy model for large stress level input.
 */
@EpoxyModelClass
open class DiaryInputSmallModel : EpoxyModelWithView<DiaryInputView>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onStressLevelSelected: (StressLevel) -> Unit
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onAnswerChanged: (String) -> Unit
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onInputConfirmed: () -> Unit
    @EpoxyAttribute
    var answer: String? = null
    @EpoxyAttribute
    var input: Pair<StressLevel, String>? = null

    override fun buildView(parent: ViewGroup): DiaryInputView {
        return ModelDiaryInputSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false).diaryInput
    }

    override fun bind(view: DiaryInputView) {
        super.bind(view)
        with(ModelDiaryInputSmallBinding.bind(view).diaryInput) {
            setOnStressLevelSelectedListener(onStressLevelSelected)
            setOnConfirmationListener(onInputConfirmed)
            setOnAnswerChangedListener(onAnswerChanged)
            input?.let { select(stressLevel = it.first, question = it.second) }
            answer?.let { setAnswer(it) }
        }
    }
}