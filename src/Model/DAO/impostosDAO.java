package Model.DAO;

import ConnectionFactory.ConnectionFactory;
import Model.bean.Imposto;
import Model.bean.Tipo;
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
public class impostosDAO {

    public List<Imposto> read(int id) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Imposto> impostos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT impostos.impostoID, tipo.tipoID, tipo.nome, tipo.tipo, subsidio, valor, pago, lancamento FROM impostos INNER JOIN tipo on tipo.tipoID = impostos.tipo where propriedadeId ='" + id + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Tipo t = new Tipo();
                t.setTipoId(rs.getInt("tipo.tipoID"));
                t.setNome(rs.getString("tipo.nome"));
                t.setTipo(rs.getString("tipo.tipo"));

                Imposto i = new Imposto();
                i.setId(rs.getInt("impostos.impostoID"));
                i.setSubsidio(rs.getDouble("subsidio"));
                i.setValorBruto(rs.getDouble("valor"));
                i.setTipo(t);
                i.setPago(rs.getInt("pago"));
                i.setLancamento(rs.getString("lancamento"));
                impostos.add(i);
            }
        } catch (SQLException ex) {
            Logger.getLogger(impostosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return impostos;
    }

    public List<Tipo> readTipo() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Tipo> tipos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT tipo.tipoID, tipo.nome, tipo.tipo FROM tipo");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Tipo t = new Tipo();
                t.setTipoId(rs.getInt("tipo.tipoID"));
                t.setNome(rs.getString("tipo.nome"));
                t.setTipo(rs.getString("tipo.tipo"));
                tipos.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(impostosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return tipos;
    }

    public String impostoEdit(List<Imposto> imp, int id) {
        String retunEdit = "";
        for (Imposto i : imp) {
            if (i.getId() != 0) {
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement stmt = null;
                try {
                    stmt = con.prepareStatement("UPDATE impostos SET  valor = ?, subsidio = ?, pago = ?, tipo = ? WHERE impostoID = ?");
                    stmt.setDouble(1, i.getValorBruto());
                    stmt.setDouble(2, i.getSubsidio());
                    stmt.setDouble(3, i.getPago());
                    stmt.setDouble(4, i.getTipo().getTipoId());
                    stmt.setInt(5, i.getId());
                    stmt.executeUpdate();
                    retunEdit = "Imposto editado com sucesso!\n";
                    System.out.println(retunEdit + " valor : " + i.getValorBruto() + " impostoID : " + i.getId());
                } catch (SQLException ex) {
                    Logger.getLogger(impostosDAO.class.getName()).log(Level.SEVERE, null, ex);
                    retunEdit = "Erro";
                } finally {
                    ConnectionFactory.closeConnection(con, stmt);
                }
            } else if (i.getValorBruto() > 0) {
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement stmt = null;
                try {
                    stmt = con.prepareStatement("INSERT INTO impostos (valor,subsidio,pago,tipo,propriedadeId) VALUES (?,?,?,?,?)");
                    stmt.setDouble(1, i.getValorBruto());
                    stmt.setDouble(2, i.getSubsidio());
                    stmt.setDouble(3, i.getPago());
                    stmt.setDouble(4, i.getTipo().getTipoId());
                    stmt.setInt(5, id);
                    stmt.executeUpdate();
                    retunEdit += "Imposto criado com sucesso!";
                } catch (SQLException ex) {
                    Logger.getLogger(impostosDAO.class.getName()).log(Level.SEVERE, null, ex);
                    retunEdit = "Erro";
                } finally {
                    ConnectionFactory.closeConnection(con, stmt);
                }
            }
        }
        return retunEdit;
    }

    public double total(int id) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        double total = 0;

        try {
            stmt = con.prepareStatement("SELECT SUM(impostos.valor) as Total FROM impostos where propriedadeId ='" + id + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                total = rs.getDouble("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(impostosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return total;
    }
}
