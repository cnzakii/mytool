package http.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import http.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static http.ResponseStatus.FAIL;
import static http.ResponseStatus.SUCCESS;

/**
 * 响应结果封装类
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-03-17
 **/


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -1124864944635622692L;


    /**
     * 当前时间,精确到毫秒
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dateTime;

    /**
     * 状态码
     */
    private String status;

    /**
     * 状态信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;


//----------------------------------------------------------------------------------------------------------自定义响应结果

    /**
     * 自定义响应结果(状态码+状态信息)--有数据
     *
     * @param code    状态码
     * @param data    数据
     * @param message 状态信息
     * @param <T>     data数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> base(String code, T data, String message) {
        return ResponseResult.<T>builder().data(data)
                .message(message)
                .status(code)
                .dateTime(LocalDateTime.now())
                .build();
    }


    /**
     * 自定义响应结果(状态码枚举+状态信息)--有数据
     *
     * @param status  状态码枚举
     * @param data    数据
     * @param message 状态信息
     * @param <T>     data类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> base(ResponseStatus status, T data, String message) {
        return base(status.getCode(), data, message);
    }


    /**
     * 自定义响应结果(状态码枚举+状态信息)--无数据
     *
     * @param status  状态码枚举
     * @param message 状态信息
     * @param <T>     data类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> base(ResponseStatus status, String message) {
        return base(status, null, message);
    }



//------------------------------------------------------------------------------------------------------------响应成功结果

    /**
     * 响应成功结果--无数据
     *
     * @param <T> data类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    /**
     * 响应成功结果--有数据
     *
     * @param data data
     * @param <T>  data类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> success(T data) {
        return base(SUCCESS.getCode(), data, SUCCESS.getDescription());
    }


//------------------------------------------------------------------------------------------------------------响应错误结果

    /**
     * 响应错误结果(状态信息)--无数据
     *
     * @param message 错误信息
     * @param <T>     data类型
     * @return 响应结果
     */
    public static <T extends Serializable> ResponseResult<T> fail(String message) {
        return fail(null, message);
    }

    /**
     * 响应错误结果(状态信息)--有数据
     *
     * @param data    响应数据
     * @param message 错误信息
     * @param <T>     data类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> fail(T data, String message) {
        return base(FAIL.getCode(), data, message);
    }


}
