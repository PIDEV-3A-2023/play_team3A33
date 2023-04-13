/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.abonnement;
import services.abonnementService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class StatisticsController implements Initializable {

/**
 * FXML Controller class
 *
 * @author Theto
 */

    @FXML
    private ImageView GoBackBtn;
    @FXML
    private PieChart StatsChart;
    
    abonnementService rs = new abonnementService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            displayStatistics();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    


    public void displayStatistics() throws SQLException {
        // Récupérer toutes les réclamations de la base de données
        List<abonnement> circuit = rs.recupererabonnement();
        // Regrouper les réclamations par type
        Map<String, Long> circuitParTYPE= circuit.stream()
                .collect(Collectors.groupingBy(abonnement::getType_abonn, Collectors.counting()));
        // conversion reclamationsParType map a une list de objects PieChart.Data
        List<PieChart.Data> pieChartData = circuitParTYPE.entrySet().stream()
                .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        // Définissez les éléments de données dans le PieChart
        StatsChart.setData(FXCollections.observableArrayList(pieChartData));
        // Configurer l'animation pour les données piechart
        StatsChart.getData().forEach(data ->
                data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    ScaleTransition st = new ScaleTransition(Duration.millis(200), data.getNode());
                    st.setToX(1.1);
                    st.setToY(1.1);
                    st.play();
                })
        );
        StatsChart.getData().forEach(data ->
                data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                    ScaleTransition st = new ScaleTransition(Duration.millis(200), data.getNode());
                    st.setToX(1.0);
                    st.setToY(1.0);
                    st.play();
                })
        );
        // Configurer l'interactivité pour les données piechart
        StatsChart.getData().forEach(data ->
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    // Obtenir la valeur actuelle de l'élément data
                    long currentValue = (long) data.getPieValue();
                    
                    // Afficher un message avec la valeur de data
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Statistique Abonnement(s)");
                    alert.setHeaderText(data.getName());
                    alert.setContentText("Nombre d'Abonnement : " + currentValue);
                    alert.showAndWait();
                })
        );
}




    @FXML
    private void GoBk(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouterabonnement.fxml"));
            Parent root = loader.load();

            // Set the root of the current scene to the new FXML file
            GoBackBtn.getScene().setRoot(root);
    }
    
}
