package TestCases;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.Myrestdemo.dto.RegisterDTO;
import com.Myrestdemo.dto.StatusDTO;
import com.Myrestdemo.service.RegisterService;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(SpringRunner.class)
public class RegisterServiceTest {

    @Test
    public void testRegisterServiceCall() throws Exception {

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setFirstname("test");
        registerDTO.setLastname("test");
        registerDTO.setMiddlename("test");
        registerDTO.setPassword("123456");

        int value = ThreadLocalRandom.current().nextInt(1, 1000000);
        String random = Long.toHexString(Double.doubleToLongBits(Math.random()));

        registerDTO.setEmail("test" + value + "@" + random + ".com");

        RegisterService registerService = new RegisterService();

        StatusDTO statusDTO = registerService.register(registerDTO);

        Assert.assertEquals(statusDTO.getCode(), 200);
        Assert.assertEquals(statusDTO.getStatus(), "SUCCESS");
        Assert.assertTrue(StringUtils.isBlank(statusDTO.getError()));
    }

    @Test
    public void testRegisterServiceDuplicateEmail() throws Exception {

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setFirstname("test");
        registerDTO.setLastname("test");
        registerDTO.setMiddlename("test");
        registerDTO.setPassword("123456");
        registerDTO.setEmail("test@gmail.com");

        RegisterService registerService = new RegisterService();

        StatusDTO statusDTO = registerService.register(registerDTO);

        Assert.assertEquals(statusDTO.getCode(), 200);
        Assert.assertEquals(statusDTO.getStatus(), "FAIL");
        Assert.assertTrue(statusDTO.getError().contains("There is already an account with this email address."));
    }
}
