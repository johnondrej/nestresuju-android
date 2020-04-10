package cz.nestresuju.screens.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Base [Fragment] with view binding setup. Should be used for simple screens without [ViewModel].
 */
abstract class BaseFragment<B : ViewBinding> : Fragment() {

    protected var _binding: B? = null
    protected val viewBinding: B
        get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}