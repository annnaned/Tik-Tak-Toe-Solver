import java.util.HashSet;
import java.util.Set;

public class Board {

    private static final int BOARD_WIDTH = 3;

    private State[][] board;
    private State playersTurn;
    private State winner;
    private Set<Integer> movesAvailable;
    private int moveCount;
    private boolean gameOver;

    public Board() {
        board = new State[BOARD_WIDTH][BOARD_WIDTH];
        movesAvailable = new HashSet<>();
        initializeStartState();
    }

    public void initializeStartState () {
        moveCount = 0;
        gameOver = false;
        playersTurn = State.X;
        winner = State.BLANK;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = State.BLANK;
            }
        }

        movesAvailable.clear();

        for (int i = 0; i < BOARD_WIDTH*BOARD_WIDTH; i++) {
            movesAvailable.add(i);
        }
    }

    public boolean move(int index) {
        int x = index % BOARD_WIDTH;
        int y = index / BOARD_WIDTH;

        if (board[y][x] == State.BLANK) {
            board[y][x] = playersTurn;
        } else {
            return false;
        }

        ++moveCount;
        movesAvailable.remove(y * BOARD_WIDTH + x);

        if (moveCount == BOARD_WIDTH * BOARD_WIDTH) {
            winner = State.BLANK;
            gameOver = true;
        }

        validateOutcome(y, x);
        playersTurn = (playersTurn == State.X) ? State.O : State.X;
        return true;
    }

    public boolean isGameOver () {
        return gameOver;
    }

    public State getTurn () {
        return playersTurn;
    }

    public State getWinner () {
        if (!gameOver) {
            throw new IllegalStateException("TicTacToe is not over yet.");
        }
        return winner;
    }

    public Set<Integer> getAvailableMoves () {
        return movesAvailable;
    }

    public State[][] toArray () {
        return board.clone();
    }

    public Board getDeepCopy () {
        Board newObj = new Board();

        for (int i = 0; i < newObj.board.length; i++) {
            newObj.board[i] = this.board[i].clone();
        }

        newObj.playersTurn = this.playersTurn;
        newObj.winner = this.winner;
        newObj.movesAvailable = new HashSet<>();
        newObj.movesAvailable.addAll(this.movesAvailable);
        newObj.moveCount = this.moveCount;
        newObj.gameOver = this.gameOver;
        return newObj;
    }

    private void validateOutcome(int row, int col) {
        checkRow(row);
        checkColumn(col);
        checkDiagonalFromTopLeft(row, col);
        checkDiagonalFromTopRight(row, col);
    }

    private void checkRow (int row) {
        for (int i = 1; i < BOARD_WIDTH && !gameOver; i++) {
            if (board[row][i] != board[row][i - 1]) {
                break;
            }
            if (i == BOARD_WIDTH - 1) {
                winner = playersTurn;
                gameOver = true;
            }
        }
    }

    private void checkColumn (int col) {
        for (int i = 1; i < BOARD_WIDTH && !gameOver; i++) {
            if (board[i][col] != board[i - 1][col]) {
                break;
            }
            if (i == BOARD_WIDTH - 1) {
                winner = playersTurn;
                gameOver = true;
            }
        }
    }

    private void checkDiagonalFromTopLeft (int x, int y) {
        if (x == y && !gameOver) {
            for (int i = 1; i < BOARD_WIDTH; i++) {
                if (board[i][i] != board[i - 1][i - 1]) {
                    break;
                }
                if (i == BOARD_WIDTH - 1) {
                    winner = playersTurn;
                    gameOver = true;
                }
            }
        }
    }

    private void checkDiagonalFromTopRight (int x, int y) {
        if (BOARD_WIDTH - 1 - x == y && !gameOver) {
            for (int i = 1; i < BOARD_WIDTH; i++) {
                if (board[BOARD_WIDTH -1-i][i] != board[BOARD_WIDTH - i][i - 1]) {
                    break;
                }
                if (i == BOARD_WIDTH - 1) {
                    winner = playersTurn;
                    gameOver = true;
                }
            }
        }
    }

    public enum State {
        BLANK,
        X,
        O
    }


}
