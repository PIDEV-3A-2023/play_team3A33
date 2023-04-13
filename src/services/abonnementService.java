/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

//import com.sun.javafx.iio.ImageStorage.ImageType;
import entities.abonnement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import utils.MyDB;
import javafx.collections.ObservableList;

//**************//
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import static org.apache.poi.hssf.usermodel.HeaderFooter.date;

/**
 *
 * @author asus
 */
public class abonnementService implements IabonnementService<abonnement> {

    Connection cnx;
    public Statement ste;
    public PreparedStatement pst;

    public abonnementService() {
        cnx = MyDB.getInstance().getCnx();

    }

    @Override
    public void ajouterabonnement(abonnement e) throws SQLException {

        String requete = "INSERT INTO `abonnement` (`nom_abonn`,`type_abonn`,`image_abonn`,`description_abonn`,`date`,`code_promo`) "
                + "VALUES (?,?,?,?,?,?);";
        try {
            pst = (PreparedStatement) cnx.prepareStatement(requete);
            pst.setString(1, e.getNom_abonn());
            pst.setString(2, e.getType_abonn());
            pst.setString(3, e.getImage_abonn());
            pst.setString(4, e.getDescription_abonn());
            pst.setDate(5, e.getDate());
            pst.setInt(6, e.getCode_promo());
            pst.executeUpdate();
            System.out.println("abonn " + e.getNom_abonn() + " added successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifierabonnement(abonnement e) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String req = "UPDATE abonnement SET nom_abonn = ?,type_abonn = ?,image_abonn=?,description_abonn = ?,date=?,code_promo=? where id_ab = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, e.getNom_abonn());
        ps.setString(2, e.getType_abonn());
        ps.setString(3, e.getImage_abonn());
        ps.setString(4, e.getDescription_abonn());
        ps.setDate(5, e.getDate());
        ps.setInt(6, e.getCode_promo());
        ps.setInt(7, e.getId_ab());
        ps.executeUpdate();
    }

    @Override
    public void supprimerabonnement(abonnement e) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String req = "delete from abonnement where id_ab = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, e.getId_ab());
        ps.executeUpdate();
        System.out.println("abonn with id= " + e.getId_ab() + "  is deleted successfully");
    }

    @Override
    public List<abonnement> recupererabonnement() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        List<abonnement> abonnements = new ArrayList<>();
        String s = "select * from abonnement";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            abonnement e = new abonnement();
            e.setNom_abonn(rs.getString("nom_abonn"));
            e.setType_abonn(rs.getString("type_abonn"));
            e.setImage_abonn(rs.getString("Image_abonn"));
            e.setDescription_abonn(rs.getString("description_abonn"));
            e.setDate(rs.getDate("date"));
            e.setCode_promo(rs.getInt("code_promo"));
            e.setId_ab(rs.getInt("id_ab"));

            abonnements.add(e);

        }
        return abonnements;
    }

    public abonnement FetchOneabonn(int id) {
        abonnement abonn = new abonnement();
        String requete = "SELECT * FROM `abonnement` where id_ab = " + id;

        try {
            ste = (Statement) cnx.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            while (rs.next()) {

                abonn = new abonnement(rs.getInt("id_ab"), rs.getInt("code_promo"), rs.getString("nom_abonn"), rs.getString("type_abonn"), rs.getString("image_abonn"), rs.getString("description_abonn"), rs.getDate("date"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(abonnementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return abonn;
    }

    public ObservableList<abonnement> Fetchabonns() {
        ObservableList<abonnement> abonns = FXCollections.observableArrayList();
        String requete = "SELECT * FROM `abonnement`";
        try {
            ste = (Statement) cnx.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            while (rs.next()) {
                abonns.add(new abonnement(rs.getInt("id_ab"), rs.getInt("code_promo"), rs.getString("nom_abonn"), rs.getString("type_abonn"), rs.getString("image_abonn"), rs.getString("description_abonn"), rs.getDate("date")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(abonnementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return abonns;
    }

    public String GenerateQrabonn(abonnement abonn) throws FileNotFoundException, IOException {
        String abonnName = "abonn name: " + abonn.getNom_abonn() + "\n" + "abonn date: " + abonn.getDate() + "\n" + "abonn description: " + abonn.getDescription_abonn() + "\n";
        ByteArrayOutputStream out = QRCode.from(abonnName).to(ImageType.JPG).stream();
        String filename = abonn.getNom_abonn() + "_QrCode.jpg";
        //File f = new File("src\\utils\\img\\" + filename);
                File f = new File("C:\\xampp\\htdocs\\xchangex\\imgQr\\qrcode" + filename);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(out.toByteArray());
        fos.flush();
       
        System.out.println("qr yemshi");
        return filename;
    }
    

    public ObservableList<abonnement> chercherabonn(String chaine) {
        String sql = "SELECT * FROM abonnement WHERE (nom_abonn LIKE ? or type_abonn LIKE ?  ) order by nom_abonn ";
        //Connection cnx= Maconnexion.getInstance().getCnx();
        String ch = "%" + chaine + "%";
        ObservableList<abonnement> myList = FXCollections.observableArrayList();
        try {

            Statement ste = cnx.createStatement();
            // PreparedStatement pst = myCNX.getCnx().prepareStatement(requete6);
            PreparedStatement stee = cnx.prepareStatement(sql);
            stee.setString(1, ch);
            stee.setString(2, ch);

            ResultSet rs = stee.executeQuery();
            while (rs.next()) {
                abonnement e = new abonnement();

                e.setNom_abonn(rs.getString("nom_abonn"));
                e.setType_abonn(rs.getString("type_abonn"));
                e.setImage_abonn(rs.getString("Image_abonn"));
                e.setDescription_abonn(rs.getString("description_abonn"));
                e.setDate(rs.getDate("date"));
                e.setCode_promo(rs.getInt("code_promo"));
                e.setId_ab(rs.getInt("id_ab"));

                myList.add(e);
                System.out.println("abonn trouvé! ");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List<abonnement> trierabonn()throws SQLException {
        List<abonnement> abonnements = new ArrayList<>();
        String s = "select * from abonnement order by nom_abonn ";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            abonnement e = new abonnement();
            e.setNom_abonn(rs.getString("nom_abonn"));
            e.setType_abonn(rs.getString("type_abonn"));
            e.setImage_abonn(rs.getString("Image_abonn"));
            e.setDescription_abonn(rs.getString("description_abonn"));
            e.setDate(rs.getDate("date"));
            e.setCode_promo(rs.getInt("code_promo"));
            e.setId_ab(rs.getInt("id_ab"));
            abonnements.add(e);
        }
        return abonnements;
    }
   

}
