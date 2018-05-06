//Christopher Kilian
//CS 420 - Spring 2018
//Programming Project 2 - N-Queens

package nqueens;

import java.util.Random;

//Concrete subclass of NQueens which implements the Simulated Annealing algorithm. A number of constant values are stored representing temperature values,
//number of allowed sub-iterations, and the multiplier for the "temperature" factor.
//Simulated Annealing algorithm based partly on the pseudocode provided in "Artificial Intelligence A Modern Approach", and partly on research paper
//"Practical Considerations for Simulated Annealing Implementation" found at:
//https://www.researchgate.net/profile/Sergio_Ledesma2/publication/221786387_Practical_Considerations_for_Simulated_Annealing_Implementation/links/00b495214f759aa71d000000/Practical-Considerations-for-Simulated-Annealing-Implementation.pdf

public class NQueensSimAn extends NQueens {
    
    private final double STARTING_TEMP = 0.1;
    private final double LOWEST_TEMP = 0.0000001;
    private final int SUB_ITERATIONS = 50;
    private final double TEMP_FACTOR = 0.98;
    
    //Constructor
    public NQueensSimAn(ChessBoard startingBoard) {
        super(startingBoard);
    }

    
    //Implementation of the Simulated Annealing algorithm. An outer loop allows the search for a solution to continue until the
    //temperature has moved below an established constant value (developed through trial and error). An innter loop
    //allows for multiple new successor boards to be tested at a given temperature value, allowing the theoretical "best" choice at a
    //given temperature to propagate forward to the next temperature iteration. When a solution is discovered, the loops are broken
    //and the method ends with the "best board" set to the solution state ChessBoard.
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
                
                if(valDiff > 0){
                    acceptedInIteration = randomBoard;
                    newAccepted = true;
                }else{
                    double probability = Math.exp(valDiff/temperature);
                    Random rand = new Random();
                    double randomVal = rand.nextDouble();
                    
                    if(randomVal < probability){
                        acceptedInIteration = randomBoard;
                        newAccepted = true;
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
