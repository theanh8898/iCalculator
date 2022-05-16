package com.example.i_calculator.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.i_calculator.R
import com.example.i_calculator.data.model.ButtonCalculator
import com.example.i_calculator.databinding.ItemButtonCalculatorBinding

class ButtonAdapter(
    private var data: MutableList<ButtonCalculator>,
    private val callback: ButtonCallback
) :
    RecyclerView.Adapter<ButtonAdapter.ViewHolder>() {
    interface ButtonCallback {
        fun onNumber(number: Int)
        fun onPi()
        fun onFloat()
        fun onOperation(value: String)
        fun onAdvanced(advanced: Advanced)
    }

    inner class ViewHolder(val binding: ItemButtonCalculatorBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemButtonCalculatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    private var selectedOperation = -1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            button.text = data[position].value
            when (data[position].type) {
                ButtonType.Number -> {
                    button.setBackgroundResource(R.drawable.shape_number)
                    button.setOnClickListener {
                        when (data[position].value) {
                            "pi" -> callback.onPi()
                            "," -> callback.onFloat()
                            else -> callback.onNumber(data[position].value.toInt())
                        }
                    }
                }
                ButtonType.Operator -> {
                    button.setBackgroundResource(R.drawable.shape_operator)
                    button.setOnClickListener {
                        callback.onOperation(
                            data[position].value
                        )
                    }
                }
                ButtonType.Advanced -> {
                    button.setOnClickListener {
                        when (data[position].value) {
                            "%" -> callback.onAdvanced(Advanced.PERCENT)
                            "±" -> callback.onAdvanced(Advanced.INVERSE)
                            else -> callback.onAdvanced(Advanced.CLEAR)
                        }
                    }
                    button.setBackgroundResource(R.drawable.shape_advanced)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    enum class ButtonType { Number, Operator, Advanced }
}