package dsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsweb.model.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {

}