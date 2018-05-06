//Christopher Kilian
//CS 420 - Spring 2018
//Programming Project 2 - N-Queens

package nqueens;

import java.util.Random;

//Top level class for the NQueens problem solver. This level of the class holds the "best" board seen so far (which in a solved puzzle will be the solution state
//board), the dimensions for the board puzzle being solved, and the number of iterations (corresponding to children generated) a particular test takes.
//Concrete sub-classes of this superclass must implement the "solveBoard" method using whatever algorithm is needed.
public abstract class NQueens {
    private ChessBoard bestBoard;
    private int dimensions;
    private int testIterations;
    
    //Constructor to initialize values
    public NQueens(ChessBoard startingBoard){
        bestBoard = startingBoard;
        dimensions = startingBoard.getBoardDimensions();
        testIterations = 0;
    }
    
    
    //Abstract method representing the action of solving a given NQueens puzzle - to be implemented by subclasses
    public abstract void solveBoard();
    
    
    //Method to get a "successor" board to a provided board state - a "successor" is a board which can be produced by moving one queen
    //in the indicated "parent" board some number of spaces. This method will randomly pick a piece to move and the number of spaces to move it,
    //and then return a new ChessBoard with that piece moved.
    protected ChessBoard getSuccessor(ChessBoard board){
        Random rand = new Random();
        int pieceToMove = rand.ints(0, getDimensions()).limit(1).findFirst().getAsInt();
        int numSpaces = rand.ints(1, getDimensions()).limit(1).findFirst().getAsInt();
        
        return board.movePiece(pieceToMove, numSpaces);
    }
    
    
    //A check to see if the current "best board" is a solution state - a solved board is one with no attacking queen pairs.
    //Returns true if the best board is a solution state.
    public boolean checkIfSolved(){
        boolean solved = false;
        
        if(bestBoard.getNumAttacking() == 0){
            solved = true;
        }
        
        return solved;
    }

    //Getter for the best board
    public ChessBoard getBestBoard() {
        return bestBoard;
    }
    
    //Getter for test iterations
    public int getTestIterations(){
        return testIterations;
    }
    
    //Getter for this test's dimensions
    public int getDimensions() {
        return dimensions;
    }
    
    //Method to be used by subclasses to increment the iterations taken by a solution test
    protected void incrementIterations(){
        testIterations++;
    }
    
    //Method to be used by subclasses to swap out the current best board with a new one
    protected void setBestBoard(ChessBoard board){
        bestBoard = board;
    }

}
