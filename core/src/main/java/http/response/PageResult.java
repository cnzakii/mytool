package http.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页查询通用返回类
 * <p>
 * 本类不仅返回分页查询所需要返回的属性外，还返回了限制的时间戳<br>
 * 防止当数据库有新数据插入时，分页结果出现相同数据的情况
 * </p>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-02
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -978729922275287338L;

    /**
     * 限制时间戳
     */
    private Long limitTimestamp;

    /**
     * 总页数,如果为null,则在转换成Json时移除该字段
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPage;

    /**
     * 每页显示的记录数
     */
    private Integer pageSize;

    /**
     * 当前页数
     */
    private Integer currentPage;

    /**
     * 分页结果
     */
    private List<T> result;


}