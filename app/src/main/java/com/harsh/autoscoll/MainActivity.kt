package com.harsh.autoscoll

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.harsh.autoscoll.databinding.ActivityMainBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var scrollHandler = Handler(Looper.getMainLooper())
    private val scrollItemList = mutableListOf<ScrollItem>()

    var scrollRunnable = Runnable {
        binding.viewPager.currentItem = binding.viewPager.currentItem + 1
    }

    companion object {
        const val SCROLL_DELAY = 2500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        scrollItemList.add(ScrollItem(R.drawable.image1))
        scrollItemList.add(ScrollItem(R.drawable.image2))
        scrollItemList.add(ScrollItem(R.drawable.image3))
        scrollItemList.add(ScrollItem(R.drawable.image4))
        scrollItemList.add(ScrollItem(R.drawable.image5))

        binding.viewPager.apply {
            adapter = ScrollAdapter(scrollItemList, binding.viewPager)
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }

        binding.viewPager.apply {
            setPageTransformer(compositePageTransformer)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    scrollHandler.removeCallbacks(scrollRunnable)
                    scrollHandler.postDelayed(scrollRunnable, SCROLL_DELAY)
                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        scrollHandler.removeCallbacks(scrollRunnable)
    }

    override fun onResume() {
        super.onResume()
        scrollHandler.postDelayed(scrollRunnable, SCROLL_DELAY)
    }

}