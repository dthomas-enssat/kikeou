package fr.enssat.kikeou.thomas_bricaud.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import fr.enssat.kikeou.thomas_bricaud.R
import fr.enssat.kikeou.thomas_bricaud.databinding.FragmentSearchBinding
import java.util.*

class SearchFragment : Fragment() {
    //binding information
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchModel
    private lateinit var viewModelFactory: SearchModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        // integrate viewmodel for databinding
        viewModelFactory = SearchModelFactory("", "", "", "", "", "", "", "", "", "")
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner

        // setter on initialization
        binding.person.name.text = viewModel.name
        binding.person.email.text = viewModel.email
        binding.person.phone.text = viewModel.phone

        binding.banner.spinner.text = viewModel.week
        binding.week.monday.text = viewModel.monday
        binding.week.tuesday.text = viewModel.tuesday
        binding.week.wednesday.text = viewModel.wednesday
        binding.week.thursday.text = viewModel.thursday
        binding.week.friday.text = viewModel.friday
        binding.week.saturday.text = viewModel.saturday

        return binding.root
    }
}