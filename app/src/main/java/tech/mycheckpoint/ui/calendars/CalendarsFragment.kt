package tech.mycheckpoint.ui.calendars

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_calendars.*

import tech.mycheckpoint.R
import tech.mycheckpoint.models.Calendars.Calendar
import tech.mycheckpoint.models.Calendars.CalendarAdapter
import tech.mycheckpoint.models.Calendars.Calendars

class CalendarsFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarsFragment()
    }

    private lateinit var viewModel: CalendarsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendars, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CalendarsViewModel::class.java)
        // TODO: Use the ViewModel
        showCalendars(Calendars.list)
    }

    private fun showCalendars(calendars: MutableList<Calendar>) {
        calendarsList.layoutManager = LinearLayoutManager(activity)
        calendarsList.adapter = CalendarAdapter(calendars)
    }

}
