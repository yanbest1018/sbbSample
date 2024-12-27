package com.sample.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.sample.sbb.DataNotFoundException;
import com.sample.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	/*
	 * public void QuestionService(QuestionRepository questionRepository) {
	 * this.questionRepository = questionRepository;
	 * 
	 * }
	 */
	public List<Question> getList() {
		return this.questionRepository.findAll();
	}
	
	public Page<Question> getList(int page) {
		List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("createDate"));
        
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
		
	}
	public Question getQuestion(Integer id) {
		
		Optional<Question> question = this.questionRepository.findById(id);
		
		if (question.isPresent() ) {
			return question.get();
		} else {
			throw new DataNotFoundException("question not found..");
			
		}
	}
	
	public void create(String subject ,String content, SiteUser siteUser ) {
		
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreateDate(LocalDateTime.now());
		question.setAuthor(siteUser);
		this.questionRepository.save(question);
	}
	
	public void delete(Question question ) {
		
		
		this.questionRepository.delete(question);
	}
	
}
