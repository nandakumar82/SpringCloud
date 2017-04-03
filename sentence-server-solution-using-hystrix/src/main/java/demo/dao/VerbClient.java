package demo.dao;

import demo.domain.Word;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("VERB")
public interface VerbClient {

	@RequestMapping(value="/word", method=RequestMethod.GET)
	Word getWord();
	
}
