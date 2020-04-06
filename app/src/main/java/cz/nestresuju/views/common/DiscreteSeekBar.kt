package cz.nestresuju.views.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import cz.nestresuju.databinding.ViewDiscreteSeekbarBinding

/**
 * Discrete [SeekBar] with labels.
 */
class DiscreteSeekBar(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes) {

    val seekBar: AppCompatSeekBar = ViewDiscreteSeekbarBinding.inflate(LayoutInflater.from(context), this, true).seek
}