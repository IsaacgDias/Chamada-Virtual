package com.ch.chamada.controllers;

import com.ch.chamada.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ch.chamada.models.Aluno;
import com.ch.chamada.models.Turma;
import com.ch.chamada.repository.AlunoRepository;
import com.ch.chamada.repository.TurmaRepository;


import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@Controller
public class ChamadaController {

	@Autowired
	private TurmaRepository tr;

	@Autowired
	AlunoRepository ar;

	@Autowired
	AlunoService alunoService;


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
