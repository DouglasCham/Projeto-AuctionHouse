/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    private final String SQL_INSERT = "INSERT INTO produtos (nome, valor, status) VALUES (?,?,?)";
    private final String SQL_SELECT = "SELECT id, nome, valor, status FROM produtos";
    
    public void cadastrarProduto (ProdutosDTO produto){
        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(SQL_INSERT);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            conn.close();
            prep.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "O cadastro falhou.");
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        try {
            int ID, VALOR;
            String NOME, STATUS;
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(SQL_SELECT);
            resultset = prep.executeQuery();
            while(resultset.next()){
                ID = resultset.getInt("id");
                NOME = resultset.getString("nome");
                VALOR = resultset.getInt("valor");
                STATUS = resultset.getString("status");
                ProdutosDTO produto = new ProdutosDTO(ID,NOME,VALOR,STATUS);
                listagem.add(produto);
            }
        }  catch(SQLException e){
            listagem.clear();
        }
        return listagem;
    }      
}

