public class MinMax implements Algorithm {

    private static double maxDepth;

    public MinMax(double maxDepth) {
        MinMax.maxDepth = maxDepth;
    }

    @Override
    public void run(Board.State player, Board board) {
        // TODO Auto-generated method stub
        miniMax(player, board, 0);
    }

    private int miniMax(Board.State player, Board board, int currentDepth) {
        if (currentDepth++ == maxDepth || board.isGameOver()) {
            return score(player, board);
        }

        if (board.getTurn() == player) {
            return getMax(player, board, currentDepth);
        } else {
            return getMin(player, board, currentDepth);
        }
    }

    private int getMin(Board.State player, Board board, int currentDepth) {
        double bestScore = Double.POSITIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {

            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(theMove);

            int score = miniMax(player, modifiedBoard, currentDepth);

            if (score <= bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }

        }

        board.move(indexOfBestMove);
        return (int)bestScore;
    }

    private int getMax(Board.State player, Board board, int currentDepth) {
        double bestScore = Double.NEGATIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {

            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(theMove);

            int score = miniMax(player, modifiedBoard, currentDepth);

            if (score >= bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }

        }

        board.move(indexOfBestMove);
        return (int)bestScore;
    }

    @Override
    public int score(Board.State player, Board board) {
        if (player == Board.State.BLANK) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        Board.State opponent = (player == Board.State.X) ? Board.State.O : Board.State.X;

        if (board.isGameOver() && board.getWinner() == player) {
            return WINNER_VALUE;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return (-1)* WINNER_VALUE;
        } else {
            return DRAW_VALUE;
        }
    }
}