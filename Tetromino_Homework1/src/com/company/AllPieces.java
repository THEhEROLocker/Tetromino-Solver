package com.company;

import java.util.Stack;

/**
 * Created by hERO on 4/7/17.
 */
public class AllPieces {
    private Tetromino[] pieces;
    private int num_pieces;
    private String piecesInp;

    private int numPiecesUsed;

    public AllPieces(){
    }

    public AllPieces(String piecesInp){
        this.num_pieces = piecesInp.length();
        this.pieces = new Tetromino[this.num_pieces];
        this.piecesInp = piecesInp;

        char toFill = 'A';

        for(int i = 0; i < num_pieces;++i){
            pieces[i] = new Tetromino(piecesInp.charAt(i),toFill);
            toFill+=1;
        }
    }

    public int getNum_pieces() {
        return num_pieces;
    }

    public void displayAll(){
        for(int i=0; i < num_pieces; ++i){
            pieces[i].display();
        }
    }

    public AllPieces clone(){
        return new AllPieces(this.piecesInp);
    }

    public boolean isBoardComplete(){
        return numPiecesUsed == num_pieces;
    }

    public Tetromino getNextPiece(int start){
        for(int i=start; i < num_pieces;++i){
            if(pieces[i].isUsed() == false){
                this.numPiecesUsed +=1;
                pieces[i].setUsed(true);
                return pieces[i];
            }
        }
        return null;
    }

    public void switchLastPieceState(char toCompare){
        for(int i = 0; i< num_pieces; ++i){
            if(pieces[i].getFillChar() == toCompare){
                this.numPiecesUsed-=1;
                pieces[i].setUsed(false);
            }
        }
    }
}
