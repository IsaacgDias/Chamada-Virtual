package com.ch.chamada.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ch.chamada.models.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

    @Modifying // indica uma operação de modificação no banco de dados
    @Transactional
    @Query(value = "DELETE FROM aluno_turma WHERE turma_id = ?", nativeQuery = true)
    void desassociarAlunosDaTurma(Long turmaId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM turma_aluno WHERE turma_id = ?", nativeQuery = true)
    void desassociarTurmaDoAluno(Long turmaId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM turma WHERE id = ?", nativeQuery = true)
    void deletarTurmaPorId(Long turmaId);

    @Modifying
    @Transactional
    @Query(value = "SELECT turma_id FROM turma_aluno WHERE aluno_id = ?", nativeQuery = true)
    void turmasDoAluno(Long turmaId);

}

