package com.exemplo.produtorrural.repository;

import com.exemplo.produtorrural.model.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {
}
