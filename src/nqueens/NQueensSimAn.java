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
public class NQueensSimAn extends NQueens {
    
    private final double STARTING_TEMP = 0.1;
    private final double LOWEST_TEMP = 0.0000001;
    private final int SUB_ITERATIONS = 50;
    private final double TEMP_FACTOR = 0.98;
    
    public NQueensSimAn(ChessBoard startingBoard) {
        super(startingBoard);
    }

    @Override
    public void solveBoard() {
        double temperature = STARTING_TEMP;
        
        while(temperature > LOWEST_TEMP){
            if(checkIfSolved()){
                break;
            }
            
            boolean newAccepted = false;
            ChessBoard acceptedInIteration = super.getBestBoard();
            for(int i = 0; i < SUB_ITERATIONS; i++){
                super.incrementIterations();
                ChessBoard randomBoard = getSuccessor(super.getBestBoard());
                int valDiff = acceptedInIteration.getNumAttacking() - randomBoard.getNumAttacking();
                //System.out.println("valDiff is " + valDiff);
                if(valDiff > 0){
                    acceptedInIteration = randomBoard;
                    newAccepted = true;
                }else{
                    double probability = Math.exp(valDiff/temperature);
                    Random rand = new Random();
                    double randomVal = rand.nextDouble();
                    //System.out.println("probability is " + probability);
                    if(randomVal < probability){
                        acceptedInIteration = randomBoard;
                        newAccepted = true;
                        //System.out.println("Accepting bad move!");
                    }
                }

                if(acceptedInIteration.getNumAttacking() == 0){
                    super.setBestBoard(acceptedInIteration);
                    break;
                }
            }
            
            if(newAccepted){
                super.setBestBoard(acceptedInIteration);
            }
            temperature = TEMP_FACTOR * temperature;
        }
    }
    
}
