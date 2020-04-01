package cz.nestresuju.views.tests

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cz.nestresuju.databinding.ViewInputTestQuestionBinding

/**
 * View for showing question & answers in input test.
 */
class InputTestQuestionView(context: Context, attributes: AttributeSet) : LinearLayout(context, attributes) {

    private val binding = ViewInputTestQuestionBinding.inflate(LayoutInflater.from(context), this, true)

    var question: String
        get() = binding.txtQuestion.text.toString()
        set(value) {
            binding.txtQuestion.text = value
        }

    fun setOnAnswerSelectedListener(onAnswerSelectedListener: (Answer) -> Unit) {
        with(binding) {
            radioBtnAnswer0.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onAnswerSelectedListener(Answer.ANSWER_0)
                }
            }
            radioBtnAnswer1.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onAnswerSelectedListener(Answer.ANSWER_1)
                }
            }
            radioBtnAnswer2.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onAnswerSelectedListener(Answer.ANSWER_2)
                }
            }
            radioBtnAnswer3.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onAnswerSelectedListener(Answer.ANSWER_3)
                }
            }
            radioBtnAnswer4.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onAnswerSelectedListener(Answer.ANSWER_4)
                }
            }
        }
    }

    enum class Answer(val intValue: Int) {
        ANSWER_0(0),
        ANSWER_1(1),
        ANSWER_2(2),
        ANSWER_3(3),
        ANSWER_4(4)
    }
}