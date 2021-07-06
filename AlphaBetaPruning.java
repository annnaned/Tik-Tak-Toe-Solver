public class AlphaBetaPruning implements Algorithm {

    private static double maxDepth;
    private boolean optimal;

    public AlphaBetaPruning(double maxDepth) {
        AlphaBetaPruning.maxDepth = maxDepth;
        optimal = false;
    }

    public AlphaBetaPruning(double maxDepth, boolean optimal) {
        AlphaBetaPruning.maxDepth = maxDepth;
        this.optimal = optimal;
    }

    @Override
    public void run(Board.State player, Board board) {
        alphaBetaPruning(player, board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
    }

    public int alphaBetaPruning(Board.State player, Board board, double alpha, double beta, int currentDepth) {
        if (currentDepth++ == maxDepth || board.isGameOver()) {
            return optimal ? score(player, board, currentDepth) : score(player, board);
        }

        if (board.getTurn() == player) {
            return getMax(player, board, alpha, beta, currentDepth);
        } else {
            return getMin(player, board, alpha, beta, currentDepth);
        }
    }

    private int getMin(Board.State player, Board board, double alpha, double beta, int currentDepth) {
        // TODO Auto-generated method stub
        int indexOfBestMove = -1;

        for (Integer move : board.getAvailableMoves()) {
            Board tmp = board.getDeepCopy();
            tmp.move(move);

            int score = alphaBetaPruning(player, tmp, alpha, beta, currentDepth);
            if (score < beta) {
                beta = score;
                indexOfBestMove = move;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }

        return (int) beta;
    }

    private int getMax(Board.State player, Board board, double alpha, double beta, int currentDepth) {
        // TODO Auto-generated method stub
        int indexOfBestMove = -1;

        for (Integer move : board.getAvailableMoves()) {

            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(move);
            int score = alphaBetaPruning(player, modifiedBoard, alpha, beta, currentDepth);

            if (score > alpha) {
                alpha = score;
                indexOfBestMove = move;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }

        return (int) alpha;
    }

    @Override
    public int score(Board.State player, Board board) {
        // TODO Auto-generated method stub
        if (player == Board.State.BLANK) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        Board.State opponent = (player == Board.State.X) ? Board.State.O : Board.State.X;

        if (board.isGameOver() && board.getWinner() == player) {
            return WINNER_VALUE;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return (-1) * WINNER_VALUE;
        } else {
            return DRAW_VALUE;
        }
    }

    public int score(Board.State player, Board board, int currentDepth) {
        // TODO Auto-generated method stub
        if (player == Board.State.BLANK) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        Board.State opponent = (player == Board.State.X) ? Board.State.O : Board.State.X;

        if (board.isGameOver() && board.getWinner() == player) {
            return WINNER_VALUE - currentDepth;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return (-1) * WINNER_VALUE + currentDepth;
        } else {
            return DRAW_VALUE;
        }
    }
}

