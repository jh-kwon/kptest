package kwon.test.kakaopay.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class EmailValidationService {
	
	/**
     * Regular Expression for Email
     * : below Regular Expression for Email Address Format supposes : can be adjusted...
     * 1) email must start with at least one character of Alphabet or digit(number)
     * 2) assume just allow most common printable character : alphabet, digit(number) and some most common used special symbol(ex: !#$%*+=~._-)
     * 3) but rule is stricter after @ :
     *  3-1) at least, must consist of two parts divided by dot(.)
     *  3-2) before last of dot(.), only allow alphabet, digit(number) and .-_, BUT can only start with alphabet or digit
     *  3-3) after last of dot(.), only allow alphabet & length is greater than 2 and less than 10
     */
	private static final String emailRegex ="(?:[a-z0-9]+(?:[a-z0-9!#$%*+=~._-]+)*)@(?:[a-z0-9A-Z](?:[a-z0-9A-Z_-]*[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,10}";
	private static final Pattern emailRegexPattern = Pattern.compile(emailRegex);
	public boolean validateEmailAddressForm(String src) {
		boolean rt = false;
		
		if(src == null || src.trim().isEmpty()){
            return rt;
        }

        Matcher matcher = emailRegexPattern.matcher(src);
        if(matcher.matches()){
            rt = true;
        }
		
		return rt;
	}
	
	public boolean validationEmailAddressDuplicated(String src) {
		boolean rt = false;
		
		//TODO check does email already used(i.e. duplicated)? -- with DB
		
		return rt;
	}

}
