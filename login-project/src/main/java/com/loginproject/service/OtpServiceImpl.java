package com.loginproject.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.loginproject.applications_exceptions.CommonLoginException;
import com.loginproject.common.CommonResponse;
import com.loginproject.entity.Otp;
import com.loginproject.models.Employee;
import com.loginproject.models.OtpResponse;
import com.loginproject.repository.OtpRepository;

@Service
public class OtpServiceImpl implements OtpService {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private OtpRepository otpRepository;

	public Employee getEmployeeById(String email, String token) {
		String url = "http://localhost:8095/employee/getEmpByEmailId?emailId=" + email;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);

		ResponseEntity<Employee> responseFromApi = restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<>("parameters", headers), Employee.class);

		return responseFromApi.getBody();

	}

	public void changeEmployeeStatus(long employeeId, String token) {
		String url = "http://localhost:8095/employee/deleteEmployee/" + employeeId;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);

		restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>("parameters", headers), String.class);

	}

	public int generateOTPNumber() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 100000 + r.nextInt(100000));
	}

	public boolean mailSender(String mail, String body, String subject) {
		boolean emailStatus = false;
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("appemail78@gmail.com");
		mailMessage.setTo(mail);
		mailMessage.setText(body);
		mailMessage.setSubject(subject);
		javaMailSender.send(mailMessage);
		emailStatus = true;
		return emailStatus;
	}

	@Override
	public CommonResponse generatAndSave(String email, String token) {
		final String hello = "Hello ";
		Employee employeeById = this.getEmployeeById(email, token);

		if (employeeById.getStatus().equalsIgnoreCase("INACTIVE")) {
			String msgOTP = hello + employeeById.getFirstName()
					+ " Your Login id status is INACTIVE please contact to admin  for ACTIVATION";
			this.mailSender(email, msgOTP, "INACTIVE - STATUS");
			throw new CommonLoginException(msgOTP, 404, HttpStatus.BAD_REQUEST);
		}

		if (otpRepository.countOfOtpPerEmployee(employeeById.getEmpId()) >= 5) {

			this.changeEmployeeStatus(employeeById.getEmpId(), token);

			String msgOTP = hello + employeeById.getFirstName()
					+ " You have crossed otp limit your Login id status is INACTIVE please contact to admin  for ACTIVATION";
			this.mailSender(email, msgOTP, "INACTIVE");
			throw new CommonLoginException(msgOTP, 404, HttpStatus.BAD_REQUEST);
		}
		int otp = this.generateOTPNumber();
		String msg = hello + employeeById.getFirstName() + " " + employeeById.getLastName() + "  Your Login Otp -"
				+ otp;

		Otp otpToDeliver = new Otp();
		otpToDeliver.setEmployeeId(employeeById.getEmpId());
		otpToDeliver.setOtpNumber(otp);

		boolean mailSender = this.mailSender(email, msg, "OTP");
		if (!mailSender)
			throw new CommonLoginException("Otp can not Sent to this email", 404, HttpStatus.BAD_REQUEST);
		Date date = new Date();

		otpToDeliver.setTimestamp(new Timestamp(date.getTime()));
		otpRepository.save(otpToDeliver);

		String responseMsg = "Otp successfully  delivered to email - " + email + " please procide for otp vlidation ";
		OtpResponse otpResponse = new OtpResponse(responseMsg);
		return new CommonResponse(200, "Otp has sent successfully", otpResponse);

	}

	@Override
	public CommonResponse validateOtp(int otp, String email, String token) {
		Employee employee = this.getEmployeeById(email, token);
		Otp findByEmployeeIdAndOtp = otpRepository.findOtpByEmployeeIdAndOtp(employee.getEmpId(), otp);

		if (findByEmployeeIdAndOtp == null)
			throw new CommonLoginException(" incorrect otp  please resend it again ", 400, HttpStatus.BAD_REQUEST);

		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		long time = (timestamp.getTime() - findByEmployeeIdAndOtp.getTimestamp().getTime()) / 60000;

		if (time >= 5)
			throw new CommonLoginException(" you have crrosed otp time limit please resend it again ", 400,
					HttpStatus.BAD_REQUEST);

		otpRepository.delteOtpByEmployeeId(employee.getEmpId());
		return new CommonResponse(200, "OTP has validate  ", new OtpResponse("valid otp proccide it forword"));

	}

}