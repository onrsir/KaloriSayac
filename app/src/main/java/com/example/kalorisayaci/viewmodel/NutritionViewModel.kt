package com.example.kalorisayaci.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kalorisayaci.data.DatabaseHelper
import com.example.kalorisayaci.data.model.Food
import com.example.kalorisayaci.data.model.Meal
import com.example.kalorisayaci.data.model.MealType
import com.example.kalorisayaci.ui.home.QuickAddItem

class NutritionViewModel : ViewModel() {

    // Kalori ve besin değerleri
    private val _caloriesConsumed = MutableLiveData<Int>()
    val caloriesConsumed: LiveData<Int> = _caloriesConsumed

    private val _calorieTarget = MutableLiveData<Int>()
    val calorieTarget: LiveData<Int> = _calorieTarget

    private val _protein = MutableLiveData<Float>()
    val protein: LiveData<Float> = _protein

    private val _carbs = MutableLiveData<Float>()
    val carbs: LiveData<Float> = _carbs

    private val _fat = MutableLiveData<Float>()
    val fat: LiveData<Float> = _fat

    // Öğünler listesi
    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> = _meals

    // Besinler listesi
    private val _popularFoods = MutableLiveData<List<Food>>()
    val popularFoods: LiveData<List<Food>> = _popularFoods

    // Hızlı ekleme öğeleri
    private val _quickAddItems = MutableLiveData<List<QuickAddItem>>()
    val quickAddItems: LiveData<List<QuickAddItem>> = _quickAddItems

    // İlk değerleri yükle
    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        // Hedef ve tüketilen değerleri al
        _caloriesConsumed.value = DatabaseHelper.getTotalCalories()
        _calorieTarget.value = DatabaseHelper.getCalorieTarget()

        // Makro besinleri al
        val macros = DatabaseHelper.getMacros()
        _protein.value = macros.first
        _carbs.value = macros.second
        _fat.value = macros.third

        // Öğünleri yükle
        _meals.value = DatabaseHelper.getMeals()

        // Popüler besinleri yükle
        _popularFoods.value = DatabaseHelper.getPopularFoods()

        // Hızlı ekleme öğelerini hazırla
        prepareQuickAddItems()
    }

    // QuickAddItems - modelden UI sınıfına dönüştür
    private fun prepareQuickAddItems() {
        val foods = DatabaseHelper.getPopularFoods()
        _quickAddItems.value = foods.map { food ->
            QuickAddItem(
                name = food.name,
                calories = food.calories,
                imageUrl = getImageUrlForFood(food.name)
            )
        }
    }

    // Besin adından görsel URL'si belirle
    private fun getImageUrlForFood(foodName: String): String {
        // Map food names to appropriate image URLs
        return when (foodName.lowercase()) {
            "elma" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/apple.png"
            "muz" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/banana.png" 
            "ekmek" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/bread.png"
            "burger" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/burger.png"
            "tavuk" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/chicken.png"
            "çikolata" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/chocolate.png"
            "kola" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/coke.png"
            "kurabiye" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/cookie.png"
            "pizza" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/pizza.png"
            "sosisli" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/hotdog.png"
            "makarna" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/pasta.png"
            "et" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/meat.png"
            "sütlaç" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/rice_pudding.png"
            "salata" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/salad.png"
            "sandviç" -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/sandwich.png"
            else -> "https://raw.githubusercontent.com/onrsir/KaloriSayac/master/fastfood_icons/default_food.png"
        }
    }

    // Hızlı ekleme işlemi
    fun quickAddFood(quickAddItem: QuickAddItem) {
        // İsme göre yiyeceği bul
        val food = DatabaseHelper.getAllFoods().find { it.name == quickAddItem.name }
        food?.let {
            // Atıştırmalıklar öğününe ekle (varsayılan)
            if (DatabaseHelper.quickAddFood(it)) {
                refreshData()
            }
        }
    }

    // Belirli bir öğüne besin ekle
    fun addFoodToMeal(food: Food, mealType: MealType, quantity: Float = 1f) {
        if (DatabaseHelper.addFoodToMeal(mealType, food, quantity)) {
            refreshData()
        }
    }

    // Verileri yenile
    private fun refreshData() {
        _caloriesConsumed.value = DatabaseHelper.getTotalCalories()
        
        val macros = DatabaseHelper.getMacros()
        _protein.value = macros.first
        _carbs.value = macros.second
        _fat.value = macros.third
        
        _meals.value = DatabaseHelper.getMeals()
    }

    // Kalori hedefini güncelle
    fun updateCalorieTarget(target: Int) {
        DatabaseHelper.updateCalorieTarget(target)
        _calorieTarget.value = target
    }
} 