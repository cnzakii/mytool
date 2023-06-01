package exception;

import http.ResponseStatus;
import lombok.Getter;

/**
 * 业务异常类
 * <p>
 * 继承RuntimeException类<br>
 * 添加了状态码和描述属性，优雅的抛出业务异常
 * </p>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-03-17
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 状态码
     */
    private final String code;

    /**
     * 异常描述信息
     */
    private final String description;

    public BusinessException(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public BusinessException(ResponseStatus errorCode) {
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ResponseStatus errorCode, String description) {
        this.code = errorCode.getCode();
        this.description = description;
    }


}
