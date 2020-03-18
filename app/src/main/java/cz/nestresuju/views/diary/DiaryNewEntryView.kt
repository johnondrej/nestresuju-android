package cz.nestresuju.views.diary

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.model.entities.domain.diary.StressLevelInput

/**
 * View for adding new entries to the diary.
 */
class DiaryNewEntryView(context: Context, attributes: AttributeSet) : CardView(context, attributes) {

    private val smiley1: DiaryStressLevelView
    private val smiley2: DiaryStressLevelView
    private val smiley3: DiaryStressLevelView
    private val smiley4: DiaryStressLevelView
    private val btnNote: DiaryStressLevelView?
    private val layoutAnswer: ViewGroup
    private val editAnswer: EditText
    private val btnConfirm: ImageView
    private val txtQuestion: TextView

    init {
        val attrs = context.obtainStyledAttributes(attributes, R.styleable.DiaryNewEntryView)
        val mode = attrs.getInt(R.styleable.DiaryNewEntryView_diaryInputMode, 0)
        when (mode) {
            0 -> View.inflate(context, R.layout.view_diary_input_large, this)
            1 -> View.inflate(context, R.layout.view_diary_input_small, this)
        }

        smiley1 = findViewById(R.id.smiley_1)
        smiley2 = findViewById(R.id.smiley_2)
        smiley3 = findViewById(R.id.smiley_3)
        smiley4 = findViewById(R.id.smiley_4)
        layoutAnswer = findViewById(R.id.layout_answer)
        editAnswer = findViewById(R.id.edit_answer)
        btnConfirm = findViewById(R.id.btn_confirm)
        txtQuestion = findViewById(R.id.txt_question)
        btnNote = if (mode == 1) findViewById(R.id.input_note) else null

        attrs.recycle()

        editAnswer.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btnConfirm.performClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    fun setOnConfirmationListener(listener: (String) -> Unit) {
        btnConfirm.setOnClickListener { listener(editAnswer.text.toString()) }
    }

    fun setOnStressLevelSelectedListener(listener: (StressLevelInput) -> Unit) {
        smiley1.setOnClickListener { listener(StressLevelInput.GREAT) }
        smiley2.setOnClickListener { listener(StressLevelInput.GOOD) }
        smiley3.setOnClickListener { listener(StressLevelInput.BAD) }
        smiley4.setOnClickListener { listener(StressLevelInput.STRESSED) }
        btnNote?.setOnClickListener { listener(StressLevelInput.NONE) }
    }

    fun select(stressLevel: StressLevelInput, question: String) {
        smiley1.setOptionSelected(stressLevel == StressLevelInput.GREAT)
        smiley2.setOptionSelected(stressLevel == StressLevelInput.GOOD)
        smiley3.setOptionSelected(stressLevel == StressLevelInput.BAD)
        smiley4.setOptionSelected(stressLevel == StressLevelInput.STRESSED)
        btnNote?.setOptionSelected(stressLevel == StressLevelInput.NONE)

        layoutAnswer.visible = true
        txtQuestion.text = question
    }

    fun clearAnswer() {
        editAnswer.text.clear()
    }
}