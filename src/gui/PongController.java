/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import entities.UserDetails;
import entities.abonnement;
import entities.promotion;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import services.promotionService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.abonnementService;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.twilio.rest.api.v2010.account.message.Media;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.MediaPlayer;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import services.Pdf2;

import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import gui.Pong;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;



public class PongController {

private final int width = 800;
private final int height = 600;
private final int PLAYER_HEIGHT = 100;
private final int PLAYER_WIDTH = 15;
private final int BALL_R = 15;

private int playerOneYPos = height / 2;
private int playerTwoYPos = height / 2;
private int ballXPos = width / 2;
private int ballYPos = height / 2;
private int ballXSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
private int ballYSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
private int scoreP1 = 0;
private int scoreP2 = 0;
   private Parent root;
	
	private boolean gameStarted;
	private int playerOneXPos = 0;
	private double playerTwoXPos = width - PLAYER_WIDTH;
		



@FXML
private Canvas canvas;
@FXML
private Button startButton;

@FXML
private void handleStartButton(ActionEvent event) {
    gameStarted = true;
}


public void initialize() {
    GraphicsContext gc = canvas.getGraphicsContext2D();

    //JavaFX Timeline = free form animation defined by KeyFrames and their duration 
    Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
    //number of cycles in animation INDEFINITE = repeat indefinitely
    tl.setCycleCount(Timeline.INDEFINITE);

    //mouse control (move and click)
    canvas.setOnMouseMoved(e -> playerOneYPos = (int) e.getY());
    canvas.setOnMouseClicked(e -> gameStarted = true);

    tl.play();
}



void run(GraphicsContext gc) {
    //set graphics
    //set background color
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, width, height);

    //set text
    gc.setFill(Color.WHITE);
    gc.setFont(Font.font(25));

    if (gameStarted) {
        //set ball movement
        ballXPos += ballXSpeed;
        ballYPos += ballYSpeed;

        //simple computer opponent who is following the ball
        if (ballXPos < width - width / 4) {
            playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
        } else {
            playerTwoYPos = ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2 ? playerTwoYPos + 1 : playerTwoYPos - 1;
        }
        //draw the ball
        gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);

    } else {
        //set the start text
        gc.setStroke(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText("Click", width / 2, height / 2);

        //reset the ball start position 
        ballXPos = width / 2;
        ballYPos = height / 2;

        //reset the ball speed and the direction
        ballXSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
        ballYSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
    }

    //makes sure the ball stays in the canvas
    if (ballYPos > height || ballYPos < 0)
        ballYSpeed *= -1;

    //if you miss the ball, computer gets a point
    if (ballXPos < -BALL_R) {
        scoreP2++;
        gameStarted = false;
    }

    //if the computer misses the ball, you get a point
    if (ballXPos > width + BALL_R) {
        scoreP1++;
        gameStarted = false;
    }

 //increase the speed after the ball hits the player
 if( ((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) || 
			((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
			ballYSpeed += 1 * Math.signum(ballYSpeed);
			ballXSpeed += 1 * Math.signum(ballXSpeed);
			ballXSpeed *= -1;
			ballYSpeed *= -1;
		}
		
		//draw score
		gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, width / 2, 100);
		//draw player 1 & 2
		gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
		gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
}}