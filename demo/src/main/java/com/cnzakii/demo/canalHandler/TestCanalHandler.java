package com.cnzakii.demo.canalHandler;

import com.cnzakii.annotation.CanalTable;
import com.cnzakii.common.AbstractCanalHandler;
import com.cnzakii.demo.entity.User;
import org.springframework.stereotype.Component;

/**
 * 测试MQ接收到canal 传输的数据的 处理程序
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-04
 **/
@CanalTable("t_user")
@Component
public class TestCanalHandler extends AbstractCanalHandler<User> {


    @Override
    public void insert(User data) {

    }

    @Override
    public void update(User oldData, User newData) {
        System.out.println("开始处理更新数据");
        System.out.println("这是老数据： " + oldData);
        System.out.println("这是新数据： " + newData);

    }

    @Override
    public void delete(User data) {

    }
}
