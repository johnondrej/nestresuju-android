package cz.nestresuju.screens.tests.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentInputTestIntroBinding

/**
 * Fragment for screen with info about input test.
 */
class InputTestIntroFragment : Fragment() {

    private var _binding: FragmentInputTestIntroBinding? = null
    private val viewBinding: FragmentInputTestIntroBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentInputTestIntroBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.input_test_intro_title)

        viewBinding.btnContinue.setOnClickListener {
            // TODO
        }
    }
}