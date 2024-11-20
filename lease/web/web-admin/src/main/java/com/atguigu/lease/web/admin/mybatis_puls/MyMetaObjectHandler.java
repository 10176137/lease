package com.atguigu.lease.web.admin.mybatis_puls;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {
		strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		MetaObjectHandler updateTime = strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
	}
}
