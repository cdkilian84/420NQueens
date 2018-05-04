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
public abstract class NQueens {
    private ChessBoard bestBoard;
    private int dimensions;
    private int testIterations;
    
    public NQueens(ChessBoard startingBoard){
        bestBoard = startingBoard;
        dimensions = startingBoard.getBoardDimensions();
        testIterations = 0;
    }
    
    public abstract void solveBoard();
    
    //simulated annealing
//    public void solveBoard(){
//        double temperature = 0.1;
//        
//        while(temperature > 0.0000001){
//            
//            boolean improved = false;
//            ChessBoard acceptedInIteration = bestBoard;
//            for(int i = 0; i < 50; i++){
//                testIterations++;
//                ChessBoard randomBoard = getSuccessor(bestBoard);
//                int valDiff = acceptedInIteration.getNumAttacking() - randomBoard.getNumAttacking();
//                //System.out.println("valDiff is " + valDiff);
//                if(valDiff > 0){
//                    acceptedInIteration = randomBoard;
//                    improved = true;
//                }else{
//                    double probability = Math.exp(valDiff/temperature);
//                    Random rand = new Random();
//                    double randomVal = rand.nextDouble();
//                    //System.out.println("probability is " + probability);
//                    if(randomVal < probability){
//                        acceptedInIteration = randomBoard;
//                        improved = true;
//                        //System.out.println("Accepting bad move!");
//                    }
//                }
//
//                if(acceptedInIteration.getNumAttacking() == 0){
//                    bestBoard = acceptedInIteration;
//                    break;
//                }
//            }
//            
//            if(improved){
//                bestBoard = acceptedInIteration;
//            }
//            if(checkIfSolved()){
//                break;
//            }
//            temperature = 0.98 * temperature;
//        }
//    }
    
    
    protected ChessBoard getSuccessor(ChessBoard board){
        Random rand = new Random();
        int pieceToMove = rand.ints(0, getDimensions()).limit(1).findFirst().getAsInt();
        int numSpaces = rand.ints(1, getDimensions()).limit(1).findFirst().getAsInt();
        
        return board.movePiece(pieceToMove, numSpaces);
    }
    
    
    public boolean checkIfSolved(){
        boolean solved = false;
        
        if(bestBoard.getNumAttacking() == 0){
            solved = true;
        }
        
        return solved;
    }

    /**
     * @return the bestBoard
     */
    public ChessBoard getBestBoard() {
        return bestBoard;
    }
    
    public int getTestIterations(){
        return testIterations;
    }
    
    protected void incrementIterations(){
        testIterations++;
    }
    
    protected void setBestBoard(ChessBoard board){
        bestBoard = board;
    }

    /**
     * @return the dimensions
     */
    public int getDimensions() {
        return dimensions;
    }
    
}
