/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.abonnement;
import entities.promotion;
import java.io.IOException;
import services.promotionService;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
    import services.abonnementService;



/**
 * FXML Controller class
 *
 * @author asus
 */
public class AfficherpromotionController implements Initializable {

    @FXML
    private TableView<promotion> tablepromotion;
     abonnementService ab=new abonnementService();
    @FXML
    private TableColumn<promotion, Integer> iduserTv;
    @FXML
    private TableColumn<promotion, Integer> idabonnTv;
    @FXML
    private TableColumn<promotion, Date> datePartTv;
    @FXML
    private Button modifierPartBtn;
    @FXML
    private Button supprimerPartBtn;
     @FXML
    private Button ajouter;
    @FXML
    private TextField idread;
    @FXML
    private TextField iduserField;
    @FXML
    private TextField idabonnField;
    @FXML
    private DatePicker datepartField;
    @FXML
    private TextField chercherabonnField;
    
    promotionService Ps=new promotionService();
    @FXML
    private TextField datepartField1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        getPromotion();
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
    @FXML
    private void rechercherabonnement(KeyEvent abonn) {
        try {
            List<abonnement> abonnements = ab.chercherabonn(chercherabonnField.getText());
            
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
    private void modifierpromotion(ActionEvent abonn) throws SQLException {
        
         promotion pa = new promotion();
        pa.setId_promo(Integer.valueOf(idread.getText()));
        pa.setId_abonnement(Integer.valueOf(idabonnField.getText()));
        pa.setId_user(Integer.valueOf(iduserField.getText()));
            Date d=Date.valueOf(datepartField.getValue());
        pa.setDate_part(d);
        //pa.setDate_part(datepartField.getText());
       
        Ps.modifierpromotion(pa);
        resetPart();
        getPromotion();
           
        
    }

    @FXML
    private void supprimerpromotion(ActionEvent abonn) {
         promotion p = tablepromotion.getItems().get(tablepromotion.getSelectionModel().getSelectedIndex());
      
        try {
            Ps.Deletepromotion(p);
        } catch (SQLException ex) {
            Logger.getLogger(AjouterabonnementController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("promotion delete");
        alert.setContentText("promotion deleted successfully!");
        alert.showAndWait();
        getPromotion();
     
    }

    @FXML
    private void choisirpromotion(ActionEvent abonn) {
        promotion part = tablepromotion.getItems().get(tablepromotion.getSelectionModel().getSelectedIndex());
        
        idread.setText(String.valueOf(part.getId_promo()));
        idabonnField.setText(String.valueOf(part.getId_abonnement()));
        iduserField.setText(String.valueOf(part.getId_user())); 
        datepartField1.setText(String.valueOf(part.getDate_part()));
        //datepartField.setValue((part.getDate_part()));
        
    }
    
    
    public void getPromotion(){
        try {
       

           // TODO
            List<promotion> part = Ps.recupererPromotion();
            ObservableList<promotion> olp = FXCollections.observableArrayList(part);
            tablepromotion.setItems(olp);
            iduserTv.setCellValueFactory(new PropertyValueFactory("id_user"));
            idabonnTv.setCellValueFactory(new PropertyValueFactory("id_abonnement"));
            datePartTv.setCellValueFactory(new PropertyValueFactory("date_part"));
            // this.delete();
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }
    
    public void resetPart() {
        idread.setText("");
        idabonnField.setText("");
        iduserField.setText("");
        datepartField.setValue(null);
        
    }
   
    
}


 