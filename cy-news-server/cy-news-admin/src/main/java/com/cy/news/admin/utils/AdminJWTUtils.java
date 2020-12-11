package com.cy.news.admin.utils;

import com.cy.news.common.Pojo.Admin;
import com.cy.news.common.Pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import java.util.Date;

/**
 * @author yj
 * @ClassName JWTUtils
 * @date 2020/12/5  15:56
 * @Version V1.0
 * @Description: TODO
 */
public class AdminJWTUtils {

    public static final String HASH_KEY="cy-newsLZNB6661";


    public static String adminLoginJwtString(Admin admin){
        long now=System.currentTimeMillis();
        long exp=now+1000*60*300;
        JwtBuilder builder= Jwts.builder().setId(admin.getAId().toString())
                .setIssuedAt(new Date())
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
