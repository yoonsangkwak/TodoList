package site.yoonsang.todolist.fragmentClasses

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import site.yoonsang.todolist.MainViewModel
import site.yoonsang.todolist.Todo
import site.yoonsang.todolist.databinding.FragmentTodoBinding

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
        mBinding?.recyclerView?.hasFixedSize()
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
                mBinding?.editText?.setText("")
            } else {
                Toast.makeText(context, "할 일을 입력해주세요", Toast.LENGTH_SHORT).show()
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
}