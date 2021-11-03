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

    public List<Imposto> read(String nome, String cnpj) {

//        if (!nome.equals("") && !cnpj.equals("")) {
//            nome = "where propriedades.nome like '%" + nome + "%' and propriedades.cpnj like '%" + cnpj + "%'";
//            cnpj = "";
//        } else if (!nome.equals("")) {
//            nome = "where propriedades.nome like '%" + nome + "%'";
//        } else if (!cnpj.equals("")) {
//            cnpj = "where propriedades.cpnj like '%" + cnpj + "%'";
//        }

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Imposto> impostos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT tipo.nome, tipo.tipo, subsidio, valor, pago, lancamento FROM impostos INNER JOIN tipo on tipo.tipoID = impostos.tipo");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Imposto i = new Imposto();
                i.setNome(rs.getString("tipo.nome"));
                i.setTipo(rs.getString("tipo.tipo"));
                i.setSubsidio(rs.getInt("subsidio"));
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

    public List<Imposto> read() {
        return read("", "");
    }
}
