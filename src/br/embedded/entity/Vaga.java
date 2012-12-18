package br.embedded.entity;

import java.io.Serializable;

/**
 * 
 * @author - Jader Assis
 *
 */
//  "tb_vaga"
public class Vaga implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String descricao;
	private Boolean ocupada;

	public Vaga() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean isOcupada() {
		return this.ocupada;
	}

	public void setOcupada(Boolean ocupada) {
		this.ocupada = ocupada;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ocupada == null) ? 0 : ocupada.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Vaga other = (Vaga) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} else if (!descricao.equals(other.descricao)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (ocupada == null) {
			if (other.ocupada != null) {
				return false;
			}
		} else if (!ocupada.equals(other.ocupada)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Vaga [id=" + id + ", descricao=" + descricao + ", ocupada="
				+ ocupada + "]";
	}

}
