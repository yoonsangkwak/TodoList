package site.yoonsang.todolist.fragmentClasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.prolificinteractive.materialcalendarview.CalendarDay
import site.yoonsang.todolist.MainViewModel
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
        mBinding?.calendarView?.setSelectedDate(CalendarDay.today())

        val sundayDecorator = SundayDecorator()
        val saturdayDecorator = SaturdayDecorator()
        val todayDecorator = TodayDecorator(requireContext())

        mBinding?.calendarView?.addDecorators(sundayDecorator, saturdayDecorator, todayDecorator)

        mBinding?.calendarView?.setOnDateChangedListener { widget, date, selected ->
            val year = date.year
            val month = date.month
            val day = date.day
            val newDate = "$year-${month + 1}-$day"
            viewModel.fetchData(newDate)
        }

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