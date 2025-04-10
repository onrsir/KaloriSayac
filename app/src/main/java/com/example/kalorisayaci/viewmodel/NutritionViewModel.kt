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
        return when (foodName.lowercase()) {
            // Wikipedia Commons'dan alınan gerçek yiyecek görselleri
            "elma" -> "https://upload.wikimedia.org/wikipedia/commons/1/15/Red_Apple.jpg"
            "muz" -> "https://upload.wikimedia.org/wikipedia/commons/8/8a/Banana-Single.jpg"
            "ekmek", "tam tahıllı ekmek" -> "https://upload.wikimedia.org/wikipedia/commons/7/71/Anadama_bread_%281%29.jpg"
            "yoğurt" -> "https://upload.wikimedia.org/wikipedia/commons/e/ea/Turkish_Yogurt.jpg"
            "tavuk göğsü", "tavuk" -> "https://upload.wikimedia.org/wikipedia/commons/3/3c/Chicken_breast_fillet.jpg"
            "burger" -> "https://upload.wikimedia.org/wikipedia/commons/4/4d/Cheeseburger.jpg"
            "çikolata" -> "https://upload.wikimedia.org/wikipedia/commons/7/70/Chocolate_%28blue_background%29.jpg"
            "kola" -> "https://upload.wikimedia.org/wikipedia/commons/c/cf/Tumbler_of_cola_with_ice.jpg"
            "pizza" -> "https://upload.wikimedia.org/wikipedia/commons/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg"
            "makarna" -> "https://upload.wikimedia.org/wikipedia/commons/3/3f/Pasta_03.jpg"
            "et" -> "https://upload.wikimedia.org/wikipedia/commons/2/2a/Beef_fillet_steak_with_mushrooms.jpg"
            "salata" -> "https://upload.wikimedia.org/wikipedia/commons/9/94/Salad_platter.jpg"
            "kahve" -> "https://upload.wikimedia.org/wikipedia/commons/4/45/A_small_cup_of_coffee.JPG"
            "yumurta" -> "https://upload.wikimedia.org/wikipedia/commons/5/5e/Chicken_egg_2009-06-04.jpg"
            // Diğer yiyecekler varsayılan bir görsel kullanacak
            else -> "https://upload.wikimedia.org/wikipedia/commons/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg" // Varsayılan yemek görseli
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