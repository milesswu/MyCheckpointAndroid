package tech.mycheckpoint.models.Calendars

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.calendar_item.view.*
import tech.mycheckpoint.R

class CalendarAdapter(val calendars: MutableList<Calendar>): RecyclerView.Adapter<CalendarAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(R.layout.calendar_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return calendars.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val calendar = calendars[position]
        holder.setData(calendar)
    }

    inner class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun setData(calendar: Calendar) {
            view.calendar_summary.text = calendar!!.summary
            view.calendar_summary.setTextColor(Color.parseColor(calendar!!.bgColor))
        }

    }
}


