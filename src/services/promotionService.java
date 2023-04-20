/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.promotion;
import entities.abonnement;
import entities.UserDetails;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDB;

/**
 *
 * @author asus
 */
public class promotionService {

    Connection cnx;
    public Statement ste;
    public PreparedStatement pst;

    public promotionService() {

        cnx = MyDB.getInstance().getCnx();
    }

    public void ajouterpromotion(promotion p) {
        UserDetails U = new UserDetails();
        abonnementService es = new abonnementService();
        String requete = "INSERT INTO `promotion` (`date_part`,`id_abonnement` ,`id_user`) VALUES(? ,?,?) ;";

        try {
            abonnement tempabonn = es.FetchOneabonn(p.getId_abonnement());
            System.out.println("before" + tempabonn);
            tempabonn.setCode_promo(tempabonn.getCode_promo() - 1);
            tempabonn.setCode_promo(Math.max(tempabonn.getCode_promo() - 1, 0));
            es.modifierabonnement(tempabonn);
            int new_id = tempabonn.getId_ab();
            p.setAbonnement(tempabonn);
            System.out.println("after" + tempabonn);

            pst = (PreparedStatement) cnx.prepareStatement(requete);
            pst.setDate(1, p.getDate_part());
            pst.setInt(2, p.getId_abonnement());
            pst.setInt(3, p.getId_user());
            pst.executeUpdate();
          

            System.out.println("promotion with id abonn = " + p.getId_abonnement() + " is added successfully");

        } catch (SQLException ex) {
            System.out.println("error in adding reservation");
            Logger.getLogger(promotionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<promotion> recupererPromotion() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        List<promotion> particip = new ArrayList<>();
        String s = "select * from promotion";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            promotion pa = new promotion();
            pa.setId_promo(rs.getInt("id_promo"));
            pa.setId_user(rs.getInt("id_user"));
            pa.setId_abonnement(rs.getInt("id_abonnement"));
            pa.setDate_part(rs.getDate("date_part"));

            particip.add(pa);

        }
        return particip;
    }

    public void supprimerpromotion(promotion p) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String req = "delete from promotion where id_promo  = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, p.getId_promo());
        ps.executeUpdate();
        System.out.println("promotion with id= " + p.getId_promo() + "  is deleted successfully");
    }

    public promotion FetchOneRes(int id) throws SQLException {
        promotion r = new promotion();
        String requete = "SELECT * FROM `promotion` where id_promo=" + id;

        try {
            ste = (Statement) cnx.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            while (rs.next()) {

                r = new promotion(rs.getInt("id_promo"), rs.getDate("date_part"), rs.getInt("id_user"), rs.getInt("id_abonnement"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(abonnementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public void Deletepromotion(promotion p) throws SQLException {
        abonnementService es = new abonnementService();
        promotionService rs = new promotionService();

        promotion r = rs.FetchOneRes(p.getId_promo());

        String requete = "delete from promotion where id_promo=" + p.getId_promo();
        try {
            abonnement tempabonn = es.FetchOneabonn(r.getId_abonnement());
            System.out.println("before" + tempabonn);
            tempabonn.setCode_promo(tempabonn.getCode_promo() + 1);
            es.modifierabonnement(tempabonn);
            System.out.println("after" + tempabonn);
            pst = (PreparedStatement) cnx.prepareStatement(requete);
            //pst.setInt(1, id);

            pst.executeUpdate();
            System.out.println("promotion with id=" + p.getId_promo() + " is deleted successfully");
        } catch (SQLException ex) {
            System.out.println("error in delete promotion " + ex.getMessage());
        }
    }
    
    public void modifierpromotion(promotion p) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String req = "UPDATE promotion SET id_user = ?,id_abonnement = ?,date_part=? where id_promo = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, p.getId_user());
        ps.setInt(2, p.getId_abonnement());
        ps.setDate(3, p.getDate_part());
        ps.setInt(4, p.getId_promo());

        ps.executeUpdate();
    }

}
