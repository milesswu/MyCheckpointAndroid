package tech.mycheckpoint.ui.three

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import tech.mycheckpoint.R

class ThreeFragment : Fragment() {

    private lateinit var threeViewModel: ThreeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        threeViewModel =
            ViewModelProviders.of(this).get(ThreeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_three, container, false)
        val textView: TextView = root.findViewById(R.id.text_three)
        threeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}