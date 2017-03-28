package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class WordController {

    @Value("${words}")
    String words;
    // String words = "icicle,refrigerator,blizzard,snowball";
    @Autowired
    DiscoveryClient client;

    @RequestMapping("/")
    public
    @ResponseBody
    Word getWord() throws Exception {

        /*long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        while (elapsedTime < 5000) {
            elapsedTime = (new Date()).getTime() - startTime;
        }*/
        ServiceInstance localInstance = client.getLocalServiceInstance();
        System.out.println("Helllooooo from ++++++"+ localInstance.getServiceId()+":"+localInstance.getHost()+":"+localInstance.getPort());
        String[] wordArray;
        int i;
        wordArray = words.split(",");
        i = (int) Math.round(Math.random() * (wordArray.length - 1));
        return new Word(wordArray[i]);

    }
}
