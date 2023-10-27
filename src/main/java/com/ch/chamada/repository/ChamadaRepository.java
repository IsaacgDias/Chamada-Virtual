package com.ch.chamada.repository;


import com.ch.chamada.models.Chamada;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadaRepository  extends JpaRepository<Chamada, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Chamada c SET c.alunoId = :alunoId, c.dataLancamento = :datalancamento, c.presenca = :presenca WHERE c.turmaId = :turmaId")
    void updatePresenca(
            @Param("alunoId") Long alunoId,
            @Param("datalancamento") String datalancamento,
            @Param("presenca") String presenca,
            @Param("turmaId") Long turmaId
    );

}
