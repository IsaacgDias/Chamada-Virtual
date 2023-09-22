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
            // Recupere o aluno e a turma com base nos IDs
            Optional<Aluno> alunoOptional = alunoRepository.findById(alunoId);
            Optional<Turma> turmaOptional = turmaRepository.findById(turmaId);

            // Verifique se o aluno e a turma foram encontrados
            if (alunoOptional.isPresent() && turmaOptional.isPresent()) {
                Aluno aluno = alunoOptional.get();
                Turma turma = turmaOptional.get();

                // Adicione o aluno à turma e a turma ao aluno (associação bidirecional)
                aluno.getTurma().add(turma);
                turma.getAluno().add(aluno);

                // Salve as alterações no repositório do aluno
                alunoRepository.save(aluno);
            }
            // Caso contrário, você pode lidar com isso de acordo com sua lógica (por exemplo, enviar uma mensagem de erro ou redirecionar)
        }
    }


