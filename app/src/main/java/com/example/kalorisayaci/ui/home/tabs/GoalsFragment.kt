package com.example.kalorisayaci.ui.home.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kalorisayaci.R

class GoalsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_goals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // In a real app, we would:
        // 1. Show current goals (calories, macros)
        // 2. Allow changing goals with sliders/inputs
        // 3. Save updated goals to database
        
        // Sample values - in a real app these would be loaded from preferences/database
        val calorieGoal = 2000
        val proteinGoal = 25 // percentage
        val carbsGoal = 50 // percentage
        val fatGoal = 25 // percentage
        val currentWeight = 75.0 // kg
        val targetWeight = 70.0 // kg
        
        // In a complete app, we would set up the UI components with these values
        // and handle saving changes to them
        
        // For now, just add a simple save button click handler
        view.findViewById<View>(R.id.btn_save_goals)?.setOnClickListener {
            Toast.makeText(requireContext(), "Hedefler kaydedildi", Toast.LENGTH_SHORT).show()
        }
    }
} 