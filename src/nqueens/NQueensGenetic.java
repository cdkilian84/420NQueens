/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nqueens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Chris
 */
public class NQueensGenetic extends NQueens{
    
    private int boardDimension;
    private final int INITIAL_POP_SIZE = 20;
    private final int MAX_TIME_TO_RUN = 100000;
    private final double MUTATION_CHANCE = 0.1;

    public NQueensGenetic(ChessBoard startingBoard) {
        super(startingBoard);
        boardDimension = startingBoard.getBoardDimensions();
    }

    //implement genetic algorithm
    @Override
    public void solveBoard() {
        List<ChessBoard> population = generateInitial();
        int timer = 0;
        
        while((!super.checkIfSolved()) && (timer < MAX_TIME_TO_RUN)){
            List<ChessBoard> newPopulation = new ArrayList<>();
            preparePopulation(population);
            Random rand = new Random();
            
            for(int i = 0; i < population.size(); i++){
                super.incrementIterations();
                ChessBoard firstSelected = randomSelection(population);
                ChessBoard secondSelected = randomSelection(population);
                
                ChessBoard child = reproduce(firstSelected, secondSelected);
                
                if(child.getNumAttacking() == 0){
                    super.setBestBoard(child);
                    break;
                }
                
                if(rand.nextDouble() <= MUTATION_CHANCE){
                    child = getSuccessor(child); //"mutate" child by randomly choosing and moving a piece
                }
                
                if(child.getNumAttacking() == 0){
                    super.setBestBoard(child);
                    break;
                }
                newPopulation.add(child);
            }
            
            population = newPopulation;
            timer++;
        }
    }
    
    private List<ChessBoard> generateInitial(){
        List<ChessBoard> initialPop = new ArrayList<>();
        
        for(int i = 0; i < INITIAL_POP_SIZE; i++){
            ChessBoard board = new ChessBoard(super.getDimensions()); //randomly generated chessboard of size "dimensions"
            initialPop.add(board);
        }
        
        return initialPop;
    }
    
    
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
    
    //evaluate the fitness of the population and then sort it by the fitness value
    private void preparePopulation(List<ChessBoard> pop){
        evalFitness(pop);
        Collections.sort(pop, new SortByFitness());
//        System.out.println("Starting...");
//        for(int i = 0; i < pop.size(); i++){
//            System.out.println("Board fitness: " + pop.get(i).getFitness());
//        }
//        System.out.println("DONE");
    }
    
    
    //set the fitness values for a list of boards - version where higher is better (non-attacking queens)
    private void evalFitness(List<ChessBoard> pop){
        int sumOfNonAttacking = 0;
        
        for(ChessBoard board : pop){
            sumOfNonAttacking += board.getNumNonAttackingQueens();
        }
        
        //System.out.println("sum of attacking is " + sumOfAttacking);
        
        //for testing:
        //double sumOfNorm = 0.0;
        for(ChessBoard board : pop){
            board.setFitness(((double)board.getNumNonAttackingQueens())/sumOfNonAttacking);
            //sumOfNorm += board.getFitness();
        }
        //System.out.println("Sum of all fitness values is: " + sumOfNorm);
    }

    
    //when using the sum of attacking queens (instead of non-attacking)
//    private void evalFitness(List<ChessBoard> pop){
//        int sumOfAttacking = 0;
//        
//        for(ChessBoard board : pop){
//            sumOfAttacking += board.getNumAttacking();
//        }
//        
//        //System.out.println("sum of attacking is " + sumOfAttacking);
//        
//        //for testing:
//        //double sumOfNorm = 0.0;
//        for(ChessBoard board : pop){
//            board.setFitness(((double)board.getNumAttacking())/sumOfAttacking);
//            //sumOfNorm += board.getFitness();
//        }
//        //System.out.println("Sum of all fitness values is: " + sumOfNorm);
//    }
    
    
    private ChessBoard reproduce(ChessBoard parentA, ChessBoard parentB){
        int dimensions = parentA.getBoardDimensions();
        Queen[] queensOfA = parentA.getBoardStatus();
        Queen[] queensOfB = parentB.getBoardStatus();
        Queen[] newSetOfQueens = new Queen[dimensions];
        Random rand = new Random();
        int splitPoint = rand.ints(0, dimensions).limit(1).findFirst().getAsInt();
        
        //first part of new child board is from parent A
        for(int i = 0; i < splitPoint; i++){
            newSetOfQueens[i] = queensOfA[i];
        }
        
        //second part of new child board is from parent B
        for(int i = splitPoint; i < dimensions; i++){
            newSetOfQueens[i] = queensOfB[i];
        }
        
        return new ChessBoard(newSetOfQueens);
    }
    
}
