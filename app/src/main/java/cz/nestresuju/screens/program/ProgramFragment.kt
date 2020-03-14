package cz.nestresuju.screens.program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cz.nestresuju.R

class ProgramFragment : Fragment() {

    private lateinit var programViewModel: ProgramViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        programViewModel =
            ViewModelProviders.of(this).get(ProgramViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_program, container, false)
        val textView: TextView = root.findViewById(R.id.text_program)
        programViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
