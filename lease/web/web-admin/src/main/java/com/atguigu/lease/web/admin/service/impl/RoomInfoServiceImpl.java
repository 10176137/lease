package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.RoomInfo;
import com.atguigu.lease.web.admin.mapper.RoomInfoMapper;
import com.atguigu.lease.web.admin.service.RoomInfoService;
import com.atguigu.lease.web.admin.vo.room.RoomItemVo;
import com.atguigu.lease.web.admin.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

	@Autowired
	private RoomInfoMapper roomInfoMapper;
	@Override
	public IPage<RoomItemVo> pageItem(long current, long size, RoomQueryVo queryVo) {
		Page<RoomInfo> page = new Page<>(current, size);
		roomInfoMapper.pageItem(page , new LambdaQueryWrapper<RoomInfo>().eq(RoomInfo::getApartmentId,queryVo.getApartmentId()) ,queryVo);
		return null;
	}
}




