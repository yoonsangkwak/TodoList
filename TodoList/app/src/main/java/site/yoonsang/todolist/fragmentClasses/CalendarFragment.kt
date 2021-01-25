package site.yoonsang.todolist.fragmentClasses

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import site.yoonsang.todolist.MainViewModel
import site.yoonsang.todolist.R
import site.yoonsang.todolist.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {

    private var mBinding: FragmentCalendarBinding? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCalendarBinding.inflate(inflater, container, false)
        mBinding = binding

        mBinding?.calendarRecyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CalendarTodoAdapter(emptyList())
        }

        mBinding?.calendarView?.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
            override fun onSelectedDayChange(
                view: CalendarView,
                year: Int,
                month: Int,
                dayOfMonth: Int
            ) {
                val date = "$year-${month + 1}-$dayOfMonth"
                viewModel.fetchData(date)
            }
        })

        viewModel.todoLiveData.observe(viewLifecycleOwner, {
            (mBinding?.calendarRecyclerView?.adapter as CalendarTodoAdapter).setData(it)
        })

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}