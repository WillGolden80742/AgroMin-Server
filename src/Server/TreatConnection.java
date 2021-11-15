/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Model.DAO.agroToxicoDAO;
import Model.DAO.arquivoDAO;
import Model.DAO.clientDAO;
import Model.DAO.contactsListDAO;
import Model.DAO.impostosDAO;
import Model.DAO.produtoDAO;
import Model.DAO.propriedadeDAO;
import Model.bean.Agrotoxico;
import Model.bean.Endereco;
import Model.bean.Imposto;
import Model.bean.Produto;
import Model.bean.Propriedade;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Communication;
import util.States;

/**
 *
 * @author William
 */
public class TreatConnection implements Runnable {

    private Socket socket;
    private States states = States.CONNECTED;
    private clientDAO cliDAO;

    public TreatConnection(Socket socket) {
        this.socket = socket;
    }

    public void treatConnection(Socket socket) throws IOException, ClassNotFoundException {

        try {

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outPut = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("Tratando...");

            while (states != States.EXIT) {
                Communication communication = (Communication) input.readObject();
                String operation = communication.getOperation();
                Communication reply = null;
                switch (states) {
                    case CONNECTED:
                        reply = executeOperation(operation, communication);
                        if (String.valueOf(reply.getParam("LOGINREPLY")).equals("OK")) {
                            this.states = states.AUTHENTICATED;
                            System.out.println("Autenticado!!");
                        }
                        break;
                    case AUTHENTICATED:
                        System.out.println("LOGADO!");
                        reply = executeOperation(operation, communication);
                        break;
                    default:
                        break;
                }
                outPut.writeObject(reply);
                outPut.flush();
            }
            input.close();
            outPut.close();
        } catch (Exception ex) {
            System.out.println("Problema no tratamento da conexão com o cliente: " + socket.getInetAddress());
            System.out.println("Erro: " + ex.getMessage());
            //Logger.getLogger(TreatConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("!!!Finalizando!!!");
            closeSocket(socket);
        }
    }

