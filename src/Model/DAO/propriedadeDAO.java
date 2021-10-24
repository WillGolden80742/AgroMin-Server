/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import ConnectionFactory.ConnectionFactory;
import Model.bean.Endereco;
import Model.bean.Propriedade;
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
 * @author William
 */
public class propriedadeDAO {

    public List<Propriedade> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Propriedade> propriedades = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM propriedades");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Propriedade p = new Propriedade();
                p.setPropriedadeId(rs.getInt("propriedadeId"));
                p.setCpnj(rs.getString("cpnj"));
                p.setNome(rs.getString("nome"));
                propriedades.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(contactsListDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return propriedades;
    }

    public Propriedade read(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Propriedade propriedades = new Propriedade();

        try {
            stmt = con.prepareStatement("SELECT * FROM propriedades where propriedades.propriedadeId ='" + id + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {

                propriedades.setPropriedadeId(rs.getInt("propriedadeId"));
                propriedades.setCpnj(rs.getString("cpnj"));
                propriedades.setNome(rs.getString("nome"));

                propriedades.setDestino(rs.getInt("destino"));
                propriedades.setNumeroEmpregados(rs.getInt("numeroEmpregados"));
                propriedades.setMaquinas(rs.getInt("maquinas"));
                propriedades.setNivelAutomacao(rs.getInt("nivelAutomacao"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(contactsListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stmt = con.prepareStatement("SELECT * FROM endereco where endereco.propriedadeId ='" + id + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Endereco endereco = new Endereco();
                endereco.setEndereco(rs.getString("endereco"));
                endereco.setReferencia(rs.getString("referencia"));
                endereco.setComplemento(rs.getString("complemento"));
                propriedades.setEndereco(endereco);
            }
        } catch (SQLException ex) {
            Logger.getLogger(contactsListDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return propriedades;
    }
}
