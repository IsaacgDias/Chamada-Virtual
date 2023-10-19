package com.ch.chamada.controllers;

import com.ch.chamada.service.AlunoService;
import com.ch.chamada.service.TurmaService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.ch.chamada.models.Aluno;
import com.ch.chamada.models.Turma;
import com.ch.chamada.repository.AlunoRepository;
import com.ch.chamada.repository.TurmaRepository;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ChamadaController {

	@Autowired
	private TurmaRepository tr;

	@Autowired
	AlunoRepository ar;

	@Autowired
	AlunoService alunoService;

	@Autowired
	TurmaService turmaService;


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
	public String formAluno(Aluno aluno, Model model) {

		ar.save(aluno);
		model.addAttribute("aluno", aluno);

		Iterable<Turma> turmas = tr.findAll();
		model.addAttribute("turmas", turmas);

		return "/add-aluno-turma";
	}

	@PostMapping("/addAluno")
	public String adicionarAlunoATurma(@RequestParam("alunoId") Long alunoId, @RequestParam("turmaId") Long turmaId) {
		alunoService.associarAlunoATurma(alunoId, turmaId);

		return "redirect:/";
	}

	@GetMapping("/formEditarAluno")
	public String formEditarAluno(@RequestParam Long id, Model model) {
		System.out.println("ID recebido: " + id);
		Aluno aluno = ar.findById(id).orElse(null);
		System.out.println("ESSE ALUNO: " + aluno.getId() + "aa" + aluno.getNome());
		model.addAttribute("aluno", aluno);

		return "Aluno/editAluno";
	}

	@PutMapping("/editarAluno")
	public String editarAluno(Aluno aluno) {
		ar.save(aluno);

		return "redirect:/listaAlunos";
	}


	@GetMapping("/deletarAluno")
	public String deletarAlunoPorId(@RequestParam Long id) {
		alunoService.deletarAlunoPorId(id);
		return "redirect:/";
	}
	@GetMapping("/encerrarTurma")
	public String encerrarTurma(@RequestParam Long id) {
		tr.desassociarAlunosDaTurma(id);
		tr.desassociarTurmaDoAluno(id);
		tr.deletarTurmaPorId(id);
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

	@GetMapping("/presenca")
	public ModelAndView presenca() {
		ModelAndView mv = new ModelAndView("Turma/presenca");
		Iterable<Turma> turmas = tr.findAll();
		mv.addObject("turmas", turmas);
		return mv;
	}

	@PostMapping("/fazerChamada")
	public String fazerChamada(@RequestParam Long turmaId, Model model) {
		Turma turma = tr.findById(turmaId).orElse(null);
		if (turma != null) {
			List<Aluno> alunos = turma.getAluno();
			model.addAttribute("alunos", alunos);

			model.addAttribute("nomeTurma", turma.getNome());

		}
		return "Turma/fazerChamada";
	}

	@Controller
	public class PresencaController {

		@Autowired
		private AlunoRepository alunoRepository;

		// ...

		@PostMapping("/salvarPresenca")
		public String salvarPresenca(@RequestParam Map<String, String> params) {
			Long turmaId = Long.parseLong(params.get("turmaId"));
			params.remove("turmaId");

			for (Map.Entry<String, String> entry : params.entrySet()) {
				String alunoIdStr = entry.getKey().replace("presenca_", "");
				Long alunoId = Long.parseLong(alunoIdStr);
				String presenca = entry.getValue();

				Aluno aluno = alunoRepository.findById(alunoId).orElse(null);
				if (aluno != null) {
					aluno.setPresenca(presenca);
					alunoRepository.save(aluno);
				}
			}

			return "redirect:/presenca"; // Redireciona para a página de presença após salvar
		}
	}

}
