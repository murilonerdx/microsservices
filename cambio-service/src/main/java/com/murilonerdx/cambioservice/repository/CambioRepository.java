package com.murilonerdx.cambioservice.repository;

import com.murilonerdx.cambioservice.model.Cambio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CambioRepository extends JpaRepository<Cambio, Long> {
}