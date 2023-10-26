package com.ch.chamada.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ch.chamada.models.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    //Remove o aluno da turma
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM turma_aluno WHERE turma_id = ? AND aluno_id = ?", nativeQuery = true)
    void removerAlunoTurma(Long turmaId, Long alunoId);
}
