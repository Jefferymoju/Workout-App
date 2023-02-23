package com.snacked.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.snacked.a7minutesworkout.databinding.ActivityCalculateBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class CalculateBmi : AppCompatActivity() {

    companion object{
        private const val METRIC_UNIT_VIEW ="METRIC_UNIT_VIEW" // Metric Unit View
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" //Us Unit View
    }
    private var currentVisibleView: String =
        METRIC_UNIT_VIEW// A variable to hold a value to make a selected view visible

    private var binding: ActivityCalculateBmiBinding? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE YOUR BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeMetricUnitsViewVisible()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId:Int ->
            if(checkedId == R.id.rbMetricUnits){
                makeMetricUnitsViewVisible()
            }else{
                makeUSMetricUnitsViewVisible()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {

            calculateUnits()
        }


    }

    private fun makeMetricUnitsViewVisible(){
        currentVisibleView = METRIC_UNIT_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE
        binding?.tilMetricUnitUSHeightFeet?.visibility = View.GONE
        binding?.tilMetricUSUnitHeightInch?.visibility = View.GONE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeUSMetricUnitsViewVisible(){
        currentVisibleView = US_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitUSHeightFeet?.visibility = View.VISIBLE
        binding?.tilMetricUSUnitHeightInch?.visibility = View.VISIBLE

        binding?.etUsMetricUnitHeightFeet?.text!!.clear()
        binding?.etUsMetricUnitHeightInch?.text!!.clear()
        binding?.etUSMetricUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi: Float){

        val bmiLabel : String
        val bmiDescription: String

        if(bmi.compareTo(15f)  <= 0){
            bmiLabel = "Very severly underweight"
            bmiDescription = "Ops! You really need to take better care of yourself! Eat more!"
        }else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severly underweight"
            bmiDescription = " Oops! You really need to take better care of yourself! Eat more!"
        }else if (bmi.compareTo(16f) >0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really neeed to take better care of yourself! Eat more"
        }else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! you are in a good shape!"
        }else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of yourself! Workout more!"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <=0){
            bmiLabel= "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of yourself! Workout more!"
        }else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0){
            bmiLabel = "Obese Class | (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }else{
            bmiLabel = "Obese Class | (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble())
            .setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIvalue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription

    }

    private fun validateMetricUnits(): Boolean{
        var isValid = true

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun calculateUnits(){
        if(currentVisibleView == METRIC_UNIT_VIEW){
            if(validateMetricUnits()){
                val heightValue : Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100

                val weightValue : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)
            }else{
                Toast.makeText(this@CalculateBmi,
                    "Please enter valid values", Toast.LENGTH_LONG).show()
            }
        }else{
            if(validateUsUnits()){
                val usUnitHeightValueFeet: String =
                    binding?.etUsMetricUnitHeightFeet?.text.toString()
                val usUnitHeightValueInch: String =
                    binding?.etUsMetricUnitHeightInch?.text.toString()
                val usUnitWeightValue: Float =
                    binding?.etUSMetricUnitWeight?.text.toString().toFloat()

                //Here the Height Feet and Inch Values are merged and multiplied by 12 for convertion
                val heightValue =
                    usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                displayBMIResult(bmi)

            }else
                Toast.makeText(this@CalculateBmi,
                "Please enter valid values.",
                Toast.LENGTH_LONG).show()
        }
    }

    private fun validateUsUnits(): Boolean{
        var isValid = true

        when{
            binding?.etUSMetricUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty() -> {
                isValid = false
            }
        }
        return isValid
    }

}