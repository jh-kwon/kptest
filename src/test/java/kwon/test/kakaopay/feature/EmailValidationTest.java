package kwon.test.kakaopay.feature;

import kwon.test.kakaopay.service.EmailValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
public class EmailValidationTest {
    @TestConfiguration
    static class EmailValidationTestConfig{
        @Bean
        public EmailValidationService emailValidationService(){
            return new EmailValidationService();
        }
    }

    @Autowired EmailValidationService emailValidationService;


    @Test
    public void testEmailValidation(){
        String email = "a@ac.kr";

        boolean rt = emailValidationService.validateEmailAddressForm(email);

        assertThat(rt).isEqualTo(true);
    }

    @Test
    public void testWrongEmailValidation(){
        String email = "_a@ac.kr";

        boolean rt = emailValidationService.validateEmailAddressForm(email);

        assertThat(rt).isEqualTo(false);
    }
}
