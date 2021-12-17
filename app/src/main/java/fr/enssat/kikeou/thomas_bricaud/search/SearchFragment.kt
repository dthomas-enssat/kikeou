package fr.enssat.kikeou.thomas_bricaud.search

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.app.SearchManager
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.enssat.kikeou.thomas_bricaud.KikeouApplication
import fr.enssat.kikeou.thomas_bricaud.MainActivity
import fr.enssat.kikeou.thomas_bricaud.database.PersonRepository
import fr.enssat.kikeou.thomas_bricaud.database.WeekRepository
import fr.enssat.kikeou.thomas_bricaud.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.toList
import java.util.*
import android.provider.BaseColumns

import android.database.MatrixCursor
import android.view.inputmethod.InputMethodManager


class SearchFragment : Fragment() {
    //binding information
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchModel
    private lateinit var viewModelFactory: SearchModelFactory

    // element in the fragment
    private lateinit var searchView: SearchView
    private lateinit var adapter: CursorAdapter

    // database
    private lateinit var personRepository : PersonRepository
    private lateinit var weekRepository: WeekRepository
    private lateinit var persons: List<fr.enssat.kikeou.thomas_bricaud.database.Person>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        // integrate viewmodel for databinding
        viewModelFactory = SearchModelFactory(((context as MainActivity).application as KikeouApplication),"", "", "", "", "", "", "", "", "", "")
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

        personRepository = viewModel.personRepository
        weekRepository = viewModel.weekRepository
        persons = personRepository.getPersons()

        val from = arrayOf("name")
        val to = intArrayOf(R.id.text1)
        adapter = SimpleCursorAdapter(
            activity,
            R.layout.simple_list_item_1,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )

        searchView = binding.search.searchBar
        searchView.suggestionsAdapter = adapter

        // Getting selected (clicked) item suggestion
        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {


            override fun onSuggestionClick(position: Int): Boolean {
                val cursor: Cursor = adapter.getItem(position) as Cursor
                if(cursor.getColumnIndex("name") >= 0) {
                    @SuppressLint("Range")
                    val txt: String = cursor.getString(cursor.getColumnIndex("name"))
                    searchView.setQuery(txt, true)
                }
                return true
            }

            override fun onSuggestionSelect(position: Int): Boolean {
                // Do nothing
                return false
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    Log.i(TAG, query)
                    hideKeyboard()
                    // Chercher dans la database
                    searchAndInflate(query)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.i(TAG, newText)
                populateAdapter(newText)
                return true
            }
        })

        return binding.root
    }

    private fun searchAndInflate(query: String) {
        val person = personRepository.getPerson(query)[0]
        // setter on initialization
        binding.person.name.text = person.name
        binding.person.email.text = person.contact.mail
        binding.person.phone.text = person.contact.tel

        val cal = Calendar.getInstance()
        val num = cal.get(Calendar.WEEK_OF_YEAR)
        val weeks = weekRepository.getWeek(num, query)
        if(weeks.isNotEmpty()) {
            val week = weeks[0]
            binding.banner.spinner.text = week.num.toString()
            binding.week.monday.text = week.day1
            binding.week.tuesday.text = week.day2
            binding.week.wednesday.text = week.day3
            binding.week.thursday.text = week.day4
            binding.week.friday.text = week.day5
            binding.week.saturday.text = week.day6
        }
    }

    private fun populateAdapter(query: String) {
        val c = MatrixCursor(arrayOf(BaseColumns._ID, "name"))
        for (i in 0 until persons.count()) {
            if (persons[i].name.lowercase().startsWith(query.lowercase())){
                c.addRow(arrayOf<Any>(i, persons[i].name))
            }
        }
        adapter.changeCursor(c)
    }
}


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}