package com.sample.sbb.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	private final PasswordEncoder passEnc;
	
	public SiteUser create(String username, String email, String password) {
		
		SiteUser user = new SiteUser();

		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passEnc.encode(password));
		this.userRepository.save(user);
		
		return user;
		
	}
	
	
}
