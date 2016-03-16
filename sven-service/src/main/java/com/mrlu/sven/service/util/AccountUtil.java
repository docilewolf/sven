package com.mrlu.sven.service.util;

import com.mrlu.sven.domain.Account;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * Created by stefan on 16-3-14.
 */
public class AccountUtil {
    private static final char[] baseChars = new char[]{
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            0,1,2,3,4,5,6,7,8,9
    };

    /**
     * 生成cookie,32位 10位 random(md5(username)) + '-' + 10位 randomUUID()+ '-' +  10位 random(md5(password))
     * @param account
     * @return
     */
    public static String generateSessionId(Account account){
        StringBuffer sb = new StringBuffer(16);
        String userNamemd5 = DigestUtils.md5Hex(account.getUserName());
        for(int i=0; i<10; i++){
            sb.append(userNamemd5.charAt((int)(Math.random() * 32)));
        }
        sb.append('-');
        sb.append(UUID.randomUUID().toString().substring(0, 10) + "-");
        String passwordmd5 = generatePassword(account.getPassword());
        for(int i=0; i<10; i++){
            sb.append(passwordmd5.charAt((int)(Math.random() * 32)));
        }

        if(sb.length()<32){
            int lackLength = 32 - sb.length();
            for(int i=0; i<lackLength; i++){
                sb.append(baseChars[(int)(Math.random()*52)]);
            }
        }
        return sb.toString();
    }

    /**
     * 密码加密, 32位 md5
     * @param password
     * @return
     */
    public static String generatePassword(String password){
        return DigestUtils.md5Hex(password);
    }

    public static void main(String[] args){
        Account account = new Account();
        account.setUserName("b");
        account.setPassword("123456a");
        System.out.println(AccountUtil.generateSessionId(account));
        System.out.println(AccountUtil.generatePassword(account.getPassword()));
    }
}
