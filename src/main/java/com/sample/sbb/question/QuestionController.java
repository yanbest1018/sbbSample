package com.sample.sbb.question;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.sample.sbb.answer.AnswerForm;
import com.sample.sbb.user.SiteUser;
import com.sample.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

	private final QuestionService questionService;
	private final UserService userService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {

		Page<Question> paging = this.questionService.getList(page);
		// List<Question> qlist = questionService.getList();
		model.addAttribute("questionList", paging);
		return "question_list";

	}

	@GetMapping(value = "/detail/{id}")
	public String detailGet(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {

		Question question = questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}

	@PostMapping(value = "/detail/{id}")
	public String detailPost(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {

		Question question = questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}

	@GetMapping("/create")
	@PreAuthorize("isAuthenticated()")
	public String createQuestion(QuestionForm questionForm) {

		return "question_form";
	}

	// BindingResult 매개변수는 @Valid 애너테이션으로 검증이 수행된 결과를 의미하는 객체이다.
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	public String createQuestion(Model model, @Valid QuestionForm questionForm, BindingResult bindingResult,
			Principal principal) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		} else {
			SiteUser siteUser = this.userService.getUser(principal.getName());
			this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		}
		return "redirect:/question/list";
	}
	
	@GetMapping("/delete/{id}")
	@PreAuthorize("isAuthenticated()")
	public String deleteQuestion(
			@PathVariable("id") Integer id,
			Principal principal) {
		
		String username = principal.getName();
		
		Question question = this.questionService.getQuestion(id);
		
		if ( question.getAuthor().getUsername() == username ) {
			this.questionService.delete(question);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다.");
		}
		return "redirect:/question/list";
	}
	
}
