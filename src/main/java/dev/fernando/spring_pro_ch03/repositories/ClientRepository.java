package dev.fernando.spring_pro_ch03.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.fernando.spring_pro_ch03.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
