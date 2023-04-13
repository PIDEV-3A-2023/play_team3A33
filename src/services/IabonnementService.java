/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.File;
import static java.nio.file.Files.list;
import static java.util.Collections.list;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author asus
 */
public interface IabonnementService<T> {
    
       public void ajouterabonnement(T t) throws SQLException;
    public void modifierabonnement(T t) throws SQLException;
    public void supprimerabonnement(T t) throws SQLException;
    public List<T> recupererabonnement() throws SQLException;
    
}
