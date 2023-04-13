/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entities.abonnement;
import entities.promotion;
import java.sql.Date;
import java.sql.SQLException;
import services.abonnementService;
import services.promotionService;


/**
 *
 * @author asus
 */
public class test {
    
      public static void main(String[] args) {   
          
          Date d=Date.valueOf("2022-06-11");
          Date d1=Date.valueOf("2020-04-12");
        try {
            //kifeh ya9ra el orde fel base de donnée , kifeh 3raf nom abonn bch n3amarha f nom 
            abonnement e = new abonnement(2,10,"nom21", "type21","image21.png","description21",d1);
            abonnement e1 = new abonnement(5,"nom3", "type3","image3.png","description3",d);
            abonnement e2 = new abonnement(6,"nom4", "type4","image4.png","description4",d);
            abonnement e3 = new abonnement(5,8,"nom5", "type5","image5.png","description5",d);
            
            
            promotion p=new promotion(d,1,2);
            promotion p1=new promotion(d1,3,4);
            promotion p2=new promotion(27,d1,55,45);

            promotionService ps=new promotionService();
            //ps.promotion(p);
          //  ps.promotion(p1);
           // ps.promotion(p2);
            ps.modifierpromotion(p2);
            //ps.promotion(p2);
            System.out.println("");
            abonnementService ab = new abonnementService();
            //ab.ajouterabonnement(e1);
            //ab.ajouterabonnement(e2);
           // ab.ajouterabonnement(e3);
            //ab.ajouter(p);
            //ab.modifierabonnement(e);
            //ab.supprimerabonnement(e3);
            System.out.println(ab.recupererabonnement());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
