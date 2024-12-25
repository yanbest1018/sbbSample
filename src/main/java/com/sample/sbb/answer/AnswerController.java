package com.sample.sbb.answer;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sample.sbb.question.Question;
import com.sample.sbb.question.QuestionService;
import com.sample.sbb.user.SiteUser;
import com.sample.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;
	
	@PostMapping("/create/{id}")
	@PreAuthorize("isAuthenticated()")
	public String createAnswer(Model model , @PathVariable("id") Integer id,  
			@Valid AnswerForm answerForm, BindingResult bindingResult,
			Principal principal) {
		
		Question question = questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		if ( bindingResult.hasErrors() ) {
			System.out.printf("Error occured : %s", bindingResult.getErrorCount());
			model.addAttribute("question", question);
            return "question_detail";
		}
		this.answerService.create(question, answerForm.getContent(), siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}
}
