package com.japc.rest.ws.erp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptEncoderTest {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		for(int i=1;i<=10;i++) {
			String encodedString = encoder.encode("qwerty");
			System.out.println(encodedString);
		}
		
	}
}
