/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.abonnement;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import services.abonnementService;
import utils.mailing;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
/**
 * FXML Controller class
 *
 * @author asus
 */
public class AfficherabonnementController implements Initializable {

    @FXML
    private GridPane gridabonn;

    abonnementService ab=new abonnementService();
    @FXML
    private TextField chercherabonnField;
    @FXML
    private Button ajouter;
    @FXML
    private Button mailButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        afficherabonnement();
               
    }    


    @FXML
    private void ajouterabonnement(ActionEvent abonn) {
      try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("ajouterabonnement.fxml"));
            chercherabonnField.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void afficherabonnement(){
         try {
            List<abonnement> abonnements = ab.recupererabonnement();
            gridabonn.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < abonnements.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("abonnement.fxml"));
                AnchorPane pane = loader.load();
               
                //passage de parametres
                abonnementController controller = loader.getController();
                controller.setabonnement(abonnements.get(i));
                controller.setIdabonn(abonnements.get(i).getId_ab());
                gridabonn.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
                if(abonnements.get(i).getCode_promo()<=0)
                {
                 // ab.supprimerabonnement(abonnements.get(i));
                controller.arreterabonn();
                }
            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }   
    }

    @FXML
    private void rechercherabonnement(KeyEvent abonn) {
        try {
            List<abonnement> abonnements = ab.chercherabonn(chercherabonnField.getText());
            gridabonn.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < abonnements.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("abonnement.fxml"));
                AnchorPane pane = loader.load();         
                //passage de parametres
                abonnementController controller = loader.getController();
                controller.setabonnement(abonnements.get(i));
                controller.setIdabonn(abonnements.get(i).getId_ab());
                gridabonn.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
                if(abonnements.get(i).getCode_promo()<=0)
                {
                 // ab.supprimerabonnement(abonnements.get(i));
                controller.arreterabonn();
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }   
    }

    @FXML
    private void mailingg(ActionEvent abonn) throws MessagingException, AddressException, IOException, URISyntaxException {
        
        String link = "https://mail.google.com/mail/u/0/?tab=cm&zx=6vpa7piztdtn#chat/space/AAAACkhBi5Q";
         Desktop.getDesktop().browse(new URI(link));
    }

    @FXML
    private void trierabonnement(ActionEvent abonn) throws SQLException {
        try {
            List<abonnement> abonnements = ab.trierabonn();
            gridabonn.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < abonnements.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("abonnement.fxml"));
                AnchorPane pane = loader.load();      
                //passage de parametres
                abonnementController controller = loader.getController();
                controller.setabonnement(abonnements.get(i));
                controller.setIdabonn(abonnements.get(i).getId_ab());
                gridabonn.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
                if(abonnements.get(i).getCode_promo()<=0)
                {
                 // ab.supprimerEvenement(abonnements.get(i));
                controller.arreterabonn();
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
 
    }
    
}
