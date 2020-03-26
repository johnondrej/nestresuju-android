package cz.nestresuju.screens.diary.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import cz.nestresuju.common.extensions.formatDay
import cz.nestresuju.databinding.ModelDiaryDayHeaderBinding
import org.threeten.bp.LocalDate

/**
 * Epoxy model for day delimiter.
 */
@EpoxyModelClass
open class DiaryDayHeaderModel : EpoxyModelWithView<TextView>() {

    @EpoxyAttribute
    lateinit var date: LocalDate

    override fun buildView(parent: ViewGroup): TextView {
        return ModelDiaryDayHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false).txtDay
    }

    override fun bind(view: TextView) {
        super.bind(view)
        with(ModelDiaryDayHeaderBinding.bind(view)) {
            txtDay.text = date.formatDay(view.context)
        }
    }
}