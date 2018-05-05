/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nqueens;

import java.util.Random;

/**
 *
 * @author Chris
 */
public class ChessBoard {
    private int boardDimension;
    private int numAttackingQueens;
    private int numNonAttackingQueens;
    private double fitness; //a "fitness" value which can be assigned by outside operators depending on needs
    private Queen[] queens;
    private final int DEFAULT_DIMENSION = 21;
    
    
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
    
    //this constructor will allow the user to setup a new board using pre-defined queen positions,
    //rather than randomly generated ones (however will create a default sized random board if a zero sized array is passed)
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
    
    //place 1 queen per column, in randomly generated rows
    private void initBoard(){
        Random rowGenerator = new Random();
        
        for(int i = 0; i < boardDimension; i++){
            int row = rowGenerator.ints(0, boardDimension).limit(1).findFirst().getAsInt();
            //System.out.println("Queen " + i + " placed in row " + row);
            queens[i] = new Queen(row, i, boardDimension); //note that column position matches queen index value - queen[3] will be in column 3 (zero indexed of course)
        }
	
    }
    
    //Counts pairs of attacking queens (pair is not double counted)
    private void evalBoard(){
        for(int i = 0; i < boardDimension; i++){
            boolean attacking = false;
            for(int j = i+1; j < boardDimension; j++){
                if(queens[i].checkAttack(queens[j])){
                    numAttackingQueens++;
                    attacking = true;
                }
            }
            if(!attacking){
                numNonAttackingQueens++;
            }
        }
    }
    
    
    //moves a piece by creating a copy of the board, moving the piece in the copy, and returning the new board
    //Note that if the queen to move value is outside the acceptable bounds, it defaults to moving queen 0
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
    
    
    //generate a deep copy of the board state as determined by the queens array - for use by "movePiece" method
    private Queen[] copyQueens(){
        Queen[] copy = new Queen[boardDimension];
        
        for(int i = 0; i < boardDimension; i++){
            copy[i] = new Queen(queens[i]);            
        }
        
        return copy;
    }
    
    public int getNumAttacking(){
        return numAttackingQueens;
    }
    
    public int getBoardDimensions(){
        return boardDimension;
    }
    
    public Queen[] getBoardStatus(){
        return queens;
    }

    /**
     * @return the fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(double fitness) {
        if(fitness >= 0){
            this.fitness = fitness;
        }
    }

    /**
     * @return the numNonAttackingQueens
     */
    public int getNumNonAttackingQueens() {
        return numNonAttackingQueens;
    }
    
    
}
