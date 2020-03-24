package cz.nestresuju.views.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.airbnb.epoxy.EpoxyRecyclerView
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.databinding.ViewCustomListBinding

/**
 * [EpoxyRecyclerView] with additional support for showing messages on errors, empty state, etc.
 */
class CustomListView(context: Context, attributes: AttributeSet) : FrameLayout(context, attributes) {

    private val binding = ViewCustomListBinding.inflate(LayoutInflater.from(context), this, true)

    var contentVisible: Boolean
        get() = binding.list.visible
        set(value) {
            binding.list.visible = value
        }

    var emptyTextVisible: Boolean
        get() = binding.txtEmpty.visible
        set(value) {
            binding.txtEmpty.visible = value
        }

    var emptyText: CharSequence
        get() = binding.txtEmpty.text
        set(value) {
            binding.txtEmpty.text = value
        }

    var emptyTextResource: Int
        @Deprecated("", level = DeprecationLevel.ERROR) get() = throw UnsupportedOperationException()
        set(value) {
            binding.txtEmpty.text = context.getString(value)
        }

    var errorTextVisible: Boolean
        get() = binding.txtError.visible
        set(value) {
            binding.txtError.visible = value
        }

    var errorTextResource: Int
        @Deprecated("", level = DeprecationLevel.ERROR) get() = throw UnsupportedOperationException()
        set(value) {
            binding.txtError.text = context.getString(value)
        }

    var errorText: CharSequence
        get() = binding.txtError.text
        set(value) {
            binding.txtError.text = value
        }

    init {
        val attrs = context.obtainStyledAttributes(attributes, R.styleable.CustomListView)

        val defaultEmptyText = attrs.getResourceId(R.styleable.CustomListView_emptyText, -1)
        val defaultErrorText = attrs.getResourceId(R.styleable.CustomListView_errorText, -1)

        if (defaultEmptyText != -1) {
            emptyTextResource = defaultEmptyText
        }
        if (defaultErrorText != -1) {
            errorTextResource = defaultErrorText
        }

        attrs.recycle()
    }

    fun showContent() {
        emptyTextVisible = false
        errorTextVisible = false
        contentVisible = true
    }

    fun showEmptyText(text: CharSequence? = null) {
        text?.let { emptyText = it }
        contentVisible = false
        errorTextVisible = false
        emptyTextVisible = true
    }

    fun showEmptyText(textRes: Int? = null) {
        textRes?.let { emptyTextResource = it }
        contentVisible = false
        errorTextVisible = false
        emptyTextVisible = true
    }

    fun showErrorText(text: CharSequence? = null) {
        text?.let { errorText = it }
        contentVisible = false
        emptyTextVisible = false
        errorTextVisible = true
    }

    fun showErrorText(textRes: Int? = null) {
        textRes?.let { errorTextResource = it }
        contentVisible = false
        emptyTextVisible = false
        errorTextVisible = true
    }
}