package com.sample.sbb.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sample.sbb.question.Question;
import com.sample.sbb.question.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

	private final QuestionService questionService;
	private final AnswerService answerService;
	
	@PostMapping("/create/{id}")
	public String createAnswer(Model model , @PathVariable("id") Integer id,  
			@Valid AnswerForm answerForm, BindingResult bindingResult,
			RedirectAttributes redirectAtributes) {
		
		Question question = questionService.getQuestion(id);
		if ( bindingResult.hasErrors() ) {
			//redirectAtributes.addFlashAttribute(bindingResult);
			model.addAttribute("id",id);
			return String.format("forward:/question/detail/%s",id);

			//model.addAttribute("question", question);
            //return "question_detail";
		}
		this.answerService.create(question, answerForm.getContent());
		return String.format("redirect:/question/detail/%s", id);
	}
}
