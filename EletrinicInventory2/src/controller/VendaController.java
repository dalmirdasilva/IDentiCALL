/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ClienteDAO;
import dao.ProdutoDAO;
import dao.VendaDAO;
import java.util.Date;
import model.Cliente;
import model.Produto;
import model.Venda;
import vendaonline.VendaOnline;

/**
 *
 * @author dalmir
 */
public class VendaController {

    public static void realizaVenda(Long produtoId, long clienteId) {
        VendaDAO vendaDao = VendaOnline.getVendaDAO();
        ProdutoDAO produtoDao = VendaOnline.getProdutoDAO();
        ClienteDAO clienteDao = VendaOnline.getClienteDAO();
        Cliente c = clienteDao.findById(clienteId, false);
        Produto p = produtoDao.findById(produtoId, false);
        Venda v = new Venda(new Date(), c, p);
        vendaDao.saveEntity(v);
    }
}
