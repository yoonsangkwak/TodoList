package site.yoonsang.todolist.fragmentClasses

import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import site.yoonsang.todolist.R
import site.yoonsang.todolist.databinding.ItemCalendarTodoBinding

class CalendarTodoAdapter(
    private var myDataset: List<DocumentSnapshot>
) : RecyclerView.Adapter<CalendarTodoAdapter.CalendarTodoViewHolder>() {

    class CalendarTodoViewHolder(val binding: ItemCalendarTodoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarTodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_todo, parent, false)

        return CalendarTodoViewHolder(ItemCalendarTodoBinding.bind(view))
    }

    override fun onBindViewHolder(holder: CalendarTodoViewHolder, position: Int) {
        val todo = myDataset[position]
        holder.binding.calendarTodoText.text = todo.getString("text")

        if (todo.getBoolean("finish") == true) {
            holder.binding.calendarTodoText.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                setTypeface(null, Typeface.ITALIC)
                holder.binding.calendarCheckboxImageView.setImageResource(R.drawable.ic_checkbox)
            }
        } else {
            holder.binding.calendarTodoText.apply {
                paintFlags = 0
                setTypeface(null, Typeface.NORMAL)
                holder.binding.calendarCheckboxImageView.setImageResource(R.drawable.ic_checkbox_outline)
            }
        }
    }

    override fun getItemCount(): Int = myDataset.size

    fun setData(newData: List<DocumentSnapshot>) {
        myDataset = newData
        notifyDataSetChanged()
    }
}