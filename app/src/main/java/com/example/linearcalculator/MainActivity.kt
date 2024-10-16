package com.example.linearcalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var textResult: TextView
    var currentOperator: String? = null
    var operand1: Double = 0.0
    var operand2: Double = 0.0
    var isOperatorPressed: Boolean = false
    var isError: Boolean = false

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
        if (!isError) {
            operand1 = textResult.text.toString().toDouble()
            currentOperator = operator
            isOperatorPressed = true
        }
    }

    private fun onEqualPressed() {
        if (!isError && currentOperator != null) {
            operand2 = textResult.text.toString().toDouble()

            val result = when (currentOperator) {
                "+" -> operand1 + operand2
                "-" -> operand1 - operand2
                "*" -> operand1 * operand2
                "/" -> if (operand2 != 0.0) {
                    operand1 / operand2
                } else {
                    textResult.text = "Error"
                    isError = true
                    return
                }
                else -> 0.0
            }
            textResult.text = result.toString()
            resetAfterCalculation()
        }
    }

    private fun clearAll() {
        textResult.text = "0"
        operand1 = 0.0
        operand2 = 0.0
        currentOperator = null
        isOperatorPressed = false
        isError = false
    }

    private fun resetAfterCalculation() {
        operand1 = textResult.text.toString().toDouble()
        operand2 = 0.0
        currentOperator = null
        isOperatorPressed = false
    }
}
