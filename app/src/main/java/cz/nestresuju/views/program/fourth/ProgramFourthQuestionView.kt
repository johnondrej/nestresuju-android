package cz.nestresuju.views.program.fourth

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cz.nestresuju.R
import cz.nestresuju.databinding.ViewProgram4QuestionBinding
import cz.nestresuju.model.entities.domain.program.fourth.ProgramFourthAnswer

/**
 * View for showing question & answers in program 4.
 */
class ProgramFourthQuestionView(context: Context, attributes: AttributeSet) : LinearLayout(context, attributes) {

    private val binding = ViewProgram4QuestionBinding.inflate(LayoutInflater.from(context), this, true)
    private var ignoreIsCheckedListeners = false

    var question: String
        get() = binding.txtQuestion.text.toString()
        set(value) {
            binding.txtQuestion.text = value
        }

    fun setOnAnswerSelectedListener(onAnswerSelectedListener: (ProgramFourthAnswer) -> Unit) {
        with(binding) {
            radioGroupAnswers.setOnCheckedChangeListener { _, checkedId ->
                val checkedAnswer = when (checkedId) {
                    R.id.radio_btn_answer_1 -> ProgramFourthAnswer.ANSWER_1
                    R.id.radio_btn_answer_2 -> ProgramFourthAnswer.ANSWER_2
                    R.id.radio_btn_answer_3 -> ProgramFourthAnswer.ANSWER_3
                    R.id.radio_btn_answer_4 -> ProgramFourthAnswer.ANSWER_4
                    R.id.radio_btn_answer_5 -> ProgramFourthAnswer.ANSWER_5
                    R.id.radio_btn_answer_6 -> ProgramFourthAnswer.ANSWER_6
                    R.id.radio_btn_answer_7 -> ProgramFourthAnswer.ANSWER_7
                    else -> return@setOnCheckedChangeListener
                }

                if (!ignoreIsCheckedListeners) {
                    onAnswerSelectedListener(checkedAnswer)
                }
            }
        }
    }

    fun selectAnswer(answer: ProgramFourthAnswer) {
        val radioBtn = when (answer) {
            ProgramFourthAnswer.ANSWER_1 -> binding.radioBtnAnswer1
            ProgramFourthAnswer.ANSWER_2 -> binding.radioBtnAnswer2
            ProgramFourthAnswer.ANSWER_3 -> binding.radioBtnAnswer3
            ProgramFourthAnswer.ANSWER_4 -> binding.radioBtnAnswer4
            ProgramFourthAnswer.ANSWER_5 -> binding.radioBtnAnswer5
            ProgramFourthAnswer.ANSWER_6 -> binding.radioBtnAnswer6
            ProgramFourthAnswer.ANSWER_7 -> binding.radioBtnAnswer7
        }

        ignoreIsCheckedListeners = true
        radioBtn.isChecked = true
        ignoreIsCheckedListeners = false
    }

    fun unselectAll() {
        ignoreIsCheckedListeners = true
        binding.radioGroupAnswers.clearCheck()
        ignoreIsCheckedListeners = false
    }
}