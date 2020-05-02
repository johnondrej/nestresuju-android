package cz.nestresuju.views.tests

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cz.nestresuju.R
import cz.nestresuju.databinding.ViewInputTestQuestionBinding
import cz.nestresuju.model.entities.domain.tests.input.InputTestAnswer

/**
 * View for showing question & answers in input test.
 */
class InputTestQuestionView(context: Context, attributes: AttributeSet) : LinearLayout(context, attributes) {

    private val binding = ViewInputTestQuestionBinding.inflate(LayoutInflater.from(context), this, true)
    private var ignoreIsCheckedListeners = false

    var question: String
        get() = binding.txtQuestion.text.toString()
        set(value) {
            binding.txtQuestion.text = value
        }

    fun setOnAnswerSelectedListener(onAnswerSelectedListener: (InputTestAnswer) -> Unit) {
        with(binding) {
            radioGroupAnswers.setOnCheckedChangeListener { _, checkedId ->
                val checkedAnswer = when (checkedId) {
                    R.id.radio_btn_answer_0 -> InputTestAnswer.ANSWER_0
                    R.id.radio_btn_answer_1 -> InputTestAnswer.ANSWER_1
                    R.id.radio_btn_answer_2 -> InputTestAnswer.ANSWER_2
                    R.id.radio_btn_answer_3 -> InputTestAnswer.ANSWER_3
                    R.id.radio_btn_answer_4 -> InputTestAnswer.ANSWER_4
                    else -> return@setOnCheckedChangeListener
                }

                if (!ignoreIsCheckedListeners) {
                    onAnswerSelectedListener(checkedAnswer)
                }
            }
        }
    }

    fun selectAnswer(answer: InputTestAnswer) {
        val radioBtn = when (answer) {
            InputTestAnswer.ANSWER_0 -> binding.radioBtnAnswer0
            InputTestAnswer.ANSWER_1 -> binding.radioBtnAnswer1
            InputTestAnswer.ANSWER_2 -> binding.radioBtnAnswer2
            InputTestAnswer.ANSWER_3 -> binding.radioBtnAnswer3
            InputTestAnswer.ANSWER_4 -> binding.radioBtnAnswer4
        }

        ignoreIsCheckedListeners = true
        radioBtn.isChecked = true
        ignoreIsCheckedListeners = false
    }

    fun setAnswersOptions(answerOptions: Array<String>) {
        with(binding) {
            radioBtnAnswer0.text = answerOptions[0]
            radioBtnAnswer1.text = answerOptions[1]
            radioBtnAnswer2.text = answerOptions[2]
            radioBtnAnswer3.text = answerOptions[3]
            radioBtnAnswer4.text = answerOptions[4]
        }
    }

    fun unselectAll() {
        ignoreIsCheckedListeners = true
        binding.radioGroupAnswers.clearCheck()
        ignoreIsCheckedListeners = false
    }
}