/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ClienteDAO;
import java.util.List;
import model.Cliente;
import vendaonline.VendaOnline;

/**
 *
 * @author dalmir
 */
public class ClienteController {
    
    public static long autentica(String login, String senha) {
        ClienteDAO dao = VendaOnline.getClienteDAO();
        Cliente c = new Cliente(login, senha);
        List<Cliente> list = dao.findByExample(c, null);
        if (!list.isEmpty()) {
            return list.get(0).getId();
        }
        return 0;
    }
    
    public static void cadastrar(String login, String senha) {
        ClienteDAO dao = VendaOnline.getClienteDAO();
        Cliente c = new Cliente(login, senha);
        dao.saveEntity(c);
    }
}
