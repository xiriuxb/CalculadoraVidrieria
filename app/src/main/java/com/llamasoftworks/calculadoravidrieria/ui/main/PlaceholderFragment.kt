package com.llamasoftworks.calculadoravidrieria.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.llamasoftworks.calculadoravidrieria.ListaFragment
import com.llamasoftworks.calculadoravidrieria.ListaTotalFragment
import com.llamasoftworks.calculadoravidrieria.R
import com.llamasoftworks.calculadoravidrieria.VidriosFragment

class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_lista, container, false)
        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): Fragment {
            var fragment = Fragment()

            when (sectionNumber){
                1->fragment = ListaFragment()
                2->fragment = ListaTotalFragment()
                3->fragment = VidriosFragment()
            }
            return fragment
        }

    }
}