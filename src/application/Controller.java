package application;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static javafx.scene.paint.Color.WHITE;

/* Controller needs to implement Initializable as JavaFX relies on the class having
 * an "initialize" method. It is going to execute the "initialize" method only AFTER the layout
 * file has been loaded.
 */

public class Controller implements Initializable {

   @FXML // The FXML loader is going to inject from the layout
   Button button_one;
   @FXML Button button_two;
   @FXML Button button_three;
   @FXML Button button_four;
   @FXML Button button_five;
   @FXML Button button_six;
   @FXML Button button_seven;
   @FXML Button button_eight;
   @FXML Button button_nine;
   @FXML Canvas canvas;

    GameBoard gameboard;
   private int player_selected_row;
   private int player_selected_col;


    public void initialize(URL arg0, ResourceBundle arg1){
        //Create an instance of our gameboard
        gameboard = new GameBoard();
        //Get graphics context from canvas
        GraphicsContext context = canvas.getGraphicsContext2D();
        //Call drawOnCanvas method, with the context we have gotten from the canvas
        drawOnCanvas(context);
        player_selected_col = 0;
        player_selected_row = 0;

   }

    public void drawOnCanvas(GraphicsContext context) {
        context.clearRect(0, 0, 450, 450);

        for(int row = 0; row<9; row++) {
            for (int col = 0; col < 9; col++) {
                // finds the y position of the cell, by multiplying the row number by 50, which is the height of a row 					// in pixels
                // then adds 2, to add some offset
                int position_y = row * 50 + 2;

                // finds the x position of the cell, by multiplying the column number by 50, which is the width of a 					// column in pixels
                // then add 2, to add some offset
                int position_x = col * 50 + 2;

                // defines the width of the square as 46 instead of 50, to account for the 4px total of blank space 					// caused by the offset
                // as we are drawing squares, the height is going to be the same
                int width = 46;

                // set the fill color to white (you could set it to whatever you want)
                context.setFill(Color.WHITE);

                // draw a rounded rectangle with the calculated position and width. The last two arguments specify the 					// rounded corner arcs width and height.
                // Play around with those if you want.
                context.fillRoundRect(position_x, position_y, width, width, 10, 10);
            }
        }
        // draw highlight around selected cell
        // set stroke color to res
        context.setStroke(Color.PURPLE);
        // set stroke width to 5px
        context.setLineWidth(5);
        // draw a strokeRoundRect using the same technique we used for drawing our board.
        context.strokeRoundRect(player_selected_col * 50 + 2, player_selected_row * 50 + 2, 46, 46, 10, 10);

        // draw the initial numbers from our GameBoard instance
        int[][] initial = gameboard.getInitial();

        // for loop is the same as before
        for(int row = 0; row<9; row++) {
            for(int col = 0; col<9; col++) {

                // finds the y position of the cell, by multiplying the row number by 50, which
                // is the height of a row in pixels then adds 2, to add some offset
                int position_y = row * 50 + 30;

                // finds the x position of the cell, by multiplying the column number by 50,
                // which is the width of a column in pixels then add 2, to add some offset
                int position_x = col * 50 + 20;

                // set the fill color to white (you could set it to whatever you want)
                context.setFill(Color.BLACK);

                // set the font, from a new font, constructed from the system one, with size 20
                context.setFont(new Font("Times Roman", 20));

                // check if value of coressponding initial array position is not 0, remember that
                // we treat zeroes as squares with no values.
                if(initial[row][col]!=0) {

                    // draw the number using the fillText method
                    context.fillText(initial[row][col] + "", position_x, position_y);
                }
            }
        }

        // draw the players numbers from our GameBoard instance
        int[][] player = gameboard.getPlayer();
        for(int row = 0; row<9; row++) {
            for(int col = 0; col<9; col++) {
                // finds the y position of the cell, by multiplying the row
                // number by 50, which is the height of a row in pixels
                // then adds 2, to add some offset
                int position_y = row * 50 + 30;
                // finds the x position of the cell, by multiplying the column
                // number by 50, which is the width of a column in pixels
                // then add 2, to add some offset
                int position_x = col * 50 + 20;
                // set the fill color to purple (you could set it to whatever you want)
                context.setFill(Color.PURPLE);
                // set the font, from a new font, constructed from the system one, with size 20
                context.setFont(new Font(20));
                // check if value of coressponding array position is not 0
                if(player[row][col]!=0) {
                    // draw the number
                    context.fillText(player[row][col] + "", position_x, position_y);
                }
            }
        }
    }

    public void canvasMouseClicked(){
        // attach a new EventHandler of the MouseEvent type to the canvas
        // override the MouseEvent to do what we want it to do
        canvas.setOnMouseClicked(event -> {
            // intercept the mouse position relative to the canvas and cast it to an integer
            int mouse_x = (int) event.getX();
            int mouse_y = (int) event.getY();

            // convert the mouseX and mouseY into rows and cols
            // We are going to take advantage of the way integers are treated and we are going to divide
            //by a cell's width.
            // This way any value between 0 and 449 for x and y is going to give us an integer from
            //0 to 8, which is exactly what we are after.
            player_selected_row = (int) (mouse_y / 50); // update player selected row
            player_selected_col = (int) (mouse_x / 50); // update player selected column

            //get the canvas graphics context and redraw
            drawOnCanvas(canvas.getGraphicsContext2D());
        });


    }

    public void buttonOnePressed(){
        // The only thing that changes between all nine methods is the value we are injecting
        // in the player array. In this case, it is 1, because it corresponds to the button.
        gameboard.modifyPlayer(1, player_selected_row, player_selected_col);

        // refresh our canvas
        drawOnCanvas(canvas.getGraphicsContext2D());
    }
    public void buttonTwoPressed(){
        gameboard.modifyPlayer(2, player_selected_row, player_selected_col);
        drawOnCanvas(canvas.getGraphicsContext2D());

    }
    public void buttonThreePressed(){
        gameboard.modifyPlayer(3, player_selected_row, player_selected_col);
        drawOnCanvas(canvas.getGraphicsContext2D());

    }
    public void buttonFourPressed(){
        gameboard.modifyPlayer(4, player_selected_row, player_selected_col);
        drawOnCanvas(canvas.getGraphicsContext2D());

    }
    public void buttonFivePressed(){
        gameboard.modifyPlayer(5, player_selected_row, player_selected_col);
        drawOnCanvas(canvas.getGraphicsContext2D());

    }
    public void buttonSixPressed(){
        gameboard.modifyPlayer(6, player_selected_row, player_selected_col);
        drawOnCanvas(canvas.getGraphicsContext2D());

    }
    public void buttonSevenPressed(){
        gameboard.modifyPlayer(7, player_selected_row, player_selected_col);
        drawOnCanvas(canvas.getGraphicsContext2D());

    }
    public void buttonEightPressed(){
        gameboard.modifyPlayer(8, player_selected_row, player_selected_col);
        drawOnCanvas(canvas.getGraphicsContext2D());

    }
    public void buttonNinePressed(){
        gameboard.modifyPlayer(9, player_selected_row, player_selected_col);
        drawOnCanvas(canvas.getGraphicsContext2D());

    }
}

