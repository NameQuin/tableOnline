package team.tb.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * JWT工具类，常用的操作存在于其中
 */
public class JWTUtils {

    private static String TOKEN = "token!Q@w3e4r";

    /**
     * 生成token
     * @param map 传入的payload
     * @return 生成的token
     */
    public static String getToken(Map<String, String> map){
        JWTCreator.Builder builder = JWT.create();
//        map.forEach(builder::withClaim);
        map.forEach(builder::withClaim);
        // 箭头函数 (k, v) -> {builder.withClaim(k, v)}
        Calendar calendar = Calendar.getInstance();
        // 设置过期时间为七天
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        builder.withExpiresAt(calendar.getTime());
        return builder.sign(Algorithm.HMAC256(TOKEN)).toString();

    }

    /**
     * 验证传入的token
     * @param token 待验证的token
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }

    /**
     * 获取token的解码后的对象
     * @param token 传入的token
     * @return token中的payload
     */
    public static DecodedJWT getToken(String token){
        return JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }

    /**
     * 获取payload中的参数
     * @param token 传入服务器的token
     * @param key payload中的键
     * @return payload中相应键对应的值
     */
    public static String getClaim(String token, String key){
        return JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token).getClaim(key).asString();
    }
}
