package com.saihil.tictactoeonline

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var boardButtons: Array<Button>
    private var board = Array(9) { "" }
    private var currentPlayer = "X"
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // XML layout

        // Initialize buttons array
        boardButtons = Array(9) { i ->
            val id = resources.getIdentifier("button$i", "id", packageName)
            findViewById<Button>(id)
        }

        // Set click listeners for each button
        boardButtons.forEachIndexed { i, button ->
            button.setOnClickListener { playTurn(i) }
        }

        // Reset button listener
        findViewById<Button>(R.id.resetButton).setOnClickListener {
            resetGame()
        }
    }

    private fun playTurn(index: Int) {
        if (!gameActive || board[index].isNotEmpty()) return

        board[index] = currentPlayer
        boardButtons[index].text = currentPlayer

        if (checkWin()) {
            Toast.makeText(this, "Player $currentPlayer wins!", Toast.LENGTH_LONG).show()
            gameActive = false
            disableBoard()
            return
        }

        if (board.all { it.isNotEmpty() }) {
            Toast.makeText(this, "It's a Draw!", Toast.LENGTH_LONG).show()
            gameActive = false
            return
        }

        // Switch player
        currentPlayer = if (currentPlayer == "X") "O" else "X"
        Toast.makeText(this, "Player $currentPlayer's turn", Toast.LENGTH_SHORT).show()
    }

    private fun checkWin(): Boolean {
        val winningPositions = arrayOf(
            intArrayOf(0,1,2), intArrayOf(3,4,5), intArrayOf(6,7,8),
            intArrayOf(0,3,6), intArrayOf(1,4,7), intArrayOf(2,5,8),
            intArrayOf(0,4,8), intArrayOf(2,4,6)
        )
        return winningPositions.any { (a,b,c) ->
            board[a].isNotEmpty() && board[a] == board[b] && board[b] == board[c]
        }
    }

    private fun resetGame() {
        board.fill("")
        boardButtons.forEach {
            it.text = ""
            it.isEnabled = true
        }
        currentPlayer = "X"
        gameActive = true
        Toast.makeText(this, "Game Reset. Player X starts!", Toast.LENGTH_SHORT).show()
    }

    private fun disableBoard() {
        boardButtons.forEach { it.isEnabled = false }
    }
}
