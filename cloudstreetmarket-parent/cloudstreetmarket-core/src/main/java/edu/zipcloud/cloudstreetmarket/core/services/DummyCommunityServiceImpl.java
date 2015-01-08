package edu.zipcloud.cloudstreetmarket.core.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.enums.Action;

@Service
public class DummyCommunityServiceImpl implements ICommunityService {
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	@Override
	public List<UserActivityDTO> getLastUserPublicActivity(int number){
		List<UserActivityDTO> result = Arrays.asList(
			new UserActivityDTO("happyFace8", "img/young-lad.jpg", Action.BUY, "NXT.L", 6, new BigDecimal(3), LocalDateTime.parse("2015-11-15 11:12", formatter)),
			new UserActivityDTO("userB", null, Action.SELL, "CCH.L", 7, new BigDecimal(12), LocalDateTime.parse("2015-11-15 10:46", formatter)),
			new UserActivityDTO("other9", "img/santa.jpg", Action.BUY, "KGF.L", 6, new BigDecimal(9.5), LocalDateTime.parse("2015-11-15 10:46", formatter)),
			new UserActivityDTO("randomGuyy34", null, Action.BUY, "III.L", 6, new BigDecimal(32), LocalDateTime.parse("2015-11-15 09:55", formatter)),
			new UserActivityDTO("traderXX", null, Action.BUY, "BLND.L", 6, new BigDecimal(15), LocalDateTime.parse("2015-11-15 09:50", formatter)),
			new UserActivityDTO("userB", null, Action.BUY, "AAL.L", 6, new BigDecimal(7), LocalDateTime.parse("2015-11-15 09:46", formatter))
		);
		return result;
	}

}
