package game2048logic;

import game2048rendering.Board;
import game2048rendering.Side;
import game2048rendering.Tile;

import java.util.Formatter;


/** The state of a game of 2048.
 *  @author P. N. Hilfinger + Josh Hug
 */
public class Model {
    /** Current contents of the board. */
    private final Board board;
    /** Current score. */
    private int score;

    /* Coordinate System: column x, row y of the board (where x = 0,
     * y = 0 is the lower-left corner of the board) will correspond
     * to board.tile(x, y).  Be careful!
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = 0;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (x, y) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score) {
        board = new Board(rawValues);
        this.score = score;
    }

    /** Return the current Tile at (x, y), where 0 <= x < size(),
     *  0 <= y < size(). Returns null if there is no tile there.
     *  Used for testing. */
    public Tile tile(int x, int y) {
        return board.tile(x, y);
    }

    /** Return the number of squares on one side of the board. */
    public int size() {
        return board.size();
    }

    /** Return the current score. */
    public int score() {
        return score;
    }


    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        board.clear();
    }


    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        return maxTileExists() || !atLeastOneMoveExists();
    }

    /** Returns this Model's board. */
    public Board getBoard() {
        return board;
    }

    /** Returns true if at least one space on the board is empty.
     *  Empty spaces are stored as null.
     * */
    public boolean emptySpaceExists() {
        if (board.size() <= 1) {
            return false;
        }
        for (int i = 0; i <= board.size() - 1; i++) {
            for (int j = 0; j <= board.size() - 1; j++) {
                if (board.tile(i, j) == null) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public boolean maxTileExists() {
        if (board.size() <= 1) {
            return false;
        }
        for (int i = 0; i <= board.size() - 1; i++) {
            for (int j = 0; j <= board.size() - 1; j++) {
                Tile t = board.tile(i, j);

                if (t != null) { //check t is null or not first
                    if (t.value() == MAX_PIECE) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public boolean atLeastOneMoveExists() {
        if (board.size() <= 1) {
            if (board.tile(0, 0) == null) {
                return true;
            }
            return false;
        }
        // if null exist then true
        for (int i = 0; i <= board.size() - 1; i++) {
            for (int j = 0; j <= board.size() - 1; j++) {
                Tile tt = board.tile(i, j);
                if (tt == null) {
                    return true;
                }
            }
        }
        // if nearby (right, and up) is equal, then true
        for (int i = 0; i <= board.size() - 2; i++) {
            for (int j = 0; j <= board.size() - 2; j++) {
                //initialize
                Tile t = board.tile(i, j);
                int rightCheck = 0;
                int upCheck = 0;

                //since (0,0) staring from left end, only need ot check right and up decrease repeats
                if (i + 1 <= board.size() - 1) {
                    rightCheck = i + 1;
                }
                if (j + 1 <= board.size() - 1) {
                    upCheck = j + 1;
                }
                //the two adjacent tiles with same value
                if (t == null) {
                    return true;
                }
                if (tile(rightCheck, j) != null) {
                    if (board.tile(rightCheck, j).value() == t.value()) {
                        return true;
                    }
                }
                if (tile(i, upCheck) != null) {
                    if (board.tile(i, upCheck).value() == t.value()) {
                        return true;
                    }
                }

            }

        }
        //border case for checking the most up right corner
        if (board.size() >= 2) {
            for (int k = 0; k <= board.size() - 2; k++) {
                Tile currTileY = board.tile(board.size() - 1, k);
                Tile currTileX = board.tile(k, board.size() - 1);

                if (currTileY != null) {
                    if (board.tile(board.size() - 1, k + 1) != null) {
                        if (currTileY.value() == board.tile(board.size() - 1, k + 1).value()) {
                            return true;
                        }
                    }
                }

                if (currTileX != null) {
                    if (board.tile(k + 1, board.size() - 1) != null) {
                        if (currTileX.value() == board.tile(k + 1, board.size() - 1).value()) {
                            return true;
                        }
                    }

                }
            }
        }

        return false;
    }

    /**
     * Moves the tile at position (x, y) as far up as possible.
     *
     * Rules for Tilt:
     * 1. If two Tiles are adjacent in the direction of motion (ignoring empty space)
     *    and have the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */
    public void moveTileUpAsFarAsPossible(int x, int y) {
        Tile currTile = board.tile(x, y);
        int myValue = currTile.value();
        int num = 0;

        //task 5 move up NOOOO merge!
        if (currTile != null) {
            if (y != board.size() - 1) {
                for (int i = y; i <= board.size() - 2; i++) {

                    if (board.tile(x, i + 1) != null) {
                        if (board.tile(x, i + 1).value() != myValue) {
                            if (!board.tile(x, i + 1).wasMerged()) {
                                board.move(x, i, currTile);
                                break;
                            } else {
                                board.move(x, i, currTile);
                                break;
                            }

                        } else if (board.tile(x, i + 1).value() == myValue) {
                            if (!board.tile(x, i + 1).wasMerged()) {
                                //task 10, add scores
                                score += currTile.value() * 2;
                                board.move(x, i + 1, currTile);
                                break;
                            } else {
                                board.move(x, i, currTile);
                                break;
                            }
                        }
                    } else if (i == board.size() - 2) {
                        board.move(x, i + 1, currTile);
                        break;
                    }
                }
            }
        }

        //task 6 merging tiles use (wasMerged)

        /*
        //failed try.
        //rule 1 same value merge & rule 3 merge leading two in direction
        for (int i = y+1; i <= (board.size()-1); i++) {

            if(board.tile(x,y + i) != null) {
                if (board.tile(x, y + i).value() == myValue) {
                        myValue *= 2;
                        board.move(x, y + i, currTile);
                }
            }
        }

        //rule 2 if merged, will not merge again
        for (int i = y+1; i <= (board.size()-1); i++) {
            if(board.tile(x,y+i) != null) {
                if(board.tile(x,y+i).value() != myValue) {
                    //if it ever moved up at least once, then the -1 disrupt the code
                    if(i >=1) {
                        board.move(x,y+i-1,currTile);
                    }
                }
            }
        }
        */
        //rule 3 three identical tiles will only merge leading two
        //will be include within rule 1, since theres repeats
    }

    /** Handles the movements of the tilt in column x of board B
     * by moving every tile in the column as far up as possible.
     * The viewing perspective has already been set,
     * so we are tilting the tiles in this column up.
     * */
    public void tiltColumn(int x) {
        for (int i = board.size() - 1; i >= 0; i--) {
            if (board.tile(x, i) != null) {
                this.moveTileUpAsFarAsPossible(x, i);
            }
        }
    }

    public void tilt(Side side) {
        board.setViewingPerspective(side);
        for (int i = 0; i < board.size(); i++) {
            this.tiltColumn(i);
        }
        board.setViewingPerspective(Side.NORTH);

    }

    /** Tilts every column of the board toward SIDE.
     */
    public void tiltWrapper(Side side) {
        board.resetMerged();
        tilt(side);
    }


    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int y = size() - 1; y >= 0; y -= 1) {
            for (int x = 0; x < size(); x += 1) {
                if (tile(x, y) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(x, y).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (game is %s) %n", score(), over);
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Model m) && this.toString().equals(m.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
