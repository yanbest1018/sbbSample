package com.sample.sbb.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	// Spring 매개 변수 v1
	@GetMapping("/signup")
	public String signup(UserCreateForm userform) {
		return "signup_form";
	}

	/*// Srping 매개변수 v2 둘이 동일한 효과
	메서드 매개변수로 전달된 UserCreateForm 객체는 빈 상태로 뷰에 전달됩니다.
	Spring은 뷰 렌더링 전에 이 객체를 Model에 추가합니다.(기본 키: 클래스 이름의 카멜케이스 버전, 여기서는 userCreateForm)
	
	@GetMapping("/signup")
	public String signup(Model model) {
		UserCreateForm userform = new UserCreateForm();
		model.addAttribute("userform", userform);
		return "signup_form";
	}*/

	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "signup_form";
		}

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}

		userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
		return "redirect:/";
	}

}
