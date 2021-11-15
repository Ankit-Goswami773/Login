package com.loginproject.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.loginproject.applications_exceptions.CommonLoginException;
import com.loginproject.common.CommonResponse;
import com.loginproject.entity.Login;
import com.loginproject.jwt_config.jwt_controller.JwtGenerateTokenService;
import com.loginproject.jwt_config.jwt_model_clasess.JwtRequest;
import com.loginproject.models.LoginDTO;
import com.loginproject.models.ReCaptchaResponseType;
import com.loginproject.repository.LoginRepository;
import com.loginproject.util.LoginUtility;

@Service
public class LoginServiceImpl implements LoginService {

	private static final String GOOGLE_RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";

	private static final String RECAPTCHA_SECRET = "6LcazPgcAAAAAJdMFSKEZOuGAaDxUCns5KKp2fw8";

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JwtGenerateTokenService jwtGenerateTokenService;

	@Override
	public CommonResponse loginCheck(LoginDTO loginDTO) {
		Login login = LoginUtility.loginDtoToEntity(loginDTO);
		Map<String, String> map = new HashMap<>();
		Login findByuserEmail = this.loginRepository.findByUserEmail(login.getUserEmail());

		if (findByuserEmail == null)
			throw new CommonLoginException("User not found", 404, HttpStatus.BAD_REQUEST);

		if (!findByuserEmail.getPassword().equals(login.getPassword()))
			throw new CommonLoginException("Credentials Invalid", 400, HttpStatus.BAD_REQUEST);

		JwtRequest jwtRequest = new JwtRequest(login.getUserEmail(), login.getPassword());
		String generatedToken = jwtGenerateTokenService.generateToken(jwtRequest);

		map.put("Role", findByuserEmail.getRoles());
		map.put("empId",findByuserEmail.getEmpId()+"");
		map.put("token", generatedToken);

		return new CommonResponse(200, "User Validated", map);
	}

	public CommonResponse validateCaptcha(String captcha) {
		CommonResponse message = new CommonResponse();

		MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
		requestMap.add("secret", RECAPTCHA_SECRET);
		requestMap.add("response", captcha);

		ReCaptchaResponseType apiResponse = restTemplate.postForObject(GOOGLE_RECAPTCHA_URL, requestMap,
				ReCaptchaResponseType.class);

		if (apiResponse == null) {
			message.setMessage("false");
			message.setStatusCode(0);
			message.setData("");
			return message;
		}
		message.setMessage(apiResponse.isSuccess() + "");
		message.setStatusCode(200);
		message.setData("");

		return message;

	}

	@Override
	public CommonResponse saveData(LoginDTO loginDTO) {
		Login login = LoginUtility.loginDtoToEntity(loginDTO);
		if (login.getUserEmail().isEmpty() && login.getPassword().isEmpty())
			throw new CommonLoginException("useremail or password is empty", 400, HttpStatus.BAD_REQUEST);
		Login save = loginRepository.save(login);
		return new CommonResponse(201, "success", save);
	}

}
