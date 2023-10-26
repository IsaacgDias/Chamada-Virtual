package com.ch.chamada.service;

import com.ch.chamada.models.Aluno;
import com.ch.chamada.models.Turma;
import com.ch.chamada.repository.AlunoRepository;
import com.ch.chamada.repository.TurmaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
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


    public boolean AlunoNaoAssociadoATurmas(Long alunoId) {
        Optional<Aluno> alunoOptional = alunoRepository.findById(alunoId);

        if (alunoOptional.isPresent()) {
            Aluno aluno = alunoOptional.get();
            return aluno.getTurma().isEmpty();
        }

        return false; // Retorne false se o aluno não for encontrado
    }

}


