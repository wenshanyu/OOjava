package cn.zwz.nav.serviceimpl;

import cn.zwz.nav.mapper.SupermarketAreaMapper;
import cn.zwz.nav.entity.SupermarketArea;
import cn.zwz.nav.service.ISupermarketAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
public class ISupermarketAreaServiceImpl extends ServiceImpl<SupermarketAreaMapper, SupermarketArea> implements ISupermarketAreaService {

    @Autowired
    private SupermarketAreaMapper supermarketAreaMapper;
}