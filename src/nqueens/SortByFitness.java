//Christopher Kilian
//CS 420 - Spring 2018
//Programming Project 2 - N-Queens

package nqueens;

import java.util.Comparator;

//Simple comparator for two chessboard objects, based on their fitness values (results in descending ordered lists)
//For use in the Genetic algorithm
public class SortByFitness implements Comparator<ChessBoard>{

    @Override
    public int compare(ChessBoard a, ChessBoard b){
        int comp = 0;
        
        double difference = b.getFitness() - a.getFitness();
        if(difference > 0){
            comp = 1;
        }else if(difference < 0){
            comp = -1;
        }
        
        return comp;
    }
}
