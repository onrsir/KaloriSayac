package com.example.kalorisayaci.data.model

data class FoodEntry(
    val id: Long = 0,
    val food: Food,
    val quantity: Float = 1f,
    val servingSize: String = food.servingSize,
    val mealId: Long = 0
) {
    val calories: Int
        get() = (food.calories * quantity).toInt()
        
    val protein: Float
        get() = food.protein * quantity
        
    val carbs: Float
        get() = food.carbs * quantity
        
    val fat: Float
        get() = food.fat * quantity
} 