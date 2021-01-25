package site.yoonsang.todolist.fragmentClasses

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import site.yoonsang.todolist.MainViewModel
import site.yoonsang.todolist.R
import site.yoonsang.todolist.Todo
import site.yoonsang.todolist.databinding.FragmentTodoBinding
import java.text.SimpleDateFormat
import java.util.*

class TodoFragment : Fragment() {

    private var mBinding: FragmentTodoBinding? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTodoBinding.inflate(inflater, container, false)
        mBinding = binding

        mBinding?.recyclerView?.layoutManager = LinearLayoutManager(activity)
        mBinding?.recyclerView?.adapter = TodoAdapter(
            emptyList(),
            onClickDeleteIcon = {
                viewModel.deleteTodo(it)
            },
            onClickItem = {
                viewModel.toggleTodo(it)
            }
        )

        mBinding?.addButton?.setOnClickListener {
            if (mBinding?.editText?.text.toString() != "") {
                val todo = Todo(mBinding?.editText?.text.toString())
                viewModel.addTodo(todo)
                val dateFormat = SimpleDateFormat("yyyy M dd", Locale.KOREA).format(todo.createdDate)
                Log.d("checkkk", "" + dateFormat)
                mBinding?.editText?.setText("")
            } else {
                customToast("할 일을 입력해주세요")
            }
        }

        // 관찰 UI 업데이트
        viewModel.todoLiveData.observe(viewLifecycleOwner, {
            (mBinding?.recyclerView?.adapter as TodoAdapter).setData(it)
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