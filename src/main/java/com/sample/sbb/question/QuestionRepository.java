package com.sample.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	//Question findBySubjectOrContent (String subject, String content);
	
	List<Question> findBySubjectLikeOrContentLike (String subject, String tnecont);
	
	List<Question> findBySubjectLike(String subject);
	
	List<Question> findBySubjectIn(String[] subject);
	
	Page<Question> findAll(Pageable pageable);
	
	
	

}
