package cz.nestresuju.screens.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentCustomListBinding
import cz.nestresuju.model.common.State
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
        controller = ContactsController(
            onEmailClicked = { email -> onEmailClicked(email) },
            onPhoneClicked = { phone -> onPhoneClicked(phone) },
            onMessageClicked = { phone -> onMessageClicked(phone) }
        )
        return FragmentCustomListBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.customList) {
            list.setController(controller)
            emptyTextResource = R.string.about_contacts_empty

            viewModel.contactsStream.observe(viewLifecycleOwner, Observer { state ->
                contentVisible = state is State.Loaded && state.data.isNotEmpty()
                refreshLayout.isRefreshing = state is State.Loading
                emptyTextVisible = (state as? State.Loaded)?.data?.isEmpty() == true

                if (state is State.Loaded) {
                    controller.contacts = state.data
                }
            })

            refreshLayout.setOnRefreshListener {
                viewModel.fetchContacts()
            }
        }
    }

    private fun onEmailClicked(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
        }

        startActivity(intent)
    }

    private fun onPhoneClicked(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }

        startActivity(intent)
    }

    private fun onMessageClicked(phone: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:$phone")
        }

        startActivity(intent)
    }
}