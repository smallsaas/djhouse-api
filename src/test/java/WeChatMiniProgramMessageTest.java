import com.jfeat.AmApplication;
import com.jfeat.am.module.house.services.common.WeChatMiniProgramMessage;
import com.jfeat.am.module.house.services.utility.HttpClientUtil;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/4/19 16:20
 * @author: hhhhhtao
 */

@SpringBootTest(classes = AmApplication.class)
public class WeChatMiniProgramMessageTest {

    /**
     * 测试获取接口调用凭据
     * 请求的是微信官方域名，不做证书验证
     * @return {access_token: 获取到的凭证，expires_in: 有效时间}
     */
    @Test
    public void should_returnHttpEntity_when_getAccessToken() {
        String result = WeChatMiniProgramMessage.getAccessToken();
        System.out.println(result);
    }
}
