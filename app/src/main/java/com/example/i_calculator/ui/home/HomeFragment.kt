package com.example.i_calculator.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.example.i_calculator.BR
import com.example.i_calculator.R
import com.example.i_calculator.base.BaseFragment
import com.example.i_calculator.data.model.ButtonCalculator
import com.example.i_calculator.databinding.FragmentHomeBinding
import com.google.android.flexbox.*

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()

            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val viewModel: HomeViewModel by viewModels()
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_home

    private lateinit var buttonAdapter: ButtonAdapter
    private val pi = "3,14159265"

    override fun initView() {
        val listButton = mutableListOf<ButtonCalculator>()
        listButton.add(ButtonCalculator("C", ButtonAdapter.ButtonType.Advanced))
        listButton.add(ButtonCalculator("±", ButtonAdapter.ButtonType.Advanced))
        listButton.add(ButtonCalculator("%", ButtonAdapter.ButtonType.Advanced))
        listButton.add(ButtonCalculator("/", ButtonAdapter.ButtonType.Operator))

        listButton.add(ButtonCalculator("7", ButtonAdapter.ButtonType.Number))
        listButton.add(ButtonCalculator("8", ButtonAdapter.ButtonType.Number))
        listButton.add(ButtonCalculator("9", ButtonAdapter.ButtonType.Number))
        listButton.add(ButtonCalculator("x", ButtonAdapter.ButtonType.Operator))

        listButton.add(ButtonCalculator("4", ButtonAdapter.ButtonType.Number))
        listButton.add(ButtonCalculator("5", ButtonAdapter.ButtonType.Number))
        listButton.add(ButtonCalculator("6", ButtonAdapter.ButtonType.Number))
        listButton.add(ButtonCalculator("-", ButtonAdapter.ButtonType.Operator))

        listButton.add(ButtonCalculator("1", ButtonAdapter.ButtonType.Number))
        listButton.add(ButtonCalculator("2", ButtonAdapter.ButtonType.Number))
        listButton.add(ButtonCalculator("3", ButtonAdapter.ButtonType.Number))
        listButton.add(ButtonCalculator("+", ButtonAdapter.ButtonType.Operator))

        listButton.add(ButtonCalculator("0", ButtonAdapter.ButtonType.Number))
        listButton.add(ButtonCalculator("pi", ButtonAdapter.ButtonType.Number))
        listButton.add(ButtonCalculator(",", ButtonAdapter.ButtonType.Number))
        listButton.add(ButtonCalculator("=", ButtonAdapter.ButtonType.Operator))

        buttonAdapter = ButtonAdapter(listButton, object : ButtonAdapter.ButtonCallback {
            @SuppressLint("SetTextI18n")
            override fun onNumber(number: Int) {
                if (viewBinding.tvShow.text == "0") viewBinding.tvShow.text = ""
                viewBinding.tvShow.text = viewBinding.tvShow.text.toString().plus(number.toString())
            }

            override fun onPi() {
                viewBinding.tvShow.text = pi
            }

            override fun onFloat() {
                viewBinding.tvShow.text = viewBinding.tvShow.text.toString().plus(",")
            }

            override fun onOperation(value: String) {
                if (value == "=") {
                    if (viewBinding.tvFirstNumber.text.isNotBlank() && viewBinding.tvShow.text.isNotBlank()) {
                        val result: String?
                        if (viewBinding.tvFirstNumber.text.toString()
                                .contains(",") || viewBinding.tvShow.text.toString()
                                .contains(",")
                        ) {
                            result = calculateFloat(
                                viewBinding.tvFirstNumber.text.toString().replace(",", ".")
                                    .toFloat(),
                                viewBinding.tvShow.text.toString().replace(",", ".").toFloat(),
                                viewBinding.tvOperation.text.toString()
                            ).toString()
                        } else {
                            result = if (viewBinding.tvOperation.text.toString() == "/") divide(
                                viewBinding.tvFirstNumber.text.toString().toFloat(),
                                viewBinding.tvShow.text.toString().toFloat()
                            ).toString()
                            else calculate(
                                viewBinding.tvFirstNumber.text.toString().toInt(),
                                viewBinding.tvShow.text.toString().toInt(),
                                viewBinding.tvOperation.text.toString()
                            ).toString()
                        }
                        viewBinding.tvFirstNumber.text = ""
                        viewBinding.tvShow.text = result.replace(".", ",")
                        viewBinding.tvOperation.text = ""
                    }
                } else {
                    if (viewBinding.tvShow.text != "0") {
                        if (viewBinding.tvFirstNumber.text.isBlank()) {
                            viewBinding.tvFirstNumber.text = viewBinding.tvShow.text
                            viewBinding.tvShow.text = "0"
                            viewBinding.tvOperation.text = value
                        } else {
                            val result: String?
                            if (viewBinding.tvFirstNumber.text.toString()
                                    .contains(",") || viewBinding.tvShow.text.toString()
                                    .contains(",")
                            ) {
                                result = calculateFloat(
                                    viewBinding.tvFirstNumber.text.toString().replace(",", ".")
                                        .toFloat(),
                                    viewBinding.tvShow.text.toString().replace(",", ".").toFloat(),
                                    viewBinding.tvOperation.text.toString()
                                ).toString()
                            } else {
                                result = if (viewBinding.tvOperation.text.toString() == "/") divide(
                                    viewBinding.tvFirstNumber.text.toString().toFloat(),
                                    viewBinding.tvShow.text.toString().toFloat()
                                ).toString()
                                else calculate(
                                    viewBinding.tvFirstNumber.text.toString().toInt(),
                                    viewBinding.tvShow.text.toString().toInt(),
                                    viewBinding.tvOperation.text.toString()
                                ).toString()
                            }
                            viewBinding.tvFirstNumber.text = result.replace(".", ",")
                            viewBinding.tvShow.text = "0"
                            viewBinding.tvOperation.text = value
                        }
                    } else {
                        if (viewBinding.tvFirstNumber.text.isNotBlank()) {
                            viewBinding.tvOperation.text = value
                        }
                    }
                }
            }

            override fun onAdvanced(advanced: Advanced) {
                when (advanced) {
                    Advanced.INVERSE -> {
                        viewBinding.tvShow.text = if (viewBinding.tvShow.text.contains(",")) {
                            (-viewBinding.tvShow.text.toString().toFloat()).toString()
                        } else {
                            (-viewBinding.tvShow.text.toString().toInt()).toString()
                        }
                    }
                    Advanced.PERCENT -> {
                        if (viewBinding.tvFirstNumber.text.isNotBlank()) {
                            val result = calculateFloat(
                                viewBinding.tvFirstNumber.text.toString().replace(",", ".")
                                    .toFloat(),
                                viewBinding.tvFirstNumber.text.toString().replace(",", ".")
                                    .toFloat() *
                                        (viewBinding.tvShow.text.toString()
                                            .replace(",", ".")
                                            .toFloat() / 100),
                                viewBinding.tvOperation.text.toString()
                            ).toString()
                            viewBinding.tvFirstNumber.text = ""
                            viewBinding.tvShow.text = result.replace(".", ",")
                            viewBinding.tvOperation.text = ""
                        } else {
                            if (viewBinding.tvShow.text != "0") {
                                viewBinding.tvShow.text =
                                    (viewBinding.tvShow.text.toString().toFloat() / 100).toString()
                            }
                        }
                    }
                    Advanced.CLEAR -> {
                        viewBinding.tvShow.text = "0"
                        viewBinding.tvOperation.text = ""
                        viewBinding.tvFirstNumber.text = ""
                    }
                }
            }

        })
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.justifyContent = JustifyContent.SPACE_AROUND
        layoutManager.alignItems = AlignItems.STRETCH
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        viewBinding.rcvButton.layoutManager = layoutManager
        viewBinding.rcvButton.adapter = buttonAdapter
    }

    override fun setupObserver() {
    }

    private fun calculateFloat(firstNum: Float, secondNum: Float, operation: String): Float? {
        return when (operation) {
            "+" -> firstNum + secondNum
            "-" -> firstNum - secondNum
            "x" -> firstNum * secondNum
            "/" -> if (secondNum != 0F) (firstNum / secondNum) else null
            else -> null
        }
    }

    private fun calculate(firstNum: Int, secondNum: Int, operation: String): Int? {
        return when (operation) {
            "+" -> firstNum + secondNum
            "-" -> firstNum - secondNum
            "x" -> firstNum * secondNum
            else -> null
        }
    }

    private fun divide(firstNum: Float, secondNum: Float): Float? {
        return if (secondNum != 0F) (firstNum / secondNum) else null
    }
}