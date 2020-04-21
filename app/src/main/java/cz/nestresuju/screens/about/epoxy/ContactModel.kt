package cz.nestresuju.screens.about.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.api.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.databinding.ModelContactBinding
import cz.nestresuju.views.about.ContactInfoView

/**
 * Epoxy model for displaying one contact.
 */
@EpoxyModelClass
open class ContactModel : EpoxyModelWithView<ContactInfoView>() {

    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute
    var description: String? = null

    @EpoxyAttribute
    var photoUrl: String? = null

    @EpoxyAttribute
    var email: String? = null

    @EpoxyAttribute
    var phone: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onEmailClicked: (String) -> Unit

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onPhoneClicked: (String) -> Unit

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onMessageClicked: (String) -> Unit

    override fun buildView(parent: ViewGroup): ContactInfoView {
        return ModelContactBinding.inflate(LayoutInflater.from(parent.context), parent, false).contactView
    }

    override fun bind(view: ContactInfoView) {
        super.bind(view)
        with(ModelContactBinding.bind(view).contactView) {
            imgPhoto.load(photoUrl) {
                scale(Scale.FILL)
                transformations(CircleCropTransformation())
            }
            txtName.text = name
            txtDescription.visible = !description.isNullOrBlank()
            txtDescription.text = description
            contactEmail.visible = email != null
            email?.let {
                contactEmail.contactText = it
                contactEmail.setOnContactClickedListener { onEmailClicked(it) }
            }
            dividerEmailPhone.visible = phone != null && email != null
            contactPhone.visible = phone != null
            phone?.let {
                contactPhone.contactText = it
                contactPhone.setOnContactClickedListener { onPhoneClicked(it) }
                contactPhone.setOnSecondaryContactClickedListener { onMessageClicked(it) }
            }
        }
    }
}