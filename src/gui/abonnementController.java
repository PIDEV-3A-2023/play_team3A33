/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.UserDetails;
import entities.abonnement;
import entities.promotion;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.abonnementService;
import services.promotionService;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class abonnementController implements Initializable {

    int idabonn;
    @FXML
    private Label nomabonnLabel;
    @FXML
    private Label typeabonnLabel;
    @FXML
    private Label descriptionabonnLabel;
    @FXML
    private Label dateabonnLabel;
    @FXML
    private Button participerabonnButton;
    @FXML
    private Label nb_participantsLabel;
    
    UserDetails u=new UserDetails();
    promotionService Ps=new promotionService();
    @FXML
    private TextField idabonnF;
    @FXML
    private TextField iduserF;
    
    abonnementService Ev=new abonnementService();
    @FXML
    private ImageView imageview;
    @FXML
    private Label promotionComplet;
    @FXML
    private TextField idPartField;
    @FXML
    private Button annulerButton;
    @FXML
    private Button likeButton;
     @FXML
    private Button deslikeButton;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        idabonnF.setVisible(false);
        deslikeButton.setVisible(false);
        likeButton.setVisible(true);
                iduserF.setVisible(false);
                promotionComplet.setVisible(false);
                annulerButton.setVisible(false);
        likeButton.setOnAction(event -> {
    likeButton.setStyle("-fx-background-color: green;");
    deslikeButton.setVisible(false);
    PauseTransition delay = new PauseTransition(Duration.seconds(5));
});
deslikeButton.setVisible(true);
likeButton.setVisible(true);
deslikeButton.setOnAction(event -> {
    PauseTransition delay = new PauseTransition(Duration.seconds(5));
    delay.setOnFinished(abonn -> {
        likeButton.setStyle("-fx-background-color: green;");
        
    });
    delay.play();
    deslikeButton.setStyle("-fx-background-color: red;");
    likeButton.setVisible(false);
});



                

    }    
    private abonnement eve=new abonnement();
    
    public void setabonnement(abonnement e) {
        this.eve=e;
        nomabonnLabel.setText(e.getNom_abonn());
        typeabonnLabel.setText(e.getType_abonn());
        descriptionabonnLabel.setText(e.getDescription_abonn());
        dateabonnLabel.setText(String.valueOf(e.getDate()));
        nb_participantsLabel.setText(String.valueOf(e.getCode_promo()));
        idabonnF.setText(String.valueOf(e.getId_ab()));
        iduserF.setText(String.valueOf(1));
         String path = e.getImage_abonn();
         File file=new File(path);
         Image img = new Image(file.toURI().toString());
         imageview.setImage(img);

    }
    public void setIdabonn(int idabonn){
        this.idabonn=idabonn;
    }


    @FXML
    private void participerabonn(MouseEvent abonn) throws SQLException {

        LocalDate dateActuelle = LocalDate.now();
        Date dateSQL = Date.valueOf(dateActuelle);
        promotion p=new promotion(dateSQL,Integer.parseInt(iduserF.getText()),Integer.parseInt(idabonnF.getText()));
        
        Ps.ajouterpromotion(p);

        idPartField.setText(String.valueOf(27));
        annulerButton.setVisible(true);
       
        
        participerabonnButton.setVisible(false);
        
 
        }
    
    public void arreterabonn()
    {
        participerabonnButton.setVisible(false);
        promotionComplet.setVisible(true);
    }

    @FXML
    private void annulerpromotion(ActionEvent abonn) throws SQLException {
        promotion p=new promotion();
        p.setId_promo(Integer.parseInt(idPartField.getText()));
        Ps.Deletepromotion(p);
        participerabonnButton.setVisible(true);
        annulerButton.setVisible(false);
        
    }
    
    
}
