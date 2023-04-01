package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {

    var lastNumeric : Boolean = false
    var lastDot : Boolean = false // abhi dot nhi he or sirf ek hi dot ho skta h


    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }

    // Instead of using OnClickListner
    // Another method of using button
   fun onDigit(view : View){
   binding.tvInput.append((view as Button).text)
   // whenever user press valid numeric key
   lastNumeric = true

   }

    fun onClear(view : View){
        binding.tvInput.text = ""
        lastNumeric = false
        lastDot = false   // niether numeric nor dot

    }

    fun OnDecimalPoint(view : View){
     // if the value before was actually a number and was not a dot before that
        if(lastNumeric && !lastDot){
          binding.tvInput.append(".")
            lastNumeric = false
            lastDot = true     // not numeric its dot
        }
    }

    fun OnEqual(view : View){
        // 7* 8/ 9-  if these were the case and u press equal button nothing will happen
        // if the last digit is numeric than only it will show the output
        if(lastNumeric){
           var tvValue = binding.tvInput.text.toString()
            var prefix = ""
            try {

                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                    // -99-1 --> 99-1
                    // -251 --> it will return from the first index i.e only 251
                }

                if(tvValue.contains("-")){
                    //perform subtraction "99-1"
                    val splitValue = tvValue.split("-")
                    // [99 , 1]
                    var one = splitValue[0] // 99
                    var two = splitValue[1] // 1
                    // -33 - 5
                    // we remove the "-" because it is creating problem in splitting but we are adding it again
                    // so that our calcualtion will not affect
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    var result = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    binding.tvInput.text = result

                }else if(tvValue.contains("/")){
                        val splitValue = tvValue.split("/")
                        var one = splitValue[0]
                        var two = splitValue[1]

                        if(!prefix.isEmpty()){
                            one = prefix + one
                        }

                        var result = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                        binding.tvInput.text = result

                    }else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    var result = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    binding.tvInput.text = result

                }else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    var result = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    binding.tvInput.text = result

                }
            }catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
        var newTvValue = binding.tvInput.text.toString()
        lastDot = newTvValue.contains(".")
    }

    fun onBacKSpaceButton(view: View) {

        var tvValue = binding.tvInput.text.toString()
        val len = tvValue.length
        if(len > 0){
            tvValue = tvValue.substring( 0 , len - 1)
            binding.tvInput.text = tvValue
        }
        lastNumeric = true
        lastDot = false

        if(tvValue.contains(".")){
            lastDot = true
        }
        if(tvValue.endsWith(".")){
            lastNumeric = false
        }
    }
   private fun removeZeroAfterDot(result: String):String{
       var value = result
       if(result.contains(".0"))
           value = result.substring(0,result.length - 2)
           return value
           // 99.0
           // 0123
           // length = 4 - 2 = 2
           // so 0 and 1 exclude 2
   }

    fun onOperator(view : View){
        if(lastNumeric && !isOperatorAdded(binding.tvInput.text.toString())){
            binding.tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false    // its operator niether numeric nor dot
        }
    }


    private fun isOperatorAdded(value : String):Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("*") ||
                    value.contains("-") || value.contains("+")
        }
    }
}