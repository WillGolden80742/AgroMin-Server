/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import ConnectionFactory.ConnectionFactory;
import Model.bean.Agrotoxico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author will
 */
public class agroToxicoDAO {

    public List<Agrotoxico> read(int id) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Agrotoxico> agrotoxicos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT agrotoxico.agrotoxicoID, agrotoxico.nome, agrotoxico.ingredienteAtivo FROM propriedades INNER JOIN agrotoxicoPropriedade ON agrotoxicoPropriedade.propriedade = propriedades.propriedadeId INNER JOIN agrotoxico ON agrotoxico.agrotoxicoID = agrotoxicoPropriedade.agrotoxico where propriedades.propriedadeId ='" + id + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Agrotoxico a = new Agrotoxico();
                a.setAgroId(rs.getInt("agrotoxico.agrotoxicoID"));
                a.setNome(rs.getString("agrotoxico.nome"));
                a.setIngrediente(rs.getString("agrotoxico.ingredienteAtivo"));
                agrotoxicos.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(contactsListDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return agrotoxicos;
    }

    public List<Agrotoxico> read() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Agrotoxico> agrotoxicos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT agrotoxico.agrotoxicoID, agrotoxico.nome, agrotoxico.ingredienteAtivo FROM agrotoxico");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Agrotoxico a = new Agrotoxico();
                a.setAgroId(rs.getInt("agrotoxico.agrotoxicoID"));
                a.setNome(rs.getString("agrotoxico.nome"));
                a.setIngrediente(rs.getString("agrotoxico.ingredienteAtivo"));
                agrotoxicos.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(contactsListDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return agrotoxicos;
    }
}