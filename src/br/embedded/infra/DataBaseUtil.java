package br.embedded.infra;

import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;

import br.embedded.entity.Vaga;
import br.embedded.service.VagaService;

/**
 * 
 * @author - Jader Assis
 *
 */
public class DataBaseUtil {

	private static BasicDataSource dataSource;
	
	static {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost/db_embedded_park");
		dataSource.setUsername("oversea");
		dataSource.setPassword("r9p6t0v8");
		dataSource.setMinIdle(1);
		dataSource.setMaxActive(4);
	}
	
	public static BasicDataSource getDataSource() {
		return dataSource;
	}
	
	public static void main(String[] args) {
	 try {
		 VagaService vaga = new VagaService();
//		 vaga.atualizaStatusVaga(true, "a1");
		 List<Vaga> list = vaga.listAll();
		 
		 for(Vaga vg : list) {
			 System.out.println(vg.getDescricao());
		 }
		 

//		 CartaoService cartao = new CartaoService();
//		 System.out.println(cartao.validaCartao("16913730"));
		 
		} catch(Exception e) { 
			System.out.println("Houve um erro:" + e.getMessage()); 
		}
	}
}
