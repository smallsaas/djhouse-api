import com.jfeat.AmApplication;
import com.jfeat.am.module.house.services.common.WeChatMiniProgramMessage;
import com.jfeat.am.module.house.services.utility.HttpClientUtil;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;

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

    @Resource
    WeChatMiniProgramMessage weChatMiniProgramMessage;

    /**
     * 测试获取接口调用凭据
     * 请求的是微信官方域名，不做证书验证
     * @return {access_token: 获取到的凭证，expires_in: 有效时间}
     */
    @Test
    public void should_returnHttpEntity_when_getAccessToken() {
        String result =  weChatMiniProgramMessage.getAccessToken();
        System.out.println(result);
    }

    /**
     * 测试发送微信订阅消息
     */
    @Test
    public void should_retuanHttpEntity_when_sendMessage() {
//        weChatMiniProgramMessage.sendMessage();
    }
}
