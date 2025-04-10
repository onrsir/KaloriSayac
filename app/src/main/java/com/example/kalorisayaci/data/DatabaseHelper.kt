package com.example.kalorisayaci.data

import com.example.kalorisayaci.data.model.Food
import com.example.kalorisayaci.data.model.FoodEntry
import com.example.kalorisayaci.data.model.Meal
import com.example.kalorisayaci.data.model.MealType
import com.example.kalorisayaci.R

/**
 * Bu sınıf gerçek bir veritabanı yerine geçici olarak kullanılmaktadır.
 * Gerçek uygulamada Room veritabanı veya Firebase gibi bir çözüm kullanılmalıdır.
 */
object DatabaseHelper {
    
    // Besin veritabanı (örnek)
    private val foodDatabase = mutableListOf(
        Food(
            id = 1,
            name = "Elma",
            calories = 95,
            protein = 0.5f,
            carbs = 25f,
            fat = 0.3f,
            servingSize = "1 orta boy",
            servingSizeWeight = 150,
            imageUrl = null,
            categoryId = 1
        ),
        Food(
            id = 2,
            name = "Yoğurt",
            calories = 150,
            protein = 8.5f,
            carbs = 12f,
            fat = 8f,
            servingSize = "1 kase",
            servingSizeWeight = 200,
            imageUrl = null,
            categoryId = 3
        ),
        Food(
            id = 3, 
            name = "Muz",
            calories = 105,
            protein = 1.3f,
            carbs = 27f,
            fat = 0.4f,
            servingSize = "1 orta boy",
            servingSizeWeight = 120,
            imageUrl = null,
            categoryId = 1
        ),
        Food(
            id = 4,
            name = "Kahve",
            calories = 5,
            protein = 0.3f,
            carbs = 0f,
            fat = 0f,
            servingSize = "1 fincan",
            servingSizeWeight = 240,
            imageUrl = null,
            categoryId = 6
        ),
        Food(
            id = 5,
            name = "Yumurta",
            calories = 78,
            protein = 6.3f,
            carbs = 0.6f,
            fat = 5.3f,
            servingSize = "1 adet",
            servingSizeWeight = 50,
            imageUrl = null,
            categoryId = 2
        ),
        Food(
            id = 6,
            name = "Tam Tahıllı Ekmek",
            calories = 80,
            protein = 4f,
            carbs = 15f,
            fat = 1f,
            servingSize = "1 dilim",
            servingSizeWeight = 30,
            imageUrl = null,
            categoryId = 4
        ),
        Food(
            id = 7,
            name = "Tavuk Göğsü",
            calories = 165,
            protein = 31f,
            carbs = 0f,
            fat = 3.6f,
            servingSize = "100g",
            servingSizeWeight = 100,
            imageUrl = null,
            categoryId = 2
        )
    )
    
    // Günlük Öğünler (aktif kullanıcı için)
    private val userMeals = mutableListOf(
        Meal(
            id = 1,
            name = "Kahvaltı",
            type = MealType.BREAKFAST,
            foods = mutableListOf()
        ),
        Meal(
            id = 2,
            name = "Öğle Yemeği",
            type = MealType.LUNCH,
            foods = mutableListOf()
        ),
        Meal(
            id = 3,
            name = "Akşam Yemeği",
            type = MealType.DINNER,
            foods = mutableListOf()
        ),
        Meal(
            id = 4,
            name = "Atıştırmalıklar",
            type = MealType.SNACK,
            foods = mutableListOf()
        )
    )
    
    // Toplam tüketilen kalori
    private var totalCaloriesConsumed = 0
    
    // Hedef kalori
    private var calorieTarget = 2000
    
    // Toplam makrolar
    private var totalProtein = 0f
    private var totalCarbs = 0f
    private var totalFat = 0f
    
    // Popüler besinleri getir
    fun getPopularFoods(): List<Food> {
        return foodDatabase.take(5)
    }
    
    // Tüm besinleri getir
    fun getAllFoods(): List<Food> {
        return foodDatabase.toList()
    }
    
    // ID'ye göre besin getir
    fun getFoodById(id: Long): Food? {
        return foodDatabase.find { it.id == id }
    }
    
    // Öğünleri getir
    fun getMeals(): List<Meal> {
        return userMeals.toList()
    }
    
    // Öğüne besin ekle
    fun addFoodToMeal(mealType: MealType, food: Food, quantity: Float = 1f): Boolean {
        val meal = userMeals.find { it.type == mealType }
        if (meal != null) {
            val foodEntry = FoodEntry(
                id = System.currentTimeMillis(),
                food = food,
                quantity = quantity,
                mealId = meal.id
            )
            
            // Öğüne besin ekle
            (meal.foods as MutableList).add(foodEntry)
            
            // Toplam değerleri güncelle
            updateTotals()
            
            return true
        }
        return false
    }
    
    // Hızlı ekleme için öğün seçimi - örneğin atıştırmalıklar
    fun quickAddFood(food: Food): Boolean {
        return addFoodToMeal(MealType.SNACK, food)
    }
    
    // Toplam değerleri güncelle
    private fun updateTotals() {
        var calories = 0
        var protein = 0f
        var carbs = 0f
        var fat = 0f
        
        userMeals.forEach { meal ->
            meal.foods.forEach { entry ->
                calories += entry.calories
                protein += entry.protein
                carbs += entry.carbs
                fat += entry.fat
            }
        }
        
        totalCaloriesConsumed = calories
        totalProtein = protein
        totalCarbs = carbs
        totalFat = fat
    }
    
    // Toplam tüketilen kaloriyi getir
    fun getTotalCalories(): Int {
        return totalCaloriesConsumed
    }
    
    // Hedef kaloriyi getir
    fun getCalorieTarget(): Int {
        return calorieTarget
    }
    
    // Makro değerlerini getir
    fun getMacros(): Triple<Float, Float, Float> {
        return Triple(totalProtein, totalCarbs, totalFat)
    }
    
    // Hedefleri güncelle
    fun updateCalorieTarget(target: Int) {
        calorieTarget = target
    }
    
    // Öğün İsimleri
    fun getMealName(mealType: MealType): String {
        return when(mealType) {
            MealType.BREAKFAST -> "Kahvaltı"
            MealType.LUNCH -> "Öğle Yemeği"
            MealType.DINNER -> "Akşam Yemeği"
            MealType.SNACK -> "Atıştırmalıklar"
        }
    }
    
    // Öğündeki toplam kaloriyi hesapla
    fun getMealCalories(mealType: MealType): Int {
        val meal = userMeals.find { it.type == mealType }
        return meal?.totalCalories ?: 0
    }
} 