package com.sample.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sample.sbb.question.QuestionService;


@SpringBootTest
public class SbbApplicationTests {
	
	@Autowired
	private QuestionService questionService;
	
	@Test
	void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = String.format("내용이 있을리가..[%03d]", i);
            this.questionService.create(subject, content,null);
        }
    }

}
