package rest;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-2-23
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public class DeviceTest {
    @Test
    public void accessUrl(){
        RestTemplate template = new RestTemplate();

        String result = template.getForObject("http://0.0.0.0:8080/service/test",
                String.class,"42", "21");
    }
}
