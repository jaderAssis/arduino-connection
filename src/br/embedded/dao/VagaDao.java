package br.embedded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;

import br.embedded.entity.Vaga;
import br.embedded.infra.DataBaseUtil;

/**
 * 
 * @author - Jader Assis
 *
 */
public class VagaDao {

	private static BasicDataSource dataSource;

	static {
		dataSource = DataBaseUtil.getDataSource();
	}

	public List<Vaga> listAll() {
		try {
			List<Vaga> list = new ArrayList<Vaga>();
			Connection connection = dataSource.getConnection();
			
			String sql = "select * from tb_vaga";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			Vaga vaga;
			while( rs.next() ) {
				vaga = new Vaga();
				vaga.setId(rs.getLong(1));
				vaga.setDescricao(rs.getString(2));
				vaga.setOcupada(rs.getBoolean(3));
				list.add(vaga);
			}
			
			st.close();
			rs.close();
			connection.close();
			
			return list;
			
		} catch(Exception e) { 
			System.out.println("Houve um erro: " + e.getMessage());
			return null;
		}
	}
	
	
	public void atualizaStatusVaga(boolean isOcupada, String descricao) {
		try {
			Connection connection = dataSource.getConnection();
			
			String sql = "update tb_vaga set isocupada = ? where descricao = ?";
			PreparedStatement ps = connection.prepareStatement(sql); 
			ps.setBoolean(1, isOcupada);
			ps.setString(2, descricao);
			
			ps.executeUpdate();
			ps.close();
			connection.close();
			
		} catch(Exception e) { 
			System.out.println("Houve um erro:" + e.getMessage()); 
		}
	}
	
}
