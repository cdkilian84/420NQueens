/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nqueens;

import java.util.Comparator;

//Simple comparator for two chessboard objects, based on their fitness values
//For use in the Genetic algorithm
public class SortByFitness implements Comparator<ChessBoard>{
    // Used for sorting in ascending order of
    // roll name
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
