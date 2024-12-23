package com.sample.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sample.sbb.answer.AnswerForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
	
	private final QuestionService questionService;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(name="page" ,defaultValue="0") int page) {
		
		Page<Question> paging = this.questionService.getList(page);
		//List<Question> qlist = questionService.getList();
		model.addAttribute("questionList",paging);
		return "question_list";
		
	}
	
	@GetMapping(value="/detail/{id}")
	public String detailGet(Model model, 
			@PathVariable("id") Integer id
			,AnswerForm answerForm) {
		
		Question question = questionService.getQuestion(id);
		model.addAttribute("question",question);
		return "question_detail";
	}
	
	@PostMapping(value="/detail/{id}")
	public String detailPost(Model model, 
			@PathVariable("id") Integer id
			,AnswerForm answerForm) {
		
		Question question = questionService.getQuestion(id);
		model.addAttribute("question",question);
		return "question_detail";
	}
	
	
	@GetMapping("/create")
	public String createQuestion(QuestionForm questionForm) {
		
		return "question_form";
	}
	// BindingResult 매개변수는 @Valid 애너테이션으로 검증이 수행된 결과를 의미하는 객체이다.
	@PostMapping("/create")
	public String createQuestion(
			Model model,
			@Valid QuestionForm questionForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		} else {
			this.questionService.create(questionForm.getSubject(), questionForm.getContent());	
		}
		return "redirect:/question/list";
	}
}
