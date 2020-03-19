package cz.nestresuju.screens.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentDiaryBinding
import cz.nestresuju.screens.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiaryFragment : BaseFragment<FragmentDiaryBinding>() {

    companion object {

        private const val TAG_EDIT_NOTE_DIALOG = "edit_note_dialog"
    }

    override val viewModel by viewModel<DiaryViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentDiaryBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.stressLevelStream.observe(viewLifecycleOwner, Observer { stressLevel ->
            viewBinding.diaryInput.select(stressLevel, "Jak se cítíš?") // TODO: pass question from VM
        })

        viewModel.clearAnswerEvent.observe(viewLifecycleOwner, Observer { viewBinding.diaryInput.clearAnswer() })

        viewBinding.diaryInput.setOnStressLevelSelectedListener { stressLevel ->
            viewModel.onStressLevelSelected(stressLevel)
        }

        viewBinding.diaryInput.setOnConfirmationListener { answer ->
            if (answer.isNotEmpty()) {
                viewModel.onAnswerConfirmed(answer)
            } else {
                Snackbar.make(view, R.string.diary_empty_answer_error, Snackbar.LENGTH_LONG).show()
            }
        }

        viewBinding.noteExample.setOnEditClickedListener {
            DiaryEditNoteDialogFragment().show(childFragmentManager, TAG_EDIT_NOTE_DIALOG)
        }
    }
}
