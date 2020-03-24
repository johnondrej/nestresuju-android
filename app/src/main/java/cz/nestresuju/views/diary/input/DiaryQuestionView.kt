package cz.nestresuju.views.diary.input

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import cz.nestresuju.databinding.ViewDiaryInputQuestionBinding

/**
 * Input view for stress level question.
 */
class DiaryQuestionView(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes) {

    private val binding = ViewDiaryInputQuestionBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        with(binding) {
            editAnswer.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnConfirm.performClick()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    fun setOnConfirmationListener(listener: () -> Unit) {
        binding.btnConfirm.setOnClickListener { listener() }
    }

    fun setOnAnswerChangedListener(listener: (String) -> Unit) {
        binding.editAnswer.doAfterTextChanged { answer -> listener(answer.toString()) }
    }

    fun setQuestion(question: String) {
        binding.txtQuestion.text = question
    }

    fun setAnswer(answer: String) {
        binding.editAnswer.setText(answer)
    }
}