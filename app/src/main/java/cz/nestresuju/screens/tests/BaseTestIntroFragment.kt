package cz.nestresuju.screens.tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cz.nestresuju.databinding.FragmentInputTestIntroBinding

/**
 * Fragment for screen with info about input/output test.
 */
abstract class BaseTestIntroFragment : Fragment() {

    private var _binding: FragmentInputTestIntroBinding? = null
    private val viewBinding: FragmentInputTestIntroBinding
        get() = _binding!!

    abstract val headlineTextRes: Int
    abstract val intro1TextRes: Int
    abstract val intro2TextRes: Int

    abstract fun onContinueClicked()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentInputTestIntroBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            txtHeadline.text = getString(headlineTextRes)
            txtIntro1.text = getString(intro1TextRes)
            txtIntro2.text = getString(intro2TextRes)

            btnContinue.setOnClickListener {
                onContinueClicked()
            }
        }
    }
}