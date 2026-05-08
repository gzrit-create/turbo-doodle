package com.accountBook.accountBook.utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 过滤器
 * 判断请求
 */


@Component
public class JwtUtil {
    private static final String SECRET="yourSecretKeyForJwtThatIsAtLeast32BytesLong";

    private static final long EXPIRATION_TIME=3600000;//1hour
    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username,Long userId){
        return Jwts.builder()//创建一个空白类似腾出内存
                .setSubject(username)//标准字段sub，标注持有人
                .claim("userId",userId)//自定义字段，定义userId
                .setIssuedAt(new Date())//许可发送时间，iat字段
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))//过期时间
                .signWith(getKey())//获取签名
                .compact();//将上述内容打包为字符串
    }

    public String getUsernameFromToken(String token){
        Claims claims=Jwts.parserBuilder()//创建一个空白内容
                .setSigningKey(getKey())//获取token的内容
                .build()//解析器
                .parseClaimsJws(token)
                .getBody();//获取token的内容
        return claims.getSubject();//返回出去
    }

    public Long getUserIdFromToken(String token){
        Claims claims=Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId",Long.class);//规定返回的内容必须是long里的
    }
//检测token
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
            return true;
        }catch(Exception e){//这里可以增加更加细致的异常逻辑检测和处理，等我先研究一下
            return false;
        }
    }
}
