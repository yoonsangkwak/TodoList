package site.yoonsang.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.viewModels
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayout
import site.yoonsang.todolist.databinding.ActivityMainBinding
import site.yoonsang.todolist.databinding.FragmentTodoBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var binding2: FragmentTodoBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("checkkk", "main1")
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding2 = FragmentTodoBinding.inflate(layoutInflater)
        binding2.recyclerView.apply {
            Log.d("checkkk", "nn")
            layoutManager = LinearLayoutManager(context)
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

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("CALENDAR"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("MAIN"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("PROFILE"))

        val adapter = ThreePageAdapter(LayoutInflater.from(this@MainActivity))
        binding.viewPager.adapter = adapter
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        Log.d("checkkk", "ada1")
        binding.viewPager.setCurrentItem(1)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.setCurrentItem(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}

class ThreePageAdapter(
    val layoutInflater: LayoutInflater
): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.d("checkkk", "when")
        when (position) {
            0 -> {
                val view = layoutInflater.inflate(R.layout.fragment_calendar, container, false)
                Log.d("checkkk", "0")
                container.addView(view)
                return view
            }
            1 -> {
                val view = layoutInflater.inflate(R.layout.fragment_todo, container, false)
                Log.d("checkkk", "1")
                container.addView(view)
                return view
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.fragment_profile, container, false)
                Log.d("checkkk", "2")
                container.addView(view)
                return view
            }
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }
}