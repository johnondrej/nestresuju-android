package cz.nestresuju.screens.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import cz.nestresuju.databinding.FragmentCustomListBinding
import cz.nestresuju.screens.about.epoxy.ContactsController
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for screen with contacts list.
 */
class ContactsFragment : BaseArchFragment<FragmentCustomListBinding>() {

    override val viewModel by viewModel<ContactsViewModel>()

    private lateinit var controller: ContactsController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentCustomListBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.customList) {
            controller = ContactsController(
                onEmailClicked = { Snackbar.make(view, "E-mail: $it", Snackbar.LENGTH_LONG).show() },
                onPhoneClicked = { Snackbar.make(view, "Volat: $it", Snackbar.LENGTH_LONG).show() },
                onMessageClicked = { Snackbar.make(view, "SMS: $it", Snackbar.LENGTH_LONG).show() }
            ).also {
                list.setController(it)
                it.requestModelBuild()
            }
        }
    }
}