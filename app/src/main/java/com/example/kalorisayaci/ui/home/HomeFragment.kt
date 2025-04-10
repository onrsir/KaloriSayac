package com.example.kalorisayaci.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kalorisayaci.R
import com.example.kalorisayaci.data.model.Food
import com.example.kalorisayaci.databinding.FragmentHomeBinding
import com.example.kalorisayaci.ui.home.tabs.DailyTrackingFragment
import com.example.kalorisayaci.ui.home.tabs.FoodDatabaseFragment
import com.example.kalorisayaci.ui.home.tabs.GoalsFragment
import com.example.kalorisayaci.viewmodel.NutritionViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    // Use ViewModel instead of static data
    private val nutritionViewModel: NutritionViewModel by viewModels()
    
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
        setupObservers()
        setupTabs()
    }
    
    private fun setupDate() {
        val dateFormat = SimpleDateFormat("d MMMM yyyy, EEEE", Locale("tr"))
        val currentDate = dateFormat.format(Date())
        binding.tvDate.text = currentDate
    }
    
    private fun setupObservers() {
        // Observe calories data and update UI
        nutritionViewModel.caloriesConsumed.observe(viewLifecycleOwner, Observer { calories ->
            binding.tvCaloriesConsumed.text = calories.toString()
            updateCalorieProgress()
        })
        
        nutritionViewModel.calorieTarget.observe(viewLifecycleOwner, Observer { target ->
            binding.tvCaloriesTarget.text = getString(R.string.calories_target, target)
            updateCalorieProgress()
        })
        
        // Observe macronutrients
        nutritionViewModel.protein.observe(viewLifecycleOwner, Observer { protein ->
            binding.tvProtein.text = "$protein g"
        })
        
        nutritionViewModel.carbs.observe(viewLifecycleOwner, Observer { carbs ->
            binding.tvCarbs.text = "$carbs g"
        })
        
        nutritionViewModel.fat.observe(viewLifecycleOwner, Observer { fat ->
            binding.tvFat.text = "$fat g"
        })
        
        // Observe quick add items
        nutritionViewModel.quickAddItems.observe(viewLifecycleOwner, Observer { items ->
            setupQuickAddItems(items)
        })
    }
    
    private fun updateCalorieProgress() {
        val consumed = nutritionViewModel.caloriesConsumed.value ?: 0
        val target = nutritionViewModel.calorieTarget.value ?: 1
        
        // Calculate progress percentage (0-100)
        val progressPercentage = ((consumed.toFloat() / target.toFloat()) * 100).toInt()
        binding.progressCalories.progress = progressPercentage
    }
    
    private fun setupQuickAddItems(quickAddItems: List<Food>) {
        val items = quickAddItems.map { 
            QuickAddItem(it.name, it.calories, it.imageUrl)
        }
        
        val quickAddAdapter = QuickAddAdapter(items) { item ->
            // Add food to meals via ViewModel
            nutritionViewModel.quickAddFood(item)
            
            // Show confirmation toast
            android.widget.Toast.makeText(
                requireContext(),
                "${item.name} eklendi (${item.calories} kcal)",
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
                0 -> DailyTrackingFragment.newInstance(nutritionViewModel)
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
    val imageUrl: String
) 