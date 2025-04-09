package com.example.kalorisayaci.ui.home.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kalorisayaci.R
import com.example.kalorisayaci.data.model.Food

class FoodDatabaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_food_database, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // In a real app, we would:
        // 1. Set up search functionality
        // 2. Display food categories
        // 3. Show popular/recent foods
        // 4. Handle food selection
        
        // Sample data that would come from database
        val popularFoods = getSamplePopularFoods()
    }
    
    private fun getSamplePopularFoods(): List<Food> {
        // This is sample data - in a real app, this would come from a database
        return listOf(
            Food(
                id = 1,
                name = "Elma",
                calories = 95,
                protein = 0.5f,
                carbs = 25f,
                fat = 0.3f,
                servingSize = "1 orta boy",
                servingSizeWeight = 150
            ),
            Food(
                id = 2,
                name = "Yoğurt",
                calories = 150,
                protein = 8.5f,
                carbs = 12f,
                fat = 8f,
                servingSize = "1 kase",
                servingSizeWeight = 200
            ),
            Food(
                id = 3,
                name = "Tam Tahıllı Ekmek",
                calories = 80,
                protein = 4f,
                carbs = 15f,
                fat = 1f,
                servingSize = "1 dilim",
                servingSizeWeight = 30
            ),
            Food(
                id = 4,
                name = "Tavuk Göğsü",
                calories = 165,
                protein = 31f,
                carbs = 0f,
                fat = 3.6f,
                servingSize = "100g",
                servingSizeWeight = 100
            )
        )
    }
} 