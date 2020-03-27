package cz.nestresuju.screens.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.nestresuju.databinding.ViewEpoxyListBinding
import cz.nestresuju.model.entities.domain.DiaryEntry
import cz.nestresuju.screens.base.BaseFragment
import cz.nestresuju.screens.diary.epoxy.DiaryController
import cz.nestresuju.screens.diary.errors.DiaryErrorHandler
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate

class DiaryFragment : BaseFragment<ViewEpoxyListBinding>(), DiaryEditNoteDialogFragment.OnEntryEditConfirmedListener {

    companion object {

        private const val TAG_EDIT_NOTE_DIALOG = "edit_note_dialog"
    }

    private lateinit var controller: DiaryController

    override val viewModel by viewModel<DiaryViewModel>()

    override val errorHandlers = super.errorHandlers + DiaryErrorHandler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ViewEpoxyListBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = DiaryController(
            onStressLevelSelected = { stressLevel -> viewModel.onStressLevelSelected(stressLevel) },
            onInputConfirmed = { viewModel.onAnswerConfirmed() },
            onAnswerChanged = { answer -> viewModel.answer = answer },
            onStressLevelEditClicked = { entry -> onShowEditEntryDialog(entry) },
            onNoteEditClicked = { entry -> onShowEditEntryDialog(entry) },
            onNoteDeleteClicked = { entry -> viewModel.onDeleteEntry(entry) }
        ).apply {
            answer = viewModel.answer
        }

        viewBinding.list.setController(controller)
        controller.requestModelBuild()

        viewModel.inputStream.observe(viewLifecycleOwner, Observer { input ->
            controller.input = input
        })

        viewModel.clearAnswerEvent.observe(viewLifecycleOwner, Observer { controller.answer = null })

        viewModel.entriesStream.observe(viewLifecycleOwner, Observer { entries ->
            controller.entries = entries
            controller.showSmallInput = entries.any { it.dateCreated.toLocalDate() == LocalDate.now() }
        })
    }

    private fun onShowEditEntryDialog(entry: DiaryEntry) {
        DiaryEditNoteDialogFragment.newInstance(entry.id, entry.text).show(childFragmentManager, TAG_EDIT_NOTE_DIALOG)
    }

    override fun onDiaryEntryEditConfirmed(entryId: Long, modifiedText: String) {
        viewModel.onEditEntry(entryId, modifiedText)
    }
}
