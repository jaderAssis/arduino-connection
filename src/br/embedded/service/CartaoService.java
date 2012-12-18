package br.embedded.service;

import br.embedded.dao.CartaoDao;

/**
 * 
 * @author - Jader Assis
 *
 */
public class CartaoService {

	private CartaoDao cartaoDao = new CartaoDao();
	
	public boolean validaCartao(String codigo) {
		return cartaoDao.validaCartao(codigo);
	}
	
}
