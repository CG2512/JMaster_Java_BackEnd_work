package jmaster.io.demo.jobscheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jmaster.io.demo.dto.BillDTO;
import jmaster.io.demo.dto.MessageDTO;
import jmaster.io.demo.repository.UserRepo;
import jmaster.io.demo.service.BillService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobSchedule {

	@Autowired
	UserRepo userRepo;

	@Autowired
	BillService billService;

	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;

	@Scheduled(cron = "0 */5 * * * *")
	public void billScan() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -5);
		Date date = cal.getTime();
		List<BillDTO> bills = billService.newBillScan(date);

		if (!bills.isEmpty()) {
			MessageDTO sendBill = new MessageDTO();
			sendBill.setData(bills);
			kafkaTemplate.send("billScan", sendBill);
		}
	}

}
