package com.ch.chamada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ch.chamada.models.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
