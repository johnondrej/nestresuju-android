package cz.nestresuju.screens.diary.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.databinding.ModelDiaryEmptyBinding

/**
 * Epoxy model for text shown when diary contains no entries.
 */
@EpoxyModelClass
open class DiaryEmptyModel : EpoxyModelWithView<TextView>() {

    override fun buildView(parent: ViewGroup): TextView {
        return ModelDiaryEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false).txtEmpty
    }
}