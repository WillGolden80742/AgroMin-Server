/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import ConnectionFactory.ConnectionFactory;
import Model.bean.Cliente;
import Model.bean.Device;
import Model.bean.ProfilePic;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author William
 */
public class clientDAO {

    public String[] authenticated(String nickName, String password) {
        String[] reply = {"Nickname ou senha errada!", ""};
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT count(nickName) as result, level FROM clientes WHERE nickName='" + nickName + "' AND senha = '" + password + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("result") == 1) {
                    reply[0] = "OK";
                    reply[1] = String.valueOf(rs.getInt("level"));
                    System.out.println("Autenticado");
                }
            }
        } catch (MySQLSyntaxErrorException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return reply;
    }

    public String[] biometricAuthenticated(String deviceID) {
        String[] reply = {"Celular não cadastrado!", "", "", ""};
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT count(nickName) as result, nickName, nomeCliente, level FROM clientes WHERE deviceID ='" + deviceID + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("result") == 1) {
                    reply[0] = "OK";
                    reply[1] = rs.getString("nickName");
                    reply[2] = "Bem vindo, " + rs.getString("nomeCliente") + "!";
                    reply[3] = String.valueOf(rs.getString("level"));
                    System.out.println("Autenticado");
                }
            }
        } catch (MySQLSyntaxErrorException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return reply;
    }

    public int level(String nickName) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs;
        int level = 0;
        try {
            stmt = con.prepareStatement("SELECT level FROM clientes WHERE clientes.nickName LIKE '" + nickName + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                level = rs.getInt("level");
            }
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return level;
    }

    public int checkClient(String nickName) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs;
        int count = 0;
        try {
            stmt = con.prepareStatement("SELECT COUNT(clientes.nickName) AS checkNickName  FROM clientes WHERE clientes.nickName LIKE '" + nickName + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("checkNickName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return count;
    }

    public int checkDevice(String deviceID) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs;
        int count = 0;
        try {
            stmt = con.prepareStatement("SELECT COUNT(clientes.deviceID) AS deviceID  FROM clientes WHERE clientes.deviceID LIKE '" + deviceID + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("deviceID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return count;
    }

    public String createAccount(byte[] picture, String format, String name, String nickName, String password, String deviceID) {
        String reply;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO clientes (clientes.nomeCliente,clientes.nickName,clientes.senha,clientes.deviceID) VALUES (?,?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, nickName);
            stmt.setString(3, password);
            stmt.setString(4, deviceID);
            stmt.executeUpdate();
            reply = "OK";
        } catch (NullPointerException ex) {
            reply = ex.toString();
        } catch (MySQLSyntaxErrorException ex) {
            reply = ex.toString();
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            reply = ex.toString();
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            stmt = con.prepareStatement("INSERT INTO profilepicture (profilepicture.clienteId,profilepicture.picture,profilepicture.format) VALUES (?,?,?)");
            stmt.setString(1, nickName);
            stmt.setBytes(2, picture);
            stmt.setString(3, format);
            stmt.executeUpdate();
        } catch (SQLException | NullPointerException ex) {
            System.out.println("Sem envio de imagem");
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return reply;
    }

    public String editAccount(byte[] picture, String format, String name, String nickName, String deviceID, String password) {
        String reply;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE clientes SET nomeCliente = ? WHERE nickName = ?");
            stmt.setString(1, name);
            stmt.setString(2, nickName);
            stmt.executeUpdate();
            reply = "NOME ATUALIZADO!";
        } catch (NullPointerException ex) {
            reply = ex.toString();
        } catch (MySQLSyntaxErrorException ex) {
            reply = ex.toString();
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            reply = ex.toString();
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            stmt = con.prepareStatement("UPDATE clientes SET deviceID = ? WHERE nickName = ?");
            stmt.setString(1, deviceID);
            stmt.setString(2, nickName);
            stmt.executeUpdate();
            reply += "\nDISPOSITVO CADASTRADO!";
        } catch (MySQLSyntaxErrorException | NullPointerException ex) {
            System.out.print("Sem cadastro de dispositivo");
        } catch (SQLException ex) {
            System.out.print("Sem cadastro de dispositivo");
        }
        try {
            stmt = con.prepareStatement("UPDATE clientes SET senha = ? WHERE nickName = ?");
            stmt.setString(1, password);
            stmt.setString(2, nickName);
            stmt.executeUpdate();
            reply += "\nSENHA ATUALIZADA!";
        } catch (MySQLSyntaxErrorException | NullPointerException ex) {
            System.out.print("Sem troca de senha");
        } catch (SQLException ex) {
            System.out.print("Sem troca de senha");
        }
        int count = 2;
        try {
            ResultSet rs;
            stmt = con.prepareStatement("SELECT count(profilepicture.clienteId) as countPic FROM profilepicture WHERE profilepicture.clienteId = '" + nickName + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("countPic");
            }
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (count == 0) {
            try {
                stmt = con.prepareStatement("INSERT INTO profilepicture (profilepicture.clienteId,profilepicture.picture,profilepicture.format) VALUES (?,?,?)");
                stmt.setString(1, nickName);
                stmt.setBytes(2, picture);
                stmt.setString(3, format);
                stmt.executeUpdate();
                reply += "\nFOTO INCLUÍDA!";
            } catch (SQLException | NullPointerException ex) {
                System.out.print("Sem envio de imagem");
            } finally {
                ConnectionFactory.closeConnection(con, stmt);
            }
        } else if (count == 1) {
            try {
                stmt = con.prepareStatement("UPDATE profilepicture SET picture = ?, format = ? WHERE clienteId = ?");
                stmt.setBytes(1, picture);
                stmt.setString(2, format);
                stmt.setString(3, nickName);
                stmt.executeUpdate();
                reply += "\nFOTO ATUALIZADA!";
            } catch (SQLException | NullPointerException ex) {
                System.out.print("Sem envio de imagem");
            } finally {
                ConnectionFactory.closeConnection(con, stmt);
            }
        }
        return reply;
    }

    public ProfilePic profilePic(String nickName) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ProfilePic profilePic = new ProfilePic();
        try {
            stmt = con.prepareStatement("SELECT * FROM `profilepicture` INNER JOIN clientes on clientes.nickName = profilepicture.clienteId WHERE clientes.nickName LIKE '" + nickName + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                profilePic.setPicture(rs.getBytes("picture"));
                profilePic.setFormat(rs.getString("format"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return profilePic;
    }

    public String biometricAuthenticated(String string, String string0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Cliente> read() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs;
        List<Cliente> clientes = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM clientes");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setNickName(rs.getString("nickName"));
                c.setNivel(rs.getInt("level"));
                clientes.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return clientes;
    }

    public void editLevel(String nickName, int level) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE clientes SET level = ? WHERE nickName = ?");
            stmt.setInt(1, level);
            stmt.setString(2, nickName);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Nível alterado com sucesso!");
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null,"Erro : "+ex.toString()); 
        } catch (MySQLSyntaxErrorException ex) {
            JOptionPane.showMessageDialog(null,"Erro : "+ex.toString()); 
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro : "+ex.toString()); 
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cliente search(String nickName) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente contact = new Cliente();
        try {
            stmt = con.prepareStatement("SELECT * FROM clientes WHERE clientes.nickName LIKE '" + nickName + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Device d = new Device();
                contact.setNickName(rs.getString("nickName"));
                contact.setNome(rs.getString("nomeCliente"));
                d.setDeviceID(rs.getString("deviceID"));
                contact.setDevice(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return contact;
    }

    public List<Cliente> read(String nickName) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();
        try {
            stmt = con.prepareStatement("call contatos('" + nickName + "')");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setNickName(rs.getString("nickNameContato"));
                c.setNome(rs.getString("contato"));
                clientes.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return clientes;
    }
}
