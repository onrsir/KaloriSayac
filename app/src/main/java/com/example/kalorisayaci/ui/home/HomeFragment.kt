package com.example.kalorisayaci.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kalorisayaci.R
import com.example.kalorisayaci.databinding.FragmentHomeBinding
import com.example.kalorisayaci.ui.home.tabs.DailyTrackingFragment
import com.example.kalorisayaci.ui.home.tabs.FoodDatabaseFragment
import com.example.kalorisayaci.ui.home.tabs.GoalsFragment
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    // Sample data - would come from a ViewModel in a real app
    private val caloriesConsumed = 1250
    private val caloriesTarget = 2000
    private val protein = 45
    private val carbs = 150
    private val fat = 35
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupDate()
        setupCalorieProgress()
        setupNutrientInfo()
        setupQuickAddItems()
        setupTabs()
    }
    
    private fun setupDate() {
        val dateFormat = SimpleDateFormat("d MMMM yyyy, EEEE", Locale("tr"))
        val currentDate = dateFormat.format(Date())
        binding.tvDate.text = currentDate
    }
    
    private fun setupCalorieProgress() {
        // Set calorie values
        binding.tvCaloriesConsumed.text = caloriesConsumed.toString()
        binding.tvCaloriesTarget.text = getString(R.string.calories_target, caloriesTarget)
        
        // Calculate progress percentage (0-100)
        val progressPercentage = ((caloriesConsumed.toFloat() / caloriesTarget.toFloat()) * 100).toInt()
        binding.progressCalories.progress = progressPercentage
    }
    
    private fun setupNutrientInfo() {
        binding.tvProtein.text = "$protein g"
        binding.tvCarbs.text = "$carbs g"
        binding.tvFat.text = "$fat g"
    }
    
    private fun setupQuickAddItems() {
        val quickAddAdapter = QuickAddAdapter(getSampleQuickAddItems()) { food ->
            // Handle quick add item click
            // This would add the food to today's log
            android.widget.Toast.makeText(
                requireContext(),
                "${food.name} eklendi (${food.calories} kcal)",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
        
        binding.rvQuickAdd.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = quickAddAdapter
        }
    }
    
    private fun setupTabs() {
        // Set up the ViewPager2 with the TabLayout
        val pagerAdapter = HomePagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        
        // Link the TabLayout and the ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_daily_tracking)
                1 -> getString(R.string.tab_food_database)
                2 -> getString(R.string.tab_goals)
                else -> "Tab ${position + 1}"
            }
        }.attach()
    }
    
    private fun getSampleQuickAddItems(): List<QuickAddItem> {
        return listOf(
            QuickAddItem("Elma", 95, R.drawable.ic_launcher_foreground),
            QuickAddItem("Yoğurt", 150, R.drawable.ic_launcher_foreground),
            QuickAddItem("Muz", 105, R.drawable.ic_launcher_foreground),
            QuickAddItem("Kahve", 5, R.drawable.ic_launcher_foreground),
            QuickAddItem("Yumurta", 78, R.drawable.ic_launcher_foreground)
        )
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    /**
     * ViewPager adapter for the home screen tabs
     */
    private inner class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        
        override fun getItemCount(): Int = 3
        
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> DailyTrackingFragment()
                1 -> FoodDatabaseFragment()
                2 -> GoalsFragment()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
    }
}

data class QuickAddItem(
    val name: String,
    val calories: Int,
    val imageResId: Int
) 