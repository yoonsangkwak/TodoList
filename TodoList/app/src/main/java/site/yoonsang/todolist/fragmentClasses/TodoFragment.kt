package site.yoonsang.todolist.fragmentClasses

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import site.yoonsang.todolist.MainViewModel
import site.yoonsang.todolist.R
import site.yoonsang.todolist.Todo
import site.yoonsang.todolist.databinding.FragmentTodoBinding

class TodoFragment : Fragment() {

    private lateinit var binding: FragmentTodoBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTodoBinding.inflate(layoutInflater)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = TodoAdapter(
                emptyList(),
                onClickDeleteIcon = {
                    viewModel.deleteTodo(it)
                },
                onClickItem = {
                    viewModel.toggleTodo(it)
                }
            )
        }

        binding.addButton.setOnClickListener {
            if (binding.editText.text.toString() != "") {
                val todo = Todo(binding.editText.text.toString())
                viewModel.addTodo(todo)
                binding.editText.setText("")
            } else {
                Toast.makeText(context, "할 일을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        // 관찰 UI 업데이트
        viewModel.todoLiveData.observe(viewLifecycleOwner, {
            (binding.recyclerView.adapter as TodoAdapter).setData(it)
        })
    }
}