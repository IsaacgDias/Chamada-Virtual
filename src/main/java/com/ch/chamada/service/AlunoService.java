package com.ch.chamada.service;

import com.ch.chamada.models.Aluno;
import com.ch.chamada.models.Turma;
import com.ch.chamada.repository.AlunoRepository;
import com.ch.chamada.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunoService {


        @Autowired
        private AlunoRepository alunoRepository;

        @Autowired
        private TurmaRepository turmaRepository;

        public void associarAlunoATurma(Long alunoId, Long turmaId) {
            Optional<Aluno> alunoOptional = alunoRepository.findById(alunoId);
            Optional<Turma> turmaOptional = turmaRepository.findById(turmaId);

            if (alunoOptional.isPresent() && turmaOptional.isPresent()) {
                Aluno aluno = alunoOptional.get();
                Turma turma = turmaOptional.get();

                aluno.getTurma().add(turma);
                turma.getAluno().add(aluno);

                alunoRepository.save(aluno);
            }

        }

        public void deletarAlunoPorId(Long id) {
            alunoRepository.deleteById(id);
        }

        public void editarAlunoPorId(Long id) {
            Aluno aluno = new Aluno();
            alunoRepository.save(aluno);
        }
    }