    private Communication executeOperation(String op, Communication communication) {
        agroToxicoDAO agroDAO = new agroToxicoDAO();
        cliDAO = new clientDAO();
        arquivoDAO arqDAO = new arquivoDAO();
        propriedadeDAO propDAO = new propriedadeDAO();
        produtoDAO prodDAO = new produtoDAO();
        impostosDAO impDAO = new impostosDAO();
        contactsListDAO contactDAO = new contactsListDAO();
        Communication reply = new Communication(op + "REPLY");
        switch (op) {
            case "BIOMETRIC":
                String[] biometricReply = cliDAO.biometricAuthenticated((String) communication.getParam("ANDROIDID"));
                reply.setParam("BIOMETRICREPLY", biometricReply[0]);
                reply.setParam("NICKNAME", biometricReply[1]);
                reply.setParam("WELCOME", biometricReply[2]);
                reply.setParam("LEVEL", biometricReply[3]);
                System.out.println("login reply :" + biometricReply[0]);
                break;
            case "LOGIN":
                String[] loginReply = cliDAO.authenticated((String) communication.getParam("nickName"), (String) communication.getParam("password"));
                reply.setParam("LOGINREPLY", loginReply[0]);
                reply.setParam("LEVEL", loginReply[1]);
                System.out.println("login reply :" + loginReply[0]);
                break;
            case "READ":
                contactsListDAO cDAO = new contactsListDAO();
                reply.setParam("READREPLY", cDAO.read((String) communication.getParam("nickName")));
                break;
            case "PROPRIEDADES":
                String nome = (String) communication.getParam("nome");
                String cnpj = (String) communication.getParam("cnpj");
                reply.setParam("PROPRIEDADESREPLY", propDAO.read(nome, cnpj));
                break;
            case "PRODUTOS":
                reply.setParam("PRODUTOSREPLY", prodDAO.read((int) communication.getParam("propriedadeId")));
                break;
            case "PRODUTOSUPDATE":
                reply.setParam("PRODUTOSUPDATEREPLY", prodDAO.prodEdit((List<Produto>) communication.getParam("produtos"), (int) communication.getParam("propriedadeId")));
                break;
            case "PROPRIEDADESELECTED":
                int propriedadeId = (int) communication.getParam("propriedadeId");
                reply.setParam("PROPRIEDADESELECTEDREPLY", propDAO.read((int) propriedadeId));
                break;
            case "PROPRIEDADEDELETE":
                int propriedadeIdDelete = (int) communication.getParam("propriedadeId");
                reply.setParam("PROPRIEDADEDELETEREPLY", (String) propDAO.delete(propriedadeIdDelete));
                break;
            case "ENDERECOUPDATE":
                reply.setParam("ENDERECOUPDATEREPLY", propDAO.enderecoEdit((Endereco) communication.getParam("endereco"), (int) communication.getParam("propriedadeId")));
                break;
            case "PROPRIEDADEUPDATE":
                reply.setParam("PROPRIEDADEUPDATEREPLY", propDAO.propriedadeEdit((Propriedade) communication.getParam("propriedade")));
                break;
            case "CNPJCHECK":
                reply.setParam("CNPJCHECKREPLY", propDAO.checkCNPJ((String) communication.getParam("cnpj")));
                break;
            case "PROPRIEDADECREATE":
                reply.setParam("PROPRIEDADECREATEREPLY", propDAO.create((Propriedade) communication.getParam("propriedade")));
                break;
            case "DOWNLOADFILE":
                reply.setParam("DOWNLOADFILEREPLY", arqDAO.read((String) communication.getParam("nomeHash")));
                break;
            case "CHECKFILE":
                reply.setParam("CHECKFILEREPLY", arqDAO.checkFile((String) communication.getParam("nomeHash")));
                break;
            case "CHECKCLIENT":
                reply.setParam("CHECKCLIENTREPLY", cliDAO.checkClient((String) communication.getParam("nickName")));
                break;
            case "CHECKDEVICE":
                reply.setParam("CHECKDEVICEREPLY", cliDAO.checkDevice((String) communication.getParam("ANDROIDID")));
                break;
            case "SEARCHCONTACT":
                reply.setParam("SEARCHCONTACTREPLY", contactDAO.search((String) communication.getParam("nickName")));
                break;
            case "CREATEACCOUNT":
                byte[] pictureC = (byte[]) communication.getParam("picture");
                String formatC = (String) communication.getParam("format");
                String nameC = (String) communication.getParam("name");
                String nickNameC = (String) communication.getParam("nickName");
                String deviceIDC = (String) communication.getParam("deviceID");
                String passwordC = (String) communication.getParam("password");
                reply.setParam("CREATEACCOUNTREPLY", cliDAO.createAccount(pictureC, formatC, nameC, nickNameC, passwordC, deviceIDC));
                break;
            case "EDITACCOUNT":
                byte[] pictureE = (byte[]) communication.getParam("picture");
                String formatE = (String) communication.getParam("format");
                String nameE = (String) communication.getParam("name");
                String nickNameE = (String) communication.getParam("nickName");
                String deviceIDE = (String) communication.getParam("deviceID");
                String passwordE = (String) communication.getParam("password");
                reply.setParam("EDITACCOUNTREPLY", cliDAO.editAccount(pictureE, formatE, nameE, nickNameE, deviceIDE, passwordE));
                break;
            case "PROFILEIMAGE":
                reply.setParam("PROFILEIMAGEREPLY", cliDAO.profilePic((String) communication.getParam("nickName")));
                break;
            case "LOGOUT":
                states = states.EXIT;
                System.out.println("Deslogado!");
                break;
            default:
                break;
        }
        int level = level(communication);
        switch (level) {
            case 1:
                switch (op) {
                    case "IMPOSTOUPDATE":
                        communication.getParam("nickName");
                        List<Imposto> imp = (List<Imposto>) communication.getParam("imposto");
                        reply.setParam("IMPOSTOUPDATEREPLY", impDAO.impostoEdit(imp, (int) communication.getParam("propriedadeId")));
                        break;
                    case "TIPOREAD":
                        reply.setParam("TIPOREADREPLY", impDAO.readTipo());
                        break;
                    case "PROPRIEDADESELECTED":
                        int propriedadeId = (int) communication.getParam("propriedadeId");
                        reply.setParam("PROPRIEDADESELECTEDREPLY", propDAO.read((int) propriedadeId));
                        reply.setParam("IMPOSTOSTOTALREPLY", impDAO.total(propriedadeId));
                        reply.setParam("IMPOSTOSREPLY", impDAO.read(propriedadeId));
                        break;
                }
                break;
            case 2:
                switch (op) {
                    case "AGROUPDATE":
                        List<Agrotoxico> agro = (List<Agrotoxico>) communication.getParam("agrotoxicos");
                        reply.setParam("AGROUPDATEREPLY", agroDAO.agroEdit(agro, (int) communication.getParam("propriedadeId")));
                        break;
                    case "PROPRIEDADESELECTED":
                        int propriedadeId = (int) communication.getParam("propriedadeId");
                        reply.setParam("PROPRIEDADESELECTEDREPLY", propDAO.read((int) propriedadeId));
                        reply.setParam("AGROREADREPLY", agroDAO.read((int) propriedadeId));
                        break;
                    case "AGROLIST":
                        reply.setParam("AGROLISTREPLY", agroDAO.read());
                }
                break;
        }

        // Agrotoxico
        return reply;
    }

    private int level(Communication c) {
        return cliDAO.level((String) c.getParam("nickName"));
    }

    private void closeSocket(Socket s) throws IOException {
        s.close();
        System.out.println("Encerrada conexão!");
    }

    @Override
    public void run() {
        try {
            System.out.println("Iniciando thread do cliente +" + socket.getInetAddress());
            treatConnection(socket);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(TreatConnection.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
