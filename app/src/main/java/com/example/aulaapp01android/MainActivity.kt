package com.example.aulaapp01android

import android.graphics.Color
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    val numbers = mutableListOf<List<Int>>()
    private var currentQuestionIndex = 0;
    private lateinit var firstNumber: TextView;
    private lateinit var secondNumber: TextView;
    private lateinit var answer: EditText;
    private lateinit var notaValue: TextView;
    private lateinit var isCorrectText: TextView;
    private lateinit var plusSign: TextView;
    private lateinit var score: TextView;
    private lateinit var questionIndex: TextView;
    private var nota: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val root = findViewById<ConstraintLayout>(R.id.main)

        firstNumber = findViewById(R.id.firstValue);
        secondNumber = findViewById(R.id.secondValue);
        answer = findViewById(R.id.answer);
        notaValue = findViewById(R.id.notaValue);
        isCorrectText = findViewById(R.id.isCorrectText);
        plusSign = findViewById(R.id.plusSign);
        score = findViewById(R.id.score);
        questionIndex = findViewById(R.id.questionIndex);
        val nextButton = findViewById<Button>(R.id.nextButton);

        generateNextNumbers()

        val confirmButton = findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener(fun (_) {
            val answerValue = answer.text.toString().toIntOrNull()
            if (answerValue == null) {
                //do something
                Toast.makeText(this, "Digite uma resposta", Toast.LENGTH_SHORT).show()
                return
            }
            if (answerValue == (numbers.get(currentQuestionIndex).get(0) + numbers.get(currentQuestionIndex).get(1))) {
                root.setBackgroundColor(0xff59ff96.toInt())
                isCorrectText.text = "Resposta Correta!"
                nota += 20
            } else {
                isCorrectText.text = "Resposta Errada!"
                    root.setBackgroundColor(0xffff6a59.toInt());
            }
            firstNumber.visibility = INVISIBLE;
            secondNumber.visibility = INVISIBLE;
            answer.visibility = INVISIBLE;
            plusSign.visibility = INVISIBLE;
            isCorrectText.visibility = VISIBLE
            nextButton.visibility = VISIBLE;
            answer.setText("")
        });

        nextButton.setOnClickListener(fun (_) {
            root.setBackgroundColor(Color.WHITE);
            isCorrectText.visibility = INVISIBLE
            firstNumber.visibility = VISIBLE;
            secondNumber.visibility = VISIBLE;
            answer.visibility = VISIBLE;
            plusSign.visibility = VISIBLE;
            nextButton.visibility = INVISIBLE
            currentQuestionIndex++
            if (currentQuestionIndex == 5) {
                nextButton.visibility = INVISIBLE
                score.visibility = VISIBLE;
                confirmButton.visibility = INVISIBLE
                secondNumber.visibility = INVISIBLE
                firstNumber.visibility = INVISIBLE
                plusSign.visibility = INVISIBLE
                answer.visibility = INVISIBLE
                notaValue.text = nota.toString()
                notaValue.visibility = VISIBLE
                questionIndex.visibility = INVISIBLE
            }
            generateNextNumbers();
        });

        val restartButton = findViewById<Button>(R.id.restart)
        restartButton.setOnClickListener(fun (_) {
            numbers.clear();
            currentQuestionIndex = 0;
            questionIndex.visibility = VISIBLE
            generateNextNumbers();
            isCorrectText.visibility = INVISIBLE
            firstNumber.visibility = VISIBLE;
            secondNumber.visibility = VISIBLE;
            answer.visibility = VISIBLE;
            plusSign.visibility = VISIBLE;
            confirmButton.visibility = VISIBLE
            nextButton.visibility = INVISIBLE
            notaValue.visibility = INVISIBLE
            score.visibility = INVISIBLE
            nota = 0;
        })
    }

    private fun generateNextNumbers() {
        val randomNumber1 = (0..99).random()
        val randomNumber2 = (0..99).random()
        numbers.add(listOf(randomNumber1, randomNumber2));
        firstNumber.text = numbers.get(currentQuestionIndex).get(0).toString();
        secondNumber.text = numbers.get(currentQuestionIndex).get(1).toString();
        questionIndex.text = "Pergunta ${currentQuestionIndex + 1}/5"
    }
}