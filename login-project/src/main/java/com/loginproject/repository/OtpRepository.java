package com.loginproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.loginproject.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
	final String QUERY_FOR_FIND_OTP_BY_EMPID = "SELECT   * FROM `otp` WHERE otp=:otp AND employee_id=:employeeId ORDER BY id DESC LIMIT 1;";
	final String QUERY_FOR_DELETE = "DELETE FROM `otp` WHERE  employee_id=:employeeId ";
	final String QUERY_FOR_COUNT = "SELECT COUNT(id) FROM `otp` WHERE employee_id=:employeeId";

	@Query(value = QUERY_FOR_FIND_OTP_BY_EMPID, nativeQuery = true)
	public Otp findOtpByEmployeeIdAndOtp(long employeeId, int otp);

	@Transactional
	@Modifying
	@Query(value = QUERY_FOR_DELETE, nativeQuery = true)
	public void delteOtpByEmployeeId(long employeeId);

	@Query(value = QUERY_FOR_COUNT, nativeQuery = true)
	public int countOfOtpPerEmployee(long employeeId);

}
