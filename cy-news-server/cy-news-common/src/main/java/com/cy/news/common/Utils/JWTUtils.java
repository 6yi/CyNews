package com.cy.news.common.Utils;

import com.cy.news.common.Pojo.User;
import io.jsonwebtoken.*;
import java.util.Date;

/**
 * @ClassName JWTUtils
 * @Author 6yi
 * @Date 2020/11/21 17:05
 * @Version 1.0
 * @Description:
 */


public class JWTUtils {

    public static final String HASH_KEY="cy-newsLZNB666";


    public static String userLoginJwtString(User user){
        long now=System.currentTimeMillis();
        long exp=now+1000*60*30;
        JwtBuilder builder= Jwts.builder().setId(user.getuId().toString())
                .setIssuedAt(new Date())
                .claim("nickName",user.getuNickname())
                .signWith(SignatureAlgorithm.HS256,HASH_KEY)
                .setExpiration(new Date(exp));//设置过期时间
       return builder.compact();
    }


    public  static Claims verifiedJwt(String jwt) throws Exception{
        Claims claims=null;
        try{
            claims = Jwts.parser().setSigningKey(HASH_KEY).parseClaimsJws(jwt).getBody();
        }catch (Exception e){
            throw new Exception("授权错误");
        }
        if (claims.getExpiration().getTime()<System.currentTimeMillis()){
            throw new Exception("授权过期");
        }
        return claims;
    }

//    public static void main(String[] args) {
//
////        User build = User.builder().uNickname("?").build();
////
////        System.out.println(new JWTUtils().userLogin(build));
//
//        Claims body = Jwts.parser()
//                .setSigningKey("cy-newsLZNB666")
//                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI_Iiwic3ViIjoi5bCP55m9IiwiaWF0IjoxNjA1OTUwMzA4LCJleHAiOjE2MDU5NTAzMDh9.OLQTJWPpg01UAavY2emnBeOm6820E_aQshYa5GaFptP0")
//                .getBody();
//        System.out.println(body);
//
//    }

}
