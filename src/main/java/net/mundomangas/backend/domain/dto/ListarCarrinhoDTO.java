package net.mundomangas.backend.domain.dto;

import java.math.BigDecimal;
import java.util.List;

public record ListarCarrinhoDTO(List<ItemCarrinhoDTO> items, Integer totalPages, Long totalItems, Integer currentPage, BigDecimal total) {
}
