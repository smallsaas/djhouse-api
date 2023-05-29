import com.jfeat.AmApplication;
import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionService;
import com.jfeat.am.module.house.services.domain.service.impl.HouseAssetTransactionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/5/29 17:42
 * @author: hhhhhtao
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HouseAssetTransactionTest {

    @Resource
    HouseAssetTransactionService houseAssetTransactionService;

    @Test
    public void should_success_when_manualTest_given_followConfigGetDownShelfTimeCycle() {

        houseAssetTransactionService.pulledOffShelvesTransaction();
    }
}
