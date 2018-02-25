import java.util.ArrayList;

/**
 * A simple OthelloAI-implementation. The method to decide the next move just
 * returns the first legal move that it finds.
 *
 * @author Group 24
 * @version 25.2.2018
 */
public class SmartAI implements IOthelloAI {

    /**
     * Returns first legal move
     */
    public Position decideMove(GameState s) {
        ArrayList<Position> moves = s.legalMoves();
        int indexOfBestMove = -1;
        int bestScore = Integer.MIN_VALUE;
        if (!moves.isEmpty()) {
            for (Position p : moves) {
                GameState modifiedGameState = new GameState(s.getBoard(), 1);
                modifiedGameState.insertToken(p);
                int score = maxValue(modifiedGameState);
                if (score >= bestScore) {
                    bestScore = score;
                    indexOfBestMove = moves.indexOf(p);
                }
            }
            return moves.get(indexOfBestMove);
        }
        else
            return new Position(-1, -1);
    }

    private int maxValue(GameState s) {
        //calls finished function and returns 1 or -1 based on how many tokens player 1 has
        int bestScore = Integer.MIN_VALUE;
        if (s.isFinished()) {
            System.out.println("best score" + bestScore);
            return finished(s);
        }
        ArrayList<Position> moves = s.legalMoves();
        for (Position p : moves) {
            //modify gamestate for calculating the minimum score for opponent
            GameState modifiedGameState = new GameState(s.getBoard(), 1);
            modifiedGameState.insertToken(p);
            int score = minValue(modifiedGameState);
            if (score >= bestScore) {
                bestScore = score;
            }
        }
        return bestScore;
    }

    private int minValue(GameState s) {
        //calls finished function and returns 1 or -1 based on how many tokens player 1 has
        int worstScore = Integer.MAX_VALUE;
        if (s.isFinished()) return finished(s);
        ArrayList<Position> moves = s.legalMoves();
        for (Position p : moves) {
            //modify gamestate for calculating the minimum score for opponent
            GameState modifiedGameState = new GameState(s.getBoard(), 2);
            modifiedGameState.insertToken(p);
            int score = maxValue(modifiedGameState);
            if (score <= worstScore) {
                worstScore = score;
            }
        }
        System.out.println("worst score" + worstScore);
        return worstScore;
    }

    private int finished(GameState s) {
        System.out.println("gets called");
        int[] tokens = s.countTokens();
        if (tokens[0] > tokens[1]) return 1;
        else return -1;
    }

}
