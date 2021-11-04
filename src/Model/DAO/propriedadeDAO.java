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

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Propriedade> read(String nome, String cnpj) {

        if (!nome.equals("") && !cnpj.equals("")) {
            nome = "where propriedades.nome like '%" + nome + "%' and propriedades.cpnj like '%" + cnpj + "%'";
            cnpj = "";
        } else if (!nome.equals("")) {
            nome = "where propriedades.nome like '%" + nome + "%'";
        } else if (!cnpj.equals("")) {
            cnpj = "where propriedades.cpnj like '%" + cnpj + "%'";
        }

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Propriedade> propriedades = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM propriedades " + nome + cnpj);
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

    public List<Propriedade> read() {
        return read("", "");
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

    public String enderecoEdit(Endereco end, int id) {
        deleteEnd(id);
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO endereco (propriedadeId,endereco,complemento,referencia) VALUES (?,?,?,?)");
            stmt.setInt(1, id);
            stmt.setString(2, end.getEndereco());
            stmt.setString(3, end.getComplemento());
            stmt.setString(4, end.getReferencia());
            stmt.executeUpdate();
            return "Endereço editado com sucesso!";
        } catch (SQLException ex) {
            Logger.getLogger(contactsListDAO.class.getName()).log(Level.SEVERE, null, ex);
            setStatus(ex.toString());
            return "Erro";
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public String propriedadeEdit(Propriedade prop) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE propriedades SET  nome = ?,destino =?, numeroEmpregados = ? , maquinas = ? , nivelAutomacao = ? WHERE propriedadeId = ?");
            stmt.setString(1, prop.getNome());
            stmt.setInt(2, prop.getDestino());
            stmt.setInt(3, prop.getNumeroEmpregados());
            stmt.setInt(4, prop.getMaquinas());
            stmt.setInt(5, prop.getNivelAutomacao());
            stmt.setInt(6, prop.getPropriedadeId());
            stmt.executeUpdate();
            return "Propriedade editado com sucesso!";
        } catch (SQLException ex) {
            Logger.getLogger(contactsListDAO.class.getName()).log(Level.SEVERE, null, ex);
            setStatus(ex.toString());
            return "Erro";
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void deleteEnd(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM endereco WHERE propriedadeId = '" + id + "'");
            setStatus("SUCCESSFULL");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(contactsListDAO.class.getName()).log(Level.SEVERE, null, ex);
            setStatus(ex.toString());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public String delete(int id) {
        String delete;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM propriedades WHERE propriedadeId = '" + id + "'");
            stmt.executeUpdate();
            delete = "Propriedade deletado com sucesso!";
        } catch (SQLException ex) {
            Logger.getLogger(contactsListDAO.class.getName()).log(Level.SEVERE, null, ex);
            delete = "Erro!";
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return delete;
    }

}
