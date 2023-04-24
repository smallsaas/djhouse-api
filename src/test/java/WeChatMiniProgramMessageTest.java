import com.alibaba.fastjson.JSONObject;
import com.jfeat.AmApplication;
import com.jfeat.wechatmessage.common.WeChatMiniProgramMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/4/19 16:20
 * @author: hhhhhtao
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeChatMiniProgramMessageTest {

    @Value("${house.appid:}")
    String appid;
    @Value("${house.appSecret:}")
    String appSecret;
    @Value("${template.orderProgress.id:}")
    String templateId;
    String openid = "oyKg55Y_wYntW2DIhZlq7DCEGpSs";

    @Resource
    WeChatMiniProgramMessage weChatMiniProgramMessage;

    /**
     * 测试获取接口调用凭据
     * 请求的是微信官方域名，不做证书验证
     * @return {access_token: 获取到的凭证，expires_in: 有效时间}
     */
    @Test
    public void should_returnHttpEntity_when_getAccessToken() {
        String result =  weChatMiniProgramMessage.getAccessToken(appid,appSecret);
        System.out.println(result);
    }

    /**
     * 测试发送微信订阅消息
     */
    @Test
    public void should_retuanHttpEntity_when_sendMessage() {

        // 封装消息内容
        JSONObject messageContent = new JSONObject();
        // 获取当前时间
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = LocalDateTime.now().format(dateTimeFormatter);
        JSONObject time3 = new JSONObject();
        time3.put("value",dateTime);
        messageContent.put("time3",time3);
        // 订单进度
        JSONObject thing4 = new JSONObject();
        thing4.put("value","待确认");
        messageContent.put("thing4",thing4);
        // 订单编号
        JSONObject character_string5 = new JSONObject();
        character_string5.put("value","1111111");
        messageContent.put("character_string5",character_string5);
        // 产品名称
        JSONObject thing10 = new JSONObject();
        thing10.put("value","团购商品");
        messageContent.put("thing10",thing10);

        weChatMiniProgramMessage.sendMessage(appid,appSecret,openid,templateId,messageContent);
    }
}
