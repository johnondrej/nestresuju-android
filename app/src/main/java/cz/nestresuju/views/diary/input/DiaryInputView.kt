package cz.nestresuju.views.diary.input

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.cardview.widget.CardView
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.model.entities.domain.StressLevel
import cz.nestresuju.screens.diary.DiaryChoiceInput

/**
 * View for adding new entries to the diary.
 */
class DiaryInputView(context: Context, attributes: AttributeSet) : CardView(context, attributes) {

    private val smiley1: DiaryStressLevelChoiceView
    private val smiley2: DiaryStressLevelChoiceView
    private val smiley3: DiaryStressLevelChoiceView
    private val smiley4: DiaryStressLevelChoiceView
    private val btnNote: DiaryStressLevelChoiceView?
    private val questionView: DiaryQuestionView

    init {
        val attrs = context.obtainStyledAttributes(attributes, R.styleable.DiaryInputView)
        val mode = attrs.getInt(R.styleable.DiaryInputView_diaryInputMode, 0)
        when (mode) {
            0 -> View.inflate(context, R.layout.view_diary_input_large, this)
            1 -> View.inflate(context, R.layout.view_diary_input_small, this)
        }

        smiley1 = findViewById(R.id.smiley_1)
        smiley2 = findViewById(R.id.smiley_2)
        smiley3 = findViewById(R.id.smiley_3)
        smiley4 = findViewById(R.id.smiley_4)
        btnNote = if (mode == 1) findViewById(R.id.input_note) else null
        questionView = findViewById(R.id.view_question)

        attrs.recycle()
    }

    fun setOnConfirmationListener(listener: () -> Unit) {
        questionView.setOnConfirmationListener(listener)
    }

    fun setOnStressLevelSelectedListener(listener: (StressLevel) -> Unit) {
        smiley1.setOnClickListener { listener(StressLevel.STRESSED) }
        smiley2.setOnClickListener { listener(StressLevel.BAD) }
        smiley3.setOnClickListener { listener(StressLevel.GOOD) }
        smiley4.setOnClickListener { listener(StressLevel.GREAT) }
        btnNote?.setOnClickListener { listener(StressLevel.NONE) }
    }

    fun setOnAnswerChangedListener(listener: (String) -> Unit) {
        questionView.setOnAnswerChangedListener(listener)
    }

    fun setInput(input: DiaryChoiceInput?) {
        val stressLevel = input?.stressLevel
        smiley1.setOptionSelected(stressLevel == StressLevel.STRESSED)
        smiley2.setOptionSelected(stressLevel == StressLevel.BAD)
        smiley3.setOptionSelected(stressLevel == StressLevel.GOOD)
        smiley4.setOptionSelected(stressLevel == StressLevel.GREAT)
        btnNote?.setOptionSelected(stressLevel == StressLevel.NONE)

        with(questionView) {
            val shouldShow = input != null
            visible = shouldShow
            layoutParams = layoutParams.apply {
                // Workaround to fix Android bug that causes view to still occupy space even if visibility is set to GONE
                height = if (shouldShow) WRAP_CONTENT else 0
            }
            setQuestion(input?.question?.text)
        }
    }

    fun setAnswer(answer: String?) {
        questionView.setAnswer(answer)
    }
}