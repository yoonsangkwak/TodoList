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
                customToast("hi")
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

    fun customToast(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        val view = toast.view
        val color = resources.getColor(R.color.design_default_color_primary)
        view?.setBackgroundColor(color)

        val group = toast.view as ViewGroup
        val msgTextView = group.getChildAt(0) as TextView
        msgTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
        msgTextView.setTextColor(resources.getColor(R.color.colorWhite))
        msgTextView.typeface.isBold
        toast.show()
    }
}