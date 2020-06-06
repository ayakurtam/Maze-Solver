/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MazeApplication;


/**
 *
 * @author Aya Kurtam
 */
public class MazeDirections {
    // cell position
    int i,j;
    public MazeDirections(){
    };
    public MazeDirections(int i,int j){
        this.i = i;
        this.j = j;
    };
    public int i() { return i;}   // get i (for overloaded methods)
    public int j() { return j;}   // get j (for overloaded methods)
    public void Print(){
       System.out.println("(" + i + "," + j + ")");  //print the position
    }
    // go up
    public MazeDirections north(){
      return new MazeDirections(i-1,j);
    }
    //go down
    public MazeDirections south(){
        return new MazeDirections(i+1 , j);
    }
    //go right
    public MazeDirections east(){
        return new MazeDirections(i,j+1);
    }
    //go left
    public MazeDirections west(){
      return new MazeDirections(i,j-1);
    }
}