import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfeat.AmApplication;
import com.jfeat.am.module.house.services.domain.service.HouseEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/6/2 17:45
 * @author: hhhhhtao
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UseKafkaSendEmailTest {

    @Resource
    HouseEmailService houseEmailService;

    @Test
    public void should_returnSuccess_when_ordinaryExecution_given_customizationEmailContent() {

        String emailTitle = "测试";
        String emailContent = "【测试】公共自定义邮件接口测试";
        List<String>  toEmailAddressList = new ArrayList<String>();
        toEmailAddressList.add("3080348136@qq.com");
        toEmailAddressList.add("1152808759@qq.com");
        toEmailAddressList.add("1799231213@qq.com");

        houseEmailService.sendEmailByCustomization(emailTitle,emailContent,toEmailAddressList);
    }

    @Test
    public void should_returnSuccess_when_ordinaryExecution_given_toEmailAddressListIsNull() {

        String emailTitle = "测试";
        String emailContent = "【测试】公共自定义邮件接口测试";
        List<String>  toEmailAddressList = new ArrayList<String>();

        houseEmailService.sendEmailByCustomization(emailTitle,emailContent,toEmailAddressList);
    }
}
