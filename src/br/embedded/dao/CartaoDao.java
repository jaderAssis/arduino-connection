package br.embedded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;

import br.embedded.infra.DataBaseUtil;

/**
 * 
 * @author - Jader Assis
 *
 */
public class CartaoDao {

	private static BasicDataSource dataSource;

	static {
		dataSource = DataBaseUtil.getDataSource();
	}

	public boolean validaCartao(String codigo) {
		try {
			String sql = "SELECT COUNT(*) FROM tb_cartao cartao WHERE cartao.codigo = '" + codigo + "'";

			Connection connection = dataSource.getConnection();
			Statement pt = connection.createStatement();
			ResultSet rs = pt.executeQuery(sql);
			int qtdCartao = 0;
			
			while(rs.next()) {
				qtdCartao = rs.getInt(1);
             }
			
			rs.close();
			pt.close();
			connection.close();
			
			return qtdCartao > 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
