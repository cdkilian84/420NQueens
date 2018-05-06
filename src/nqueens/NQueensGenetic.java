//Christopher Kilian
//CS 420 - Spring 2018
//Programming Project 2 - N-Queens

package nqueens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//Concrete subclass of NQueens which implements the Genetic algorithm. A number of constant values are stored representing population size to be tested,
//maximum number of iterations allowed, and the mutation chance.
public class NQueensGenetic extends NQueens{
    
    //population size, mutation chance, and max time to run are all set here - optimal combination found through trial-and-error
    private final int POP_SIZE = 16;
    private final int MAX_TIME_TO_RUN = 65000;
    private final double MUTATION_CHANCE = 0.27;

    //Constructor accepting an integer representing the dimensions of the boards to be tested.
    //Passes a random ChessBoard to the superclass using this dimension, though this board is just a placeholder at this point.
    public NQueensGenetic(int dimension) {
        super(new ChessBoard(dimension));
    }

    
    //This method implements the Genetic algorithm. It initially generates a starting population, then runs through a series of loops in which
    //children to the randomly chosen parents are generated, checked for whether they represent a solution or not, and then added to the new population
    //to be tested in the next iteration. When a solution state is discovered, the loops are broken and the method ends with "best board" assigned the
    //solution ChessBoard.
    @Override
    public void solveBoard() {
        List<ChessBoard> population = generateInitial();
        Random rand = new Random();
        int timer = 0;
        
        while((!super.checkIfSolved()) && (timer < MAX_TIME_TO_RUN)){
            List<ChessBoard> newPopulation = new ArrayList<>();
            preparePopulation(population);
            boolean foundSolution = false;
            
            
            while(newPopulation.size() < POP_SIZE){
                //randomly select 2 parents from the population
                ChessBoard firstSelected = randomSelection(population);
                ChessBoard secondSelected = randomSelection(population);
                
                //produce 2 children from the parents
                ChessBoard[] children = reproduce(firstSelected, secondSelected);
                
                //process both of the returned children from the reproduction method
                for(ChessBoard child : children){
                    super.incrementIterations(); //iterations correspnd to children generated
                    //check for solution before mutation
                    if(child.getNumAttacking() == 0){
                        super.setBestBoard(child);
                        foundSolution = true;
                        break;
                    }

                    if(rand.nextDouble() <= MUTATION_CHANCE){
                        child = getSuccessor(child); //"mutate" child by randomly choosing and moving a piece
                        //check for solution again after mutating
                        if(child.getNumAttacking() == 0){
                            super.setBestBoard(child);
                            foundSolution = true;
                            break;
                        }
                    }

                    newPopulation.add(child);
                }
                if(foundSolution){
                    break;
                }
            }
            
            population = newPopulation;
            timer++;
        }
        //System.out.println("Finished at timer = " + timer);
    }
    
    
    //Method to generate a list of the initial population of randomly generated ChessBoards to be tested.
    private List<ChessBoard> generateInitial(){
        List<ChessBoard> initialPop = new ArrayList<>();
        
        for(int i = 0; i < POP_SIZE; i++){
            ChessBoard board = new ChessBoard(super.getDimensions()); //randomly generated chessboard of size "dimensions"
            initialPop.add(board);
        }
        
        return initialPop;
    }
    
    
    //Method to evaluate the fitness of the population and then sort it by the fitness value, for use by the random selection method.
    private void preparePopulation(List<ChessBoard> pop){
        evalFitness(pop);
        Collections.sort(pop, new SortByFitness());
    }
    
    
    //Method to set the fitness values for a list of boards. The fitness of a board is the normalized value of the number
    //of non-attacking queens (giving a "larger is better" fitness rating) a particular board has. The sum of all of the
    //fitnesses will be 1 thanks to the normalization process.
    private void evalFitness(List<ChessBoard> pop){
        int sumOfNonAttacking = 0;
        
        for(ChessBoard board : pop){
            sumOfNonAttacking += board.getNumNonAttackingQueens();
        }

        for(ChessBoard board : pop){
            board.setFitness(((double)board.getNumNonAttackingQueens())/sumOfNonAttacking);
        }
    }
    
    
    //Method to randomly select a member of the population for reproduction. This selection process is biased to
    //more often select a board with a higher fitness value by selecting a randomly generated number and then proceeding through
    //the population, summing their fitness values until the sum exceeds the random value. The last ChessBoard which didn't exceed this
    //value is the selected board.
    private ChessBoard randomSelection(List<ChessBoard> pop){
        ChessBoard selected = pop.get(0);
        Random rand = new Random();
        double randomVal = rand.nextDouble();
        double accumulatedFitness = 0.0;
        
        for(int i = 0; i < pop.size(); i++){
            accumulatedFitness += pop.get(i).getFitness();
            if(accumulatedFitness >= randomVal){
                break;
            }else{
                selected = pop.get(i);
            }
        }
        
        return selected;
    }
    
    
    //Method which accepts two ChessBoards to "reproduce" and create 2 child boards which are a combination of their
    //parent board arrangements. A random value is selected between 0 and the largest index value of the board state arrays (the
    //Queen arrays). This value is used a split value, with one child getting the Queens up to the split value from parent A and the
    //remainder from parent B, while the second child recieves the opposite. Both children are returned as a two element array of ChessBoards.
    private ChessBoard[] reproduce(ChessBoard parentA, ChessBoard parentB){
        int dimensions = parentA.getBoardDimensions();
        Queen[] queensOfA = parentA.getBoardStatus();
        Queen[] queensOfB = parentB.getBoardStatus();
        Queen[] newSetOfQueensA = new Queen[dimensions];
        Queen[] newSetOfQueensB = new Queen[dimensions];
        Random rand = new Random();
        int splitPoint = rand.ints(0, dimensions).limit(1).findFirst().getAsInt();
        
        //first part of childA board is from parent A, and first part of childB board is from parent B
        for(int i = 0; i < splitPoint; i++){
            newSetOfQueensA[i] = queensOfA[i];
            newSetOfQueensB[i] = queensOfB[i];
        }
        
        //second part of childA board is from parent B, and second part of childB board is from parent A
        for(int i = splitPoint; i < dimensions; i++){
            newSetOfQueensA[i] = queensOfB[i];
            newSetOfQueensB[i] = queensOfA[i];
        }

        //create children boards from queen sets
        ChessBoard[] children = {new ChessBoard(newSetOfQueensA), new ChessBoard(newSetOfQueensB)};
        return children;
    }
    
    
}
