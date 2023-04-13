/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;

/**
 *
 * @author asus
 */
public class abonnement {

   
        private int id_ab;
        private int code_promo;
    private String nom_abonn,type_abonn,image_abonn,description_abonn;
    private Date date;

    public abonnement() {
    }

    public abonnement(int id_ab,int code_promo, String nom_abonn, String type_abonn, String image_abonn, String description_abonn, Date date) {
        this.id_ab = id_ab;
        this.code_promo=code_promo;
        this.nom_abonn = nom_abonn;
        this.type_abonn = type_abonn;
        this.image_abonn = image_abonn;
        this.description_abonn = description_abonn;
        this.date = date;
    }
    public abonnement(int code_promo, String nom_abonn, String type_abonn, String image_abonn, String description_abonn, Date date) {
        this.code_promo=code_promo;
        this.nom_abonn = nom_abonn;
        this.type_abonn = type_abonn;
        this.image_abonn = image_abonn;
        this.description_abonn = description_abonn;
        this.date = date;
    }
    
    
     public abonnement(int id_ab,int code_promo, String nom_abonn, String type_abonn, String image_abonn, String description_abonn) {
        this.id_ab = id_ab;
        this.code_promo=code_promo;
        this.nom_abonn = nom_abonn;
        this.type_abonn = type_abonn;
        this.image_abonn = image_abonn;
        this.description_abonn = description_abonn;
        
    }
    
    
     //****************** getters ****************

    public int getId_ab() {
        return id_ab;
    }

    public String getNom_abonn() {
        return nom_abonn;
    }

    public String getType_abonn() {
        return type_abonn;
    }

    public String getImage_abonn() {
        return image_abonn;
    }

    public String getDescription_abonn() {
        return description_abonn;
    }

    public Date getDate() {
        return date;
    }
    
    
    //****************** setters ****************

    public void setId_ab(int id_ab) {
        this.id_ab = id_ab;
    }

    public void setNom_abonn(String nom_abonn) {
        this.nom_abonn = nom_abonn;
    }

    public void setType_abonn(String type_abonn) {
        this.type_abonn = type_abonn;
    }

    public void setImage_abonn(String image_abonn) {
        this.image_abonn = image_abonn;
    }

    public void setDescription_abonn(String description_abonn) {
        this.description_abonn = description_abonn;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCode_promo() {
        return code_promo;
    }

    public void setCode_promo(int code_promo) {
        this.code_promo = code_promo;
    }
    
    

    @Override
    public String toString() {
        return "abonnement{" + "id_ab=" + id_ab+ ", code_promo=" + code_promo  + ", nom_abonn=" + nom_abonn + ", type_abonn=" + type_abonn + ", image_abonn=" + image_abonn + ", description_abonn=" + description_abonn + ", date=" + date + '}';
    }
    
    
    
    
    
    
}
