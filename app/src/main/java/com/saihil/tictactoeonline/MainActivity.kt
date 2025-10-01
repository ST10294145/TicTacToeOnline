package com.saihil.tictactoeonline

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var board: Array<Array<Button>>
    private var currentPlayer = "X"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the 3x3 board
        board = Array(3) { row ->
            Array(3) { col ->
                val buttonId = resources.getIdentifier("button$row$col", "id", packageName)
                findViewById<Button>(buttonId).apply {
                    setOnClickListener { onCellClicked(this, row, col) }
                }
            }
        }

        // Reset button
        val resetButton = findViewById<Button>(R.id.buttonReset)
        resetButton.setOnClickListener { resetBoard() }
    }

    private fun onCellClicked(button: Button, row: Int, col: Int) {
        if (button.text.isNotEmpty()) return

        button.text = currentPlayer

        if (checkWinner()) {
            Toast.makeText(this, "Player $currentPlayer wins!", Toast.LENGTH_SHORT).show()
            disableBoard()
        } else if (isBoardFull()) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show()
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
        }
    }

    private fun checkWinner(): Boolean {
        // Check rows and columns
        for (i in 0..2) {
            if (board[i][0].text == currentPlayer &&
                board[i][1].text == currentPlayer &&
                board[i][2].text == currentPlayer
            ) return true

            if (board[0][i].text == currentPlayer &&
                board[1][i].text == currentPlayer &&
                board[2][i].text == currentPlayer
            ) return true
        }

        // Check diagonals
        if (board[0][0].text == currentPlayer &&
            board[1][1].text == currentPlayer &&
            board[2][2].text == currentPlayer
        ) return true

        if (board[0][2].text == currentPlayer &&
            board[1][1].text == currentPlayer &&
            board[2][0].text == currentPlayer
        ) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell.text.isEmpty()) return false
            }
        }
        return true
    }

    private fun disableBoard() {
        for (row in board) {
            for (cell in row) {
                cell.isEnabled = false
            }
        }
    }

    private fun resetBoard() {
        for (row in board) {
            for (cell in row) {
                cell.text = ""
                cell.isEnabled = true
            }
        }
        currentPlayer = "X"
    }
}
