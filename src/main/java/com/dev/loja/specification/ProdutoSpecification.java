package com.dev.loja.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.dev.loja.domain.Produto;



public class ProdutoSpecification implements Specification<Produto>{
	private Produto filter;
	
	public ProdutoSpecification(Produto fliter) {
		super();
		this.filter = filter;
	}
	
	@Override
	public Predicate toPredicate(Root<Produto> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		Predicate p = cb.disjunction();
		
		if(filter.getDescricao() != null) {
			p.getExpressions().add(cb.like(root.get("nome"), "%" + filter.getDescricao() + "%"));
		}
		
		return p;
	}

}
