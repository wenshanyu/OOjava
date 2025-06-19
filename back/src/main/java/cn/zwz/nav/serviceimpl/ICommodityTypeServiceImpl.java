package cn.zwz.nav.serviceimpl;

import cn.zwz.nav.mapper.CommodityTypeMapper;
import cn.zwz.nav.entity.CommodityType;
import cn.zwz.nav.service.ICommodityTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
public class ICommodityTypeServiceImpl extends ServiceImpl<CommodityTypeMapper, CommodityType> implements ICommodityTypeService {

    @Autowired
    private CommodityTypeMapper commodityTypeMapper;
}