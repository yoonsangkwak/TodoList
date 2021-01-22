package site.yoonsang.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import site.yoonsang.todolist.databinding.ActivityMainBinding
import site.yoonsang.todolist.fragmentClasses.CalendarFragment
import site.yoonsang.todolist.fragmentClasses.ProfileFragment
import site.yoonsang.todolist.fragmentClasses.TodoFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewPager: ViewPager
    private val adapter = MainFragmentPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mViewPager = binding.viewPager
        mViewPager.adapter = adapter
        mViewPager.setPageTransformer(true, ZoomOutPageTransformer())
        setupViewPager(mViewPager)
        binding.tabLayout.setupWithViewPager(mViewPager)
        binding.viewPager.currentItem = 1
    }

    private fun setupViewPager(viewPager: ViewPager) {
        adapter.addFragment(CalendarFragment(), "calendar")
        adapter.addFragment(TodoFragment(), "todo")
        adapter.addFragment(ProfileFragment(), "profile")
        viewPager.adapter = adapter
    }
}