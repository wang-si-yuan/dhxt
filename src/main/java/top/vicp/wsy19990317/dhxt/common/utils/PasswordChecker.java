package top.vicp.wsy19990317.dhxt.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 验证密码是否符合规则
 */
@Component
public class PasswordChecker {

    @Value("${passwordChecker.upperCase}")
    private boolean upperCase; // 包含大写字母

    @Value("${passwordChecker.lowerCase}")
    private boolean lowerCase; // 包含小写字母

    @Value("${passwordChecker.letter}")
    private boolean letter; // 包含字母

    @Value("${passwordChecker.digit}")
    private boolean digit; // 包含数字

    @Value("${passwordChecker.special}")
    private boolean special; // 包含特殊字符

    private Set<Character> specialCharSet=PasswordChecker.defaultSpecialCharSet(); // 特殊字符集合

    @Value("${passwordChecker.minLength}")
    private int minLength; // 最小长度

    @Value("${passwordChecker.maxLength}")
    private int maxLength; // 最大长度

    public PasswordChecker(){
        this.specialCharSet = defaultSpecialCharSet();
    }

    /**
     * 密码符合规则，返回true
     */
    public boolean check(String password){
        if(password==null || password.length()<this.minLength || password.length()>this.maxLength){
            // 长度不符合
            return false;
        }

        boolean containUpperCase = false;
        boolean containLowerCase = false;
        boolean containLetter = false;
        boolean containDigit = false;
        boolean containSpecial = false;

        for(char ch : password.toCharArray()){
            if(Character.isUpperCase(ch)){
                containUpperCase = true;
                containLetter = true;
            }else if(Character.isLowerCase(ch)){
                containLowerCase = true;
                containLetter = true;
            }else if(Character.isDigit(ch)){
                containDigit = true;
            }else if(this.specialCharSet.contains(ch)){
                containSpecial = true;
            }else{
                // 非法字符
                return false;
            }
        }

        if(this.upperCase && !containUpperCase){
            return false;
        }
        if(this.lowerCase && !containLowerCase){
            return false;
        }
        if(this.letter && !containLetter){
            return false;
        }
        if(this.digit && !containDigit){
            return false;
        }
        if(this.special && !containSpecial){
            return false;
        }
        return true;
    }

    public static Set<Character> defaultSpecialCharSet(){
        Set<Character> specialChars = new LinkedHashSet<>();
        // 键盘上能找到的符号
        specialChars.add(Character.valueOf('.'));
        specialChars.add(Character.valueOf('_'));
        return specialChars;
    }
}
