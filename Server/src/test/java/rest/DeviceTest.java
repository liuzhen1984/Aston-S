package rest;

import com.silveroaklabs.domain.DeviceObj;
import com.silveroaklabs.domain.Result;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-2-23
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public class DeviceTest {
    @Test
    public void accessUrl() throws UnsupportedEncodingException {
        String str = "[0,1,1,[1,34]]";
        DeviceObj deviceObj = new DeviceObj();
        deviceObj.setMac("0000.0000.0000");
        List<DeviceObj> ds = new ArrayList<DeviceObj>();
        ds.add(deviceObj);
        Result result = new Result();
        result.setData(ds);
        System.out.println(result.getData());

    }

}
