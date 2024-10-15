package com.example.test1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var textResult: TextView

    var state: Int = 1
    var currentOperator: Char = ' '
    var op1: Double = 0.0
    var op2: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.text_result)

        // Setting click listeners for digit buttons
        findViewById<Button>(R.id.btn0).setOnClickListener(this)
        findViewById<Button>(R.id.btn1).setOnClickListener(this)
        findViewById<Button>(R.id.btn2).setOnClickListener(this)
        findViewById<Button>(R.id.btn3).setOnClickListener(this)
        findViewById<Button>(R.id.btnAdd).setOnClickListener(this)
        findViewById<Button>(R.id.btnSubtract).setOnClickListener(this)
        findViewById<Button>(R.id.btnMultiply).setOnClickListener(this)
        findViewById<Button>(R.id.btnDivide).setOnClickListener(this)
        findViewById<Button>(R.id.btnEqual).setOnClickListener(this)
        findViewById<Button>(R.id.btnClear).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val id = view?.id
        when (id) {
            R.id.btn0 -> addDigit(0)
            R.id.btn1 -> addDigit(1)
            R.id.btn2 -> addDigit(2)
            R.id.btn3 -> addDigit(3)

            R.id.btnAdd -> selectOperator('+')
            R.id.btnSubtract -> selectOperator('-')
            R.id.btnMultiply -> selectOperator('*')
            R.id.btnDivide -> selectOperator('/')

            R.id.btnEqual -> calculateResult()
            R.id.btnClear -> clearAll()
        }
    }

    fun addDigit(digit: Int) {
        if (state == 1) {
            op1 = op1 * 10 + digit
            textResult.text = "$op1"
        } else {
            op2 = op2 * 10 + digit
            textResult.text = "$op2"
        }
    }

    fun selectOperator(operator: Char) {
        currentOperator = operator
        state = 2 // Move to second operand input
    }

    fun calculateResult() {
        val result: Double = when (currentOperator) {
            '+' -> op1 + op2
            '-' -> op1 - op2
            '*' -> op1 * op2
            '/' -> {
                if (op2 == 0.0) {
                    textResult.text = "Error"
                    return
                } else {
                    op1 / op2
                }
            }
            else -> 0.0
        }
        textResult.text = result.toString()

        // Reset the state after calculation
        state = 1
        op1 = result
        op2 = 0.0
    }

    fun clearAll() {
        op1 = 0.0
        op2 = 0.0
        currentOperator = ' '
        state = 1
        textResult.text = "0"
    }
}
