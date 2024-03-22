package jmaster.io.demo.jobscheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jmaster.io.demo.entity.Bill;
import jmaster.io.demo.entity.User;
import jmaster.io.demo.repository.UserRepo;
import jmaster.io.demo.service.BillService;
import jmaster.io.demo.service.EmailService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobSchedule {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	BillService billService;

	//@Scheduled(fixedDelay = 60000) // ms
	public void hello() {
		log.info("hello");
		//emailService.testEmail();
	}

	// giay-phut-gio-ngay-thang-thu
	//@Scheduled(cron = "*/10 * * * * *") // ms
	public void morning() {
		Calendar cal=Calendar.getInstance();
		int date=cal.get(Calendar.DATE);
		int month=cal.get(Calendar.MONTH) + 1; 	//thang 1 = month 0
		
		
		List<User> users= userRepo.findByBirthdate(date, month);
		for (User u: users) {
			log.info("happy birthday " + u.getName());
			//emailService.sendBirthdayEmail(u.getEmail(),u.getName());

		}
		log.info("Good morning");
	}
	
	@Scheduled(cron = "0 */5 * * * *")
	public void billScan() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -5);
		Date date=cal.getTime();
		
		List<Bill> bills= billService.newBillScan(date);
		
		if (!bills.isEmpty()) {
		emailService.sendNewBill(bills);
		}
	}
	
	
}
