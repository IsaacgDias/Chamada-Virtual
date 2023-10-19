package com.ch.chamada.service;

import com.ch.chamada.models.Aluno;
import com.ch.chamada.models.Turma;
import com.ch.chamada.repository.AlunoRepository;
import com.ch.chamada.repository.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TurmaService {
    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public Turma turmaPorId(Long id) {
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        return turmaOptional.orElse(null);
    }

}
