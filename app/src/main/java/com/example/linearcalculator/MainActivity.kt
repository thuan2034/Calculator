package com.example.linearcalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var textResult: TextView
    private var currentOperator: String? = null
    private var operand1: Double = 0.0
    private var isOperatorPressed: Boolean = false
    private var isError: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.text_result)

        // Set up buttons and their listeners
        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        // Digit Buttons
        val digitButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9
        )
        digitButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener(this)
        }

        // Operator Buttons
        val operatorButtons = listOf(
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide
        )
        operatorButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener(this)
        }

        // Other Buttons
        findViewById<Button>(R.id.btnEqual).setOnClickListener(this)
        findViewById<Button>(R.id.btnClear).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val id = view.id

        // Handle digit buttons
        if (id in listOf(
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9
            )
        ) {
            onDigitPressed((view as Button).text.toString())
        }

        // Handle operator buttons
        when (id) {
            R.id.btnAdd -> onOperatorPressed("+")
            R.id.btnSubtract -> onOperatorPressed("-")
            R.id.btnMultiply -> onOperatorPressed("*")
            R.id.btnDivide -> onOperatorPressed("/")
        }

        // Handle equals button
        if (id == R.id.btnEqual) {
            onEqualPressed()
        }

        // Handle clear button
        if (id == R.id.btnClear) {
            clearAll()
        }
    }

    private fun onDigitPressed(digit: String) {
        if (isError) {
            clearAll()  // If there was an error, reset before proceeding
        }
        if (isOperatorPressed) {
            textResult.text = digit
            isOperatorPressed = false
        } else {
            textResult.text = if (textResult.text == "0") {
                digit
            } else {
                textResult.text.toString() + digit
            }
        }
    }

    private fun onOperatorPressed(operator: String) {
        if (isError) return

        if (currentOperator != null) {
            // If an operator is already set, perform the calculation first
            val currentValue = textResult.text.toString().toDouble()
            operand1 = calculate(operand1, currentValue, currentOperator!!)
            textResult.text = operand1.toString()
        } else {
            // If no operator has been set, set the first operand
            operand1 = textResult.text.toString().toDouble()
        }

        currentOperator = operator
        isOperatorPressed = true // Set operator pressed flag
    }

    private fun onEqualPressed() {
        if (isError) return

        val currentValue = textResult.text.toString().toDouble()
        if (currentOperator != null) {
            operand1 = calculate(operand1, currentValue, currentOperator!!)
            textResult.text = operand1.toString()
            currentOperator = null // Reset after calculation
        }
    }

    private fun calculate(operand1: Double, operand2: Double, operator: String): Double {
        return when (operator) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> if (operand2 != 0.0) {
                operand1 / operand2
            } else {
                textResult.text = "Error"
                isError = true
                return 0.0
            }
            else -> operand1
        }
    }

    private fun clearAll() {
        textResult.text = "0"
        operand1 = 0.0
        currentOperator = null
        isOperatorPressed = false
        isError = false
    }
}
