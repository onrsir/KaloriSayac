package com.example.kalorisayaci.data.model

data class Meal(
    val id: Long = 0,
    val name: String,
    val type: MealType,
    val dateTime: Long = System.currentTimeMillis(),
    val foods: List<FoodEntry> = emptyList()
) {
    val totalCalories: Int
        get() = foods.sumOf { it.calories }
        
    val totalProtein: Float
        get() = foods.sumOf { it.protein.toDouble() }.toFloat()
        
    val totalCarbs: Float
        get() = foods.sumOf { it.carbs.toDouble() }.toFloat()
        
    val totalFat: Float
        get() = foods.sumOf { it.fat.toDouble() }.toFloat()
}

enum class MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK
} 