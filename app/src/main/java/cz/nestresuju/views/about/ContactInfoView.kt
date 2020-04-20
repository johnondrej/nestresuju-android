package cz.nestresuju.views.about

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import cz.nestresuju.databinding.ViewContactInfoBinding

/**
 * View with contact info about some person.
 */
class ContactInfoView(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes) {

    private val binding = ViewContactInfoBinding.inflate(LayoutInflater.from(context), this, true)

    val txtName: TextView
        get() = binding.txtName

    val txtDescription: TextView
        get() = binding.txtDescription

    val imgPhoto: ImageView
        get() = binding.imgPhoto

    val contactEmail: ContactMethodView
        get() = binding.contactEmail

    val dividerEmailPhone: View
        get() = binding.dividerEmailPhone

    val contactPhone: ContactMethodView
        get() = binding.contactPhone
}