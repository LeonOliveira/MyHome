package br.com.myHome.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@Entity
@Table(name = "centro_custo")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CentroCusto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3356268947130206942L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CENTRO_CUSTO_OID")
	@SequenceGenerator(name = "CENTRO_CUSTO_OID", sequenceName = "oid_centro_custo", allocationSize = 1)
	@Column(name = "oid", nullable = false)
	private Integer oid;

	@Column(name = "codigo", length = 20, nullable = false)
	private String codigo;

	@Column(name = "descricao", length = 100)
	private String descricao;
	
	@Column(name = "oid_gestor", nullable = false)
	private Integer oid_gestor;
	
	@Column(name = "detalhe")
	private String detalhe;
	
	@Transient
    private ArrayList<Gestor> gestores;
}
