package cz.nestresuju.views.about

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.databinding.ViewContactMethodBinding

/**
 * View that provides way to contact given person.
 */
class ContactMethodView(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes) {

    private val binding = ViewContactMethodBinding.inflate(LayoutInflater.from(context), this, true)

    var contactText: String?
        get() = binding.txtContact.text.toString()
        set(value) {
            binding.txtContact.text = value
        }

    var contactDescriptionText: String?
        get() = binding.txtContactDescription.text.toString()
        set(value) {
            binding.txtContactDescription.text = value
        }

    init {
        val attrs = context.obtainStyledAttributes(attributes, R.styleable.ContactMethodView)
        val contactIcon = attrs.getDrawable(R.styleable.ContactMethodView_contactIcon)
        val contactDescription = attrs.getString(R.styleable.ContactMethodView_contactDescription)
        val secondaryContactIcon = attrs.getDrawable(R.styleable.ContactMethodView_secondaryContactIcon)

        with(binding) {
            imgContactMethod.setImageDrawable(contactIcon)
            txtContactDescription.text = contactDescription
            btnContactSecondary.setImageDrawable(secondaryContactIcon)
            btnContactSecondary.visible = secondaryContactIcon != null
        }

        attrs.recycle()
    }

    fun setOnContactClickedListener(onContactClicked: () -> Unit) {
        with(binding) {
            root.setOnClickListener { onContactClicked() }
            imgContactMethod.setOnClickListener { onContactClicked() }
        }
    }

    fun setOnSecondaryContactClickedListener(onSecondaryContactClicked: () -> Unit) {
        binding.btnContactSecondary.setOnClickListener { onSecondaryContactClicked() }
    }
}