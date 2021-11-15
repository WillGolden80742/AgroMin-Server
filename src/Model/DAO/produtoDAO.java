/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import ConnectionFactory.ConnectionFactory;
import Model.bean.Produto;
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
public class produtoDAO {

    public List<Produto> read(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT produto.nome, produto.producaoAnual FROM produto where produto.propriedadeId ='" + id + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setNome(rs.getString("produto.nome"));
                p.setProducaoAnual(rs.getDouble("produto.producaoAnual"));
                produtos.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(produtoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return produtos;
    }

    public String prodEdit(List<Produto> prod, int id) {
        String retunEdit = "";
        delete(id);
        for (Produto p : prod) {
            if (p.getProducaoAnual() > 0) {
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement stmt = null;
                try {
                    stmt = con.prepareStatement("INSERT INTO produto (nome,producaoAnual,propriedadeId) VALUES (?,?,?)");
                    stmt.setString(1, p.getNome());
                    stmt.setDouble(2, p.getProducaoAnual());
                    stmt.setDouble(3, id);
                    stmt.executeUpdate();
                    retunEdit = "Produto editado com sucesso!";
                } catch (SQLException ex) {
                    Logger.getLogger(produtoDAO.class.getName()).log(Level.SEVERE, null, ex);
                    retunEdit = "Erro";
                } finally {
                    ConnectionFactory.closeConnection(con, stmt);
                }
            }
        }
        return retunEdit;
    }

    public void delete(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM produto WHERE propriedadeId = '" + id + "'");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(produtoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
