package br.embedded.service;

import java.util.List;

import br.embedded.dao.VagaDao;
import br.embedded.entity.Vaga;

/**
 * 
 * @author - Jader Assis
 *
 */
public class VagaService {

	private VagaDao vagaDao = new VagaDao();
	
	public List<Vaga> listAll() {
		return vagaDao.listAll();
	}
	
	public void atualizaStatusVaga(boolean isOcupada, String descricao) {
		if ( descricao != null ) {
			descricao = descricao.toUpperCase();
			vagaDao.atualizaStatusVaga(isOcupada, descricao);
		}
	}
	
}
