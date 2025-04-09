package com.example.kalorisayaci.data.model

import java.util.Date

data class DailyNutrition(
    val id: Long = 0,
    val date: Date = Date(),
    val caloriesConsumed: Int = 0,
    val caloriesTarget: Int = 2000,
    val protein: Int = 0,
    val proteinTarget: Int = 50,
    val carbs: Int = 0,
    val carbsTarget: Int = 150,
    val fat: Int = 0,
    val fatTarget: Int = 35,
    val meals: List<Meal> = emptyList()
) {
    val calorieProgress: Float
        get() = if (caloriesTarget > 0) caloriesConsumed.toFloat() / caloriesTarget.toFloat() else 0f
        
    val proteinProgress: Float
        get() = if (proteinTarget > 0) protein.toFloat() / proteinTarget.toFloat() else 0f
        
    val carbsProgress: Float
        get() = if (carbsTarget > 0) carbs.toFloat() / carbsTarget.toFloat() else 0f
        
    val fatProgress: Float
        get() = if (fatTarget > 0) fat.toFloat() / fatTarget.toFloat() else 0f
} 