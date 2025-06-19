package cn.zwz.nav.serviceimpl;

import cn.zwz.nav.mapper.SupermarketShelvesMapper;
import cn.zwz.nav.entity.SupermarketShelves;
import cn.zwz.nav.service.ISupermarketShelvesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
public class ISupermarketShelvesServiceImpl extends ServiceImpl<SupermarketShelvesMapper, SupermarketShelves> implements ISupermarketShelvesService {

    @Autowired
    private SupermarketShelvesMapper supermarketShelvesMapper;
}