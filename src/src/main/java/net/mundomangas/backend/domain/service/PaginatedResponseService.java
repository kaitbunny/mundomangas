package net.mundomangas.backend.domain.service;

import java.util.List;

import lombok.Getter;

@Getter
public class PaginatedResponseService<T> {
	private List<T> items;
	private Integer totalPages;
	private Long totalItems;
	private Integer currentPage;
	
	public PaginatedResponseService(List<T> items, Integer totalPages, Long totalItems, Integer currentPage) {
		this.items = items;
		this.totalPages = totalPages;
		this.totalItems = totalItems;
		this.currentPage = currentPage;
	}
}
