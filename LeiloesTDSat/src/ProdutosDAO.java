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
    private final String SQL_UPDATE = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
    private final String SQL_SELECT_VENDIDOS = "SELECT id, nome, valor, status FROM produtos WHERE status = 'Vendido'";
    private final String SQL_MAX_ID = "SELECT MAX(id) as 'maximo' FROM produtos";
    
    public void cadastrarProduto (ProdutosDTO produto){
        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(SQL_INSERT);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            prep.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível cadastrar o produto.");
        }
    }
    
    public void venderProduto (int id){
        try {
            if(id > buscarIDMaximo() || id <= 0){
                JOptionPane.showMessageDialog(null, "Não foi possível realizar a venda.\nID Inválido, por favor tente outro.");
            } else {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(SQL_UPDATE);
            prep.setInt(1, id);
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Venda do produto realizada com sucesso.");
            prep.close();
            conn.close();
            }
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Não foi possível realizar a venda.");
        }
    }
    
    public int buscarIDMaximo(){
        int idMaximo = 0;
        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(SQL_MAX_ID);
            resultset = prep.executeQuery();
            while(resultset.next()){
                idMaximo = resultset.getInt("maximo");
            }
            resultset.close();
            prep.close();
            conn.close();
        } catch(SQLException e) {
            //Exception
        }
        return idMaximo;
    }
    
    public ArrayList<ProdutosDTO>listarProdutosVendidos(){
        try {
            int ID, VALOR;
            String NOME, STATUS;
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(SQL_SELECT_VENDIDOS);
            resultset = prep.executeQuery();
            while(resultset.next()){
                ID = resultset.getInt("id");
                NOME = resultset.getString("nome");
                VALOR = resultset.getInt("valor");
                STATUS = resultset.getString("status");
                ProdutosDTO produto = new ProdutosDTO(ID,NOME,VALOR,STATUS);
                listagem.add(produto);
            }
            resultset.close();
            prep.close();
            conn.close();
        }  catch(SQLException e){
            listagem.clear();
        }
        return listagem;
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
            resultset.close();
            prep.close();
            conn.close();
        }  catch(SQLException e){
            listagem.clear();
        }
        return listagem;
    }      
}

