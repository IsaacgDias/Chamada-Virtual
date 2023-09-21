package com.ch.chamada.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ch.chamada.models.Aluno;
import com.ch.chamada.models.Turma;
import com.ch.chamada.repository.AlunoRepository;
import com.ch.chamada.repository.TurmaRepository;


import jakarta.validation.Valid;

@Controller
public class ChamadaController {

	@Autowired
	private TurmaRepository tr;
	
	@Autowired AlunoRepository ar;
	
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/cadastrarTurma")
	public String form() {
		return "Turma/formTurma";
	}
	

	@PostMapping("/cadastrarTurma")
	public String form(Turma turma) {
		
		tr.save(turma);
		return "redirect:/";
	}
	
	@GetMapping("/cadastrarAluno")
	public String formAluno() {
		return "Aluno/formAluno";
	
	}
	
	@PostMapping("/cadastrarAluno")
	public String formAluno(Aluno aluno) {
		
		ar.save(aluno);
		return "redirect:/";
	}
	
	@GetMapping("/listaAlunos")
	public ModelAndView listaAlunos() {
		ModelAndView mv = new ModelAndView("Aluno/alunos");
		Iterable<Aluno> alunos = ar.findAll();
		// mv.addObject("alunos") a mesma do alunos.html ${alunos}
		mv.addObject("alunos", alunos);
		return mv;
	
	}
	
	@GetMapping("/listaTurmas")
	public ModelAndView listaTurmas() {
		ModelAndView mv = new ModelAndView("Turma/turmas");
		Iterable<Turma> turmas = tr.findAll();
		// mv.addObject("turmas") a mesma do alunos.html ${turmas}
		mv.addObject("turmas", turmas);
		return mv;
	
	}
	
	
}
