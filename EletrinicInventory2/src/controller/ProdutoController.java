/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProdutoDAO;
import java.util.List;
import model.Cliente;
import model.Produto;
import vendaonline.VendaOnline;

/**
 *
 * @author dalmir
 */
public class ProdutoController {

    public static List<Produto> getProdutos(String busca) {
        ProdutoDAO dao = VendaOnline.getProdutoDAO();
        Produto p = new Produto(busca, null, 0);
        return dao.findByExample(p, new String[]{"descricao", "preco"});
    }
    
    public static List<Produto> getAllProdutos() {
        ProdutoDAO dao = VendaOnline.getProdutoDAO();
        return dao.findAll();
    }

    public static void cadastrar(String nome, String desc, String preco) {
        ProdutoDAO dao = VendaOnline.getProdutoDAO();
        Produto p = new Produto(nome, desc, Float.parseFloat(preco));
        dao.saveEntity(p);
    }
    
    public static Produto getProduto(long id) {
        ProdutoDAO dao = VendaOnline.getProdutoDAO();
        return dao.findById(id, false);
    }

    public static void deletaProduto(Long currentId) {
        ProdutoDAO dao = VendaOnline.getProdutoDAO();
        Produto p = dao.findById(currentId, false);
        if (p != null) {
            dao.deleteEntity(p);
        }
    }

    public static void atualizaProduto(Long currentId, String nome, String desc, String preco) {
        ProdutoDAO dao = VendaOnline.getProdutoDAO();
        Produto p = dao.findById(currentId, false);
        p.setNome(nome);
        p.setDescricao(desc);
        p.setPreco(Float.parseFloat(preco));
        dao.saveEntity(p);
    }
}
