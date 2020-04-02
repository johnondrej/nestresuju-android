package cz.nestresuju.views.tests

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
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
            radioBtnAnswer0.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && !ignoreIsCheckedListeners) {
                    onAnswerSelectedListener(InputTestAnswer.ANSWER_0)
                }
            }
            radioBtnAnswer1.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && !ignoreIsCheckedListeners) {
                    onAnswerSelectedListener(InputTestAnswer.ANSWER_1)
                }
            }
            radioBtnAnswer2.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && !ignoreIsCheckedListeners) {
                    onAnswerSelectedListener(InputTestAnswer.ANSWER_2)
                }
            }
            radioBtnAnswer3.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && !ignoreIsCheckedListeners) {
                    onAnswerSelectedListener(InputTestAnswer.ANSWER_3)
                }
            }
            radioBtnAnswer4.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && !ignoreIsCheckedListeners) {
                    onAnswerSelectedListener(InputTestAnswer.ANSWER_4)
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

    fun unselectAll() {
        ignoreIsCheckedListeners = true
        binding.radioGroupAnswers.clearCheck()
        ignoreIsCheckedListeners = false
    }
}