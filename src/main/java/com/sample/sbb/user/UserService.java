package com.sample.sbb.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sample.sbb.DataNotFoundException;

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
	
	public SiteUser getUser(String username) {
		Optional<SiteUser> user = this.userRepository.findByusername(username);
		if (user.isPresent()) {
			return user.get();	
		} else {
			throw new DataNotFoundException(username);
		}
	}
}
