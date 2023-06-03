package http;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 常见状态码枚举类
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-03-17
 **/

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    SUCCESS("200", "success"),                      // 请求成功
    FAIL("501", "failed"),                          // 请求失败
    REQUEST_ERROR("400", "Request Error"),           // 请求参数错误
    NO_AUTHENTICATION("401", "No Authentication"),  // 没有认证(token)
    NO_AUTHORITIES("403", "No Authorities"),        // 没有权限
    SERVER_ERROR("500", "Server Error"),            // 服务器内部错误
    TOO_MANY_REQUESTS("429", "Too Many Requests");// 过多请求


    /**
     * 响应状态码
     */
    private final String code;

    /**
     * 状态描述信息
     */
    private final String description;
}
