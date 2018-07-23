package com.fw121.core.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mvt-zhaosandong-mac on 2018/7/16.
 */
public class Md5SaltToolTest {

    private static Map users = new HashMap();

    public static void main(String[] args){
        String userName = "zyg";
        String password = "test";
        registerUser(userName,password);

        userName = "changong";
        password = "456";
        registerUser(userName,password);

        String loginUserId = "zyg";
        String pwd = "1232";
        try {
            if(loginValid(loginUserId,pwd)){
                System.out.println("欢迎登陆！！！");
            }else{
                System.out.println("口令错误，请重新输入！！！");
            }

            testValid();

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 注册用户
     *
     * @param userName
     * @param password
     */
    public static void registerUser(String userName,String password){
        String encryptedPwd = null;
        encryptedPwd = Md5SaltTool.getEncryptedPwd(password);
        System.out.println();
        System.out.println("password: " + password);
        System.out.println("encryptedPwd: " + encryptedPwd);
        System.out.println();
        users.put(userName, encryptedPwd);
    }

    /**
     * 验证登陆
     *
     * @param userName
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static boolean loginValid(String userName,String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
             /*String loginUserId = "zyg";
               String pwd = "1232";*/
        String pwdInDb = (String)users.get(userName);
        if(null!=pwdInDb){ // 该用户存在
            return Md5SaltTool.validPassword(password, pwdInDb);
        }else{
            System.out.println("不存在该用户！！！");
            return false;
        }
    }

    public static void testValid() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String pwd = "123";
        String pwdInDb = "882182CB947E5C34E471C8D4A4AABE38485615A7B8D782E8294E2E31";
        System.out.println("valid pwd 123: " + Md5SaltTool.validPassword(pwd, pwdInDb));
        pwdInDb = "8147905896976246ADADC14BDAB84D116133B886ED493B470E77D0DE";
        System.out.println("valid pwd 123: " + Md5SaltTool.validPassword(pwd, pwdInDb));
        pwdInDb = "5B8FCF58F279D5BBF4C24C1C59F46D2D84D19EFF3202E057BDB8DD6C";
        System.out.println("valid pwd 123: " + Md5SaltTool.validPassword(pwd, pwdInDb));

        pwd = "456";
        pwdInDb = "092E2B5215A28D182497A86A33CF736B3D2F5E422198A306DDF04980";
        System.out.println("valid pwd 456: " + Md5SaltTool.validPassword(pwd, pwdInDb));
        pwdInDb = "F16EE1E4651B052795DBD62B80A1291C800F7612C0CCEA339E967058";
        System.out.println("valid pwd 456: " + Md5SaltTool.validPassword(pwd, pwdInDb));
        pwdInDb = "51C6EBCEC6A42A5054366727BDDDE2E066B6AB067718250DA2542704";
        System.out.println("valid pwd 456: " + Md5SaltTool.validPassword(pwd, pwdInDb));
        pwdInDb = "5B8FCF58F279D5BBF4C24C1C59F46D2D84D19EFF3202E057BDB8DD6C";
        System.out.println("valid pwd 456 use 123's password: " + Md5SaltTool.validPassword(pwd, pwdInDb));
    }

}