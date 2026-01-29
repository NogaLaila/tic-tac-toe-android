package com.example.tic_tac_toe_app

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tic_tac_toe_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    enum class Turn {
        NOUGHT,
        CROSS
    }
    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS
    private var crossesScore = 0
    private var noughtsCrosses = 0

    private var boardList = mutableListOf<Button>()


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initBoard()
        setTurnLabel()
    }

    private fun initBoard(){
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    private fun checkForVictory(noughtsOrCrosses: String): Boolean {
        if (match(binding.a1, noughtsOrCrosses) && match(binding.a2, noughtsOrCrosses) && match(binding.a3, noughtsOrCrosses))
            return true
        if (match(binding.b1, noughtsOrCrosses) && match(binding.b2, noughtsOrCrosses) && match(binding.b3, noughtsOrCrosses))
            return true
        if (match(binding.c1, noughtsOrCrosses) && match(binding.c2, noughtsOrCrosses) && match(binding.c3, noughtsOrCrosses))
            return true

        if (match(binding.a1, noughtsOrCrosses) && match(binding.b1, noughtsOrCrosses) && match(binding.c1, noughtsOrCrosses))
            return true
        if (match(binding.a2, noughtsOrCrosses) && match(binding.b2, noughtsOrCrosses) && match(binding.c2, noughtsOrCrosses))
            return true
        if (match(binding.a3, noughtsOrCrosses) && match(binding.b3, noughtsOrCrosses) && match(binding.c3, noughtsOrCrosses))
            return true

        if (match(binding.a1, noughtsOrCrosses) && match(binding.b2, noughtsOrCrosses) && match(binding.c3, noughtsOrCrosses))
            return true
        if (match(binding.a3, noughtsOrCrosses) && match(binding.b2, noughtsOrCrosses) && match(binding.c1, noughtsOrCrosses))
            return true

        return false
    }

    private fun match(button: Button, symbol: String): Boolean = button.text == symbol

    fun boardTapped(view: View){
        if(view !is Button)
            return
        addToBoard(view)

        if (checkForVictory(NOUGHT)){
            noughtsCrosses++

            result("Noughts Win!")
        }
        if (checkForVictory(CROSS)){
            crossesScore++
            result("Crosses Win!")
        }



        if (fullBoard()){
            result("Draw")
        }
    }

    private fun result(title: String) {
        val message = "\nNoughts $noughtsCrosses\n\nCrosses $crossesScore"
        AlertDialog.Builder(this).setTitle(title).setMessage(message).setPositiveButton("Reset"){
            _,_ ->
            resetBoard()

        }.setCancelable(false).show()
    }

    private fun resetBoard(){
        for (button in boardList){
            button.text = ""
        }

        if (firstTurn == Turn.NOUGHT)
            firstTurn = Turn.CROSS
        else if (firstTurn == Turn.CROSS)
            firstTurn = Turn.NOUGHT

        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun fullBoard(): Boolean {
        for (button in boardList){
            if (button.text == "")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button) {
        if (button.text != "")
            return
        if (currentTurn == Turn.NOUGHT) {
            button.text = NOUGHT
            currentTurn = Turn.CROSS
        } else if (currentTurn == Turn.CROSS) {
            button.text = CROSS
            currentTurn = Turn.NOUGHT
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        var turnText = ""
        if (currentTurn == Turn.CROSS)
            turnText= "Turn $CROSS"
        else if (currentTurn == Turn.NOUGHT)
            turnText = "Turn $NOUGHT"

        binding.turnTV.text = turnText
    }

    companion object {
        const val NOUGHT = "O"
        const val CROSS = "X"
    }
}


