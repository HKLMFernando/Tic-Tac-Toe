package com.assignment.tictactoe.controller;

import com.assignment.tictactoe.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.layout.GridPane;

public class BoardController implements BoardUi {
    BoardImpl board;
    AiPlayer ai;
    HumanPlayer human;

    public BoardController() {
        board = new BoardImpl();
        ai = new AiPlayer(board);
        human = new HumanPlayer(board);
    }

    @FXML
    private Button bid1;

    @FXML
    private Button bid2;

    @FXML
    private Button bid3;

    @FXML
    private Button bid4;

    @FXML
    private Button bid5;

    @FXML
    private Button bid6;

    @FXML
    private Button bid7;

    @FXML
    private Button bid8;

    @FXML
    private Button bid9;

    @FXML
    private GridPane gameGride;

    @FXML
    void buttonClicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonId = button.getId();

        // Ensure the button ID starts with "bid" and has a single digit after
        if (buttonId.startsWith("bid") && buttonId.length() == 4) {
            int index = Integer.parseInt(buttonId.substring(3)); // Get the digit at index 3

            // Calculate row and column from the index
            int row = (index - 1) / 3; // Assuming a 3x3 grid
            int col = (index - 1) % 3;

            // Handle the move for human player (X)
            human.move(row, col); // Pass Piece.X explicitly
            ai.findBestMove(); // AI makes its move
            board.printBoard();
            resetBoard();

            // Check for winner or draw
            if (board.checkWinner() != null) {
                NotifyWinner(board.checkWinner().getWinningPiece());
            } else if (board.isBoardFull()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Game Drawn");
                alert.setOnCloseRequest((DialogEvent dEvent) -> {
                    board.initializeBoard();
                    resetBoard();
                });
                alert.showAndWait();
            }
        }
    }

    public void resetBoard() {
        for (int i = 0; i < board.getPieces().length; i++) {
            for (int j = 0; j < board.getPieces()[i].length; j++) {
                update(i, j, board.getPieces()[i][j]);
            }
        }
    }

    @Override
    public void update(int row, int col, Piece piece) {
        String buttonId = "bid" + (row ) + (col ); // Update to use 1-based indexing
        Button button = getButtonById(buttonId);
        if (button != null) {
            if (piece == Piece.X) {
                button.setText("X");
            } else if (piece == Piece.O) {
                button.setText("O");
            } else {
                button.setText("");
            }
        }
    }

    private Button getButtonById(String buttonId) {
        switch (buttonId) {
            case "bid00": return bid1;
            case "bid01": return bid2;
            case "bid02": return bid3;
            case "bid10": return bid4;
            case "bid11": return bid5;
            case "bid12": return bid6;
            case "bid20": return bid7;
            case "bid21": return bid8;
            case "bid22": return bid9;
            default: return null;
        }
    }

    @Override
    public void NotifyWinner(Piece winner) {
        if (winner == Piece.X) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Winner is X");
            alert.setOnCloseRequest((DialogEvent event) -> {
                board.initializeBoard();
                resetBoard();
            });
            alert.showAndWait();
        } else if (winner == Piece.O) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Winner is O");
            alert.setOnCloseRequest((DialogEvent event) -> {
                board.initializeBoard();
                resetBoard();
            });
            alert.showAndWait();
        }
    }
}
