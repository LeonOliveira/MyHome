package br.com.myHome.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@Entity
@Table(name = "gestor")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class Gestor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1400145918077418042L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GESTOR_ID")
	@SequenceGenerator(name = "GESTOR_ID", sequenceName = "id_gestor", allocationSize = 1)
	@Column(name = "oid", nullable = false)
	private Integer oid;

	@Column(name = "codigo", nullable = false)
	private String codigo;

	@Column(name = "descricao", length = 60, nullable = false)
	private String descricao;
}
