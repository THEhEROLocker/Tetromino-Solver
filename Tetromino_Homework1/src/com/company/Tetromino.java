package com.company;

/**
 * Created by hERO on 4/5/17.
 */
public class Tetromino {
    private char piece;
    private char fillChar;
    private boolean used;

    public Tetromino(){
        piece = '\0';
        fillChar = '\0';
        used = false;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Tetromino(char piece, char fillChar) {
        this.piece = piece;
        this.fillChar = fillChar;
        this.used = false;

    }

    public void copy(char piece, char fillChar){
        this.piece = piece;
        this.fillChar = fillChar;
    }


    public char getPiece() {
        return piece;
    }

    public char getFillChar() {
        return fillChar;
    }

    public void display(){
        System.out.println(this.piece + " " + this.fillChar);
    }

    public Tetromino clone(){
        return new Tetromino(this.piece,this.fillChar);
    }

}
