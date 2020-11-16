//package com.oss.tool.util;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//public class JwtHelperUtil {
//    Logger LOGGER = LoggerFactory.getLogger(JwtHelper.class);
//
//    public static String generateJWT(String userId, String userName, String ...identities) {
//        //签名算法，选择SHA-256
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//        //获取当前系统时间
//        long nowTimeMillis = System.currentTimeMillis();
//        Date now = new Date(nowTimeMillis);
//        //将BASE64SECRET常量字符串使用base64解码成字节数组
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(AESSecretUtil.BASE64SECRET);
//        //使用HmacSHA256签名算法生成一个HS256的签名秘钥Key
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
//        //添加构成JWT的参数
//        Map<String, Object> headMap = new HashMap<>();
//        /*
//            Header
//            {
//              "alg": "HS256",
//              "typ": "JWT"
//            }
//         */
//        headMap.put("alg", SignatureAlgorithm.HS256.getValue());
//        headMap.put("typ", "JWT");
//        JwtBuilder builder = Jwts.builder().setHeader(headMap)
//                /*
//                    Payload
//                    {
//                      "userId": "1234567890",
//                      "userName": "John Doe",
//                    }
//                 */
//                //加密后的客户编号
//                .claim("userId", AESSecretUtil.encryptToStr(userId, AESSecretUtil.DATAKEY))
//                //客户名称
//                .claim("userName", userName)
//                //客户端浏览器信息
////                .claim("userAgent", identities[0])
//                //Signature
//                .signWith(signatureAlgorithm, signingKey);
//        //添加Token过期时间
////        if (AESSecretUtil.EXPIRESSECOND >= 0) {
////            long expMillis = nowTimeMillis + AESSecretUtil.EXPIRESSECOND;
////            Date expDate = new Date(expMillis);
////            builder.setExpiration(expDate).setNotBefore(now);
////        }
//        return builder.compact();
//    }
//
//    /**
//     * @Author: Helon
//     * @Description: 解析JWT
//     * 返回Claims对象
//     * @param jsonWebToken - JWT
//     * @Data: 2018/7/28 19:25
//     * @Modified By:
//     */
//    public static Claims parseJWT(String jsonWebToken) {
//        Claims claims = null;
//        try {
//            if (ValidateUtil.isNotEmpty(jsonWebToken)) {
//                //解析jwt
//                claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(AESSecretUtil.BASE64SECRET))
//                        .parseClaimsJws(jsonWebToken).getBody();
//            }else {
//                System.out.println("[JWTHelper]-json web token 为空");
////                logger.warn("[JWTHelper]-json web token 为空");
//            }
//        } catch (Exception e) {
//            System.out.println("[JWTHelper]-JWT解析异常：可能因为token已经超时或非法token");
////            logger.error("[JWTHelper]-JWT解析异常：可能因为token已经超时或非法token");
//        }
//        return claims;
//    }
//
//    /**
//     * @Author: Helon
//     * @Description: 校验JWT是否有效
//     * 返回json字符串的demo:
//     * {"freshToken":"A.B.C","userName":"Judy","userId":"123", "userAgent":"xxxx"}
//     * freshToken-刷新后的jwt
//     * userName-客户名称
//     * userId-客户编号
//     * userAgent-客户端浏览器信息
//     * @param jsonWebToken - JWT
//     * @Data: 2018/7/24 15:28
//     * @Modified By:
//     */
//    public static String validateLogin(String jsonWebToken) {
//        Map<String, Object> retMap = null;
//        Claims claims = parseJWT(jsonWebToken);
//        if (claims != null) {
//            //解密客户编号
//            String decryptUserId = AESSecretUtil.decryptToStr((String)claims.get("userId"), AESSecretUtil.DATAKEY);
//            retMap = new HashMap<>();
//            //加密后的客户编号
//            retMap.put("userId", decryptUserId);
//            //客户名称
//            retMap.put("userName", claims.get("userName"));
//            //客户端浏览器信息
////            retMap.put("userAgent", claims.get("userAgent"));
//            //刷新JWT
////            retMap.put("freshToken", generateJWT(decryptUserId, (String)claims.get("userName"), (String)claims.get("userAgent"), (String)claims.get("domainName")));
//        }else {
////            logger.warn("[JWTHelper]-JWT解析出claims为空");
//            System.out.println("[JWTHelper]-JWT解析出claims为空");
//        }
//        return retMap!=null?new JsonObject(retMap).toString():null;
//    }
//
//    public static void main(String[] args) {
//        String jsonWebKey = generateJWT("1", "wbh",
//                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
//        System.out.println("加密: "+ jsonWebKey);
//        Claims claims =  parseJWT(jsonWebKey);
//        System.out.println("解密: "+claims);
//        System.out.println("验证" +validateLogin(jsonWebKey));
//    }
//}
