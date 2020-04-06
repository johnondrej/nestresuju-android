package cz.nestresuju.views.program.first

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cz.nestresuju.R
import cz.nestresuju.databinding.ViewProgram1OverviewAnswerBinding

/**
 * View for answer on overview of first program.
 */
class ProgramFirstSummaryAnswerView(context: Context, attributes: AttributeSet) : LinearLayout(context, attributes) {

    private val binding = ViewProgram1OverviewAnswerBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String
        get() = binding.txtTitle.text.toString()
        set(value) {
            binding.txtTitle.text = value
        }

    var answer: String
        get() = binding.txtAnswer.text.toString()
        set(value) {
            binding.txtAnswer.text = value
        }

    init {
        val attrs = context.obtainStyledAttributes(attributes, R.styleable.ProgramFirstSummaryAnswerView)
        val title = attrs.getString(R.styleable.ProgramFirstSummaryAnswerView_summaryTitle)
        val answer = attrs.getString(R.styleable.ProgramFirstSummaryAnswerView_summaryAnswer)

        binding.txtTitle.text = title
        binding.txtAnswer.text = answer

        attrs.recycle()
    }
}