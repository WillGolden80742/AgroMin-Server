package Model.DAO;

import ConnectionFactory.ConnectionFactory;
import Model.bean.Imposto;
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
            stmt = con.prepareStatement("SELECT impostos.impostoID, tipo.nome, tipo.tipo, subsidio, valor, pago, lancamento FROM impostos INNER JOIN tipo on tipo.tipoID = impostos.tipo where propriedadeId ='" + id + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Imposto i = new Imposto();
                i.setId(rs.getInt("impostos.impostoID"));
                i.setNome(rs.getString("tipo.nome"));
                i.setTipo(rs.getString("tipo.tipo"));
                i.setSubsidio(rs.getDouble("subsidio"));
                i.setValorBruto(rs.getDouble("valor"));
                i.setPago(rs.getInt("pago"));
                i.setLancamento(rs.getString("lancamento"));
                impostos.add(i);
            }
        } catch (SQLException ex) {
            Logger.getLogger(contactsListDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return impostos;
    }

    public String impostoEdit(List<Imposto> imp) {
        String retunEdit = "";
        for (Imposto i : imp) {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            try {
                stmt = con.prepareStatement("UPDATE impostos SET  valor = ?, subsidio = ?, pago = ? WHERE impostoID = ?");
                stmt.setDouble(1, i.getValorBruto());
                stmt.setDouble(2, i.getSubsidio());
                stmt.setDouble(3, i.getPago());
                stmt.setInt(4, i.getId());
                stmt.executeUpdate();
                retunEdit = "Imposto editado com sucesso!";
                System.out.println(retunEdit + " impostoID : " + i.getId());
            } catch (SQLException ex) {
                Logger.getLogger(contactsListDAO.class.getName()).log(Level.SEVERE, null, ex);
                retunEdit = "Erro";
            } finally {
                ConnectionFactory.closeConnection(con, stmt);
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
            Logger.getLogger(contactsListDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return total;
    }
}
