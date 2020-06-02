package com.projectboard.ppmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.projectboard.ppmtool.domain.User;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> aClass) {
		// TODO Auto-generated method stub
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object obj, Errors err) {
		// TODO Auto-generated method stub
		User user = (User) obj;
		if(user.getPassword().length() < 6) {
			err.rejectValue("password","Length","Password must be at least 6 characters");
		}
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			err.rejectValue("confirmPassword", "Match", "Passwords must match");
		}
		
	}

}
