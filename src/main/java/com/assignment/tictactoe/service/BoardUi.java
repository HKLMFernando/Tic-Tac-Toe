package com.assignment.tictactoe.service;

public interface BoardUi {
    void update(int row, int col, Piece piece);
    void NotifyWinner(Piece winner);
}
