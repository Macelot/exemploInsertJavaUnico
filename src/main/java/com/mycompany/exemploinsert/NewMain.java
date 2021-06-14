/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exemploinsert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import org.joda.time.DateTime;

/**
 *
 * @author marce
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Connection connection;
        try {
            String url = "jdbc:mysql://127.0.0.1:3307/test";
            String user = "root";
            String pass = "usbw";
            connection = DriverManager.getConnection(url,user,pass);
            
            //este comando via rodar apenas a primeira vez, caso vc já tenha a tabela pode retirar este CREATE
            String comandoCreate = "CREATE TABLE IF NOT EXISTS `usuarios` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `nome` varchar(30) NOT NULL,\n" +
                "  `usuario` varchar(30) DEFAULT NULL,\n" +
                "  `salario` decimal(14,2) DEFAULT NULL,\n" +
                "  `nascimento` date DEFAULT NULL,\n" +
                "  `dataCadastro` datetime DEFAULT NULL,\n" +
                "  `ativo` varchar(5) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB  DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;";
            
            Statement stmtCreate = connection.createStatement();
            stmtCreate.execute(comandoCreate);
            
            //verificar se existe o usuário com o nome informado
            String comandoSelect = "SELECT * FROM usuarios WHERE nome=?;";
            PreparedStatement pstmtSelect = connection.prepareStatement(comandoSelect);        
            pstmtSelect.setString(1,"Marcelo J. Telles");
            ResultSet resultadoSelect = pstmtSelect.executeQuery();
            while(resultadoSelect.next()){
                System.out.println("Usuário já existe");
                //caso o usuário já exista vamos retornar, isso significa que os comandos abaixo serão ignorados
                return;
            }
            
            //este comando também so roda a primeira vez. 
            //Para testar troque o valor da linha 53 e 83 para outro nome. Outra alternativa seria colocar no banco de dados a coluna nome como Unique
            //assim:
            //ALTER TABLE `usuarios` ADD UNIQUE (`nome`);
            
            String comandoInsert = "insert into usuarios ("
                    + "nome,"
                    + "usuario,"
                    + "salario,"
                    + "nascimento,"
                    + "dataCadastro,"
                    + "ativo)"
                    + "values ("
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?);";
            PreparedStatement pstmtInsert = connection.prepareStatement(comandoInsert);
            
            Date nascimento = (Date) new DateTime(1979,11,21,0,0).toDate();
            pstmtInsert.setString(1,"Marcelo J. Telles");
            pstmtInsert.setString(2,"marcelot");
            pstmtInsert.setFloat(3, 20000.50f);
            pstmtInsert.setDate(4, new java.sql.Date(nascimento.getTime()));
            pstmtInsert.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            pstmtInsert.setInt(6, 1);
            pstmtInsert.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
