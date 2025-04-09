package com.example.kalorisayaci.data.model

data class Food(
    val id: Long = 0,
    val name: String,
    val calories: Int,
    val protein: Float = 0f,
    val carbs: Float = 0f,
    val fat: Float = 0f,
    val servingSize: String = "",
    val servingSizeUnit: String = "g",
    val servingSizeWeight: Int = 0,
    val imageUrl: String? = null,
    val categoryId: Long = 0
) 