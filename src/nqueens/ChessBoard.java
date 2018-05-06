//Christopher Kilian
//CS 420 - Spring 2018
//Programming Project 2 - N-Queens

package nqueens;

import java.util.Random;

//This class represents an n x n chess board. It contains an array of queens that are positioned on the board, as well as various information and attributes about
//the state of the game board, such as how many pairs of attacking queens exist, how many individual queens are not attacking/being attacked, the board dimensions,
//and an optional "fitness" value which can be assigned by outside actors depending on need.
public class ChessBoard {
    private int boardDimension;
    private int numAttackingQueens;
    private int numNonAttackingQueens;
    private double fitness; //a "fitness" value which can be assigned by outside operators depending on needs
    private Queen[] queens;
    private final int DEFAULT_DIMENSION = 21;
    
    //Constructor that builds a board with a semi-random queen placement (random in row but not column).
    //This constructor only takes an integer value representing board dimensions and builds the random board accordingly.
    public ChessBoard(int dimensions){
        if(dimensions > 0){
            this.boardDimension = dimensions;
        }else{
            boardDimension = DEFAULT_DIMENSION;
        }
        this.numAttackingQueens = 0;
        this.numNonAttackingQueens = 0;
        this.fitness = 0.0;
        queens = new Queen[boardDimension];
        initBoard();
        evalBoard();
    }
    
    //This constructor will allow the user to setup a new board using pre-defined queen positions,
    //rather than randomly generated ones (however will create a default sized random board if a zero sized array is passed).
    public ChessBoard(Queen[] queenArr){
        if(queenArr.length > 0){
            this.queens = queenArr;
            this.boardDimension = queens.length;
        }else{
            boardDimension = DEFAULT_DIMENSION;
            queens = new Queen[boardDimension];
            initBoard();
        }
        
        this.numAttackingQueens = 0;
        this.numNonAttackingQueens = 0;
        this.fitness = 0.0;
        evalBoard();
    }
    
    
    //Method to randomly initialize a board based on the number of queens (in turn based on board dimensions) required.
    //Place 1 queen per column, in randomly generated rows.
    private void initBoard(){
        Random rowGenerator = new Random();
        
        for(int i = 0; i < boardDimension; i++){
            int row = rowGenerator.ints(0, boardDimension).limit(1).findFirst().getAsInt();
            queens[i] = new Queen(row, i, boardDimension); //note that column position matches queen index value - queen[3] will be in column 3 (zero indexed of course)
        }
	
    }
    
    
    //Method to evaluate the status of the board. Simply a container for multiple function calls which need to be made back-to-back.
    private void evalBoard(){
        countAttacking();
        countNotAttacking();
    }
    
    
    //Method to count the number of attacking pairs of queens on the board. Does not double count (one increment per attacking pair).
    private void countAttacking(){
        for(int i = 0; i < boardDimension; i++){
            for(int j = i+1; j < boardDimension; j++){
                if(queens[i].checkAttack(queens[j])){
                    numAttackingQueens++;
                }
            }
        }
    }
    
    
    //Method to count the number of non-attacking pairs of queens on the board. Must be done separately from attacking pairs since
    //attacking pairs only counts once per pair (in other words, for each queen i, must check if it's attack ALL other queens on the board,
    //instead of only those to its right on the board).
    private void countNotAttacking(){
        for(int i = 0; i < boardDimension; i++){
            boolean queenAttacking = false;
            for(int j = 0; j < boardDimension; j++){
                if((i != j) && queens[i].checkAttack(queens[j])){
                    queenAttacking = true;
                    break;
                }
            }
            if(!queenAttacking){
                numNonAttackingQueens++;
            }
        }
    }
    
    
    //Method which moves a piece by creating a copy of the board, moving the piece in the copy, and returning the new board.
    //Note that if the queen to move value is outside the acceptable bounds, it defaults to moving queen 0.
    public ChessBoard movePiece(int queenToMove, int numSpaces){
        int checkedQueenNum = queenToMove;
        
        if((checkedQueenNum < 0) || (checkedQueenNum >= boardDimension)){
            checkedQueenNum = 0;
        }
        
        Queen[] newBoard = copyQueens();

        for(int i = 0; i < numSpaces; i++){
            newBoard[checkedQueenNum].moveUpColumn();
        }
    
        return new ChessBoard(newBoard);
    }
    
    
    //Method to generate a deep copy of the board state as determined by the queens array - for use by "movePiece" method.
    private Queen[] copyQueens(){
        Queen[] copy = new Queen[boardDimension];
        
        for(int i = 0; i < boardDimension; i++){
            copy[i] = new Queen(queens[i]);            
        }
        
        return copy;
    }
    
    //Getter for number of attacking queen pairs
    public int getNumAttacking(){
        return numAttackingQueens;
    }
    
    //Getter for board dimensions
    public int getBoardDimensions(){
        return boardDimension;
    }
    
    //Getter for the queen array which represents the board status
    public Queen[] getBoardStatus(){
        return queens;
    }

    //Getter for the fitness value
    public double getFitness() {
        return fitness;
    }
    
    //Getter for number of non-attacking queens
    public int getNumNonAttackingQueens() {
        return numNonAttackingQueens;
    }

    //Setter for the fitness value (disallows negative fitness values)
    public void setFitness(double fitness) {
        if(fitness >= 0){
            this.fitness = fitness;
        }
    }

}
