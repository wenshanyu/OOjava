package cn.zwz.nav.controller;

import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.nav.entity.SupermarketArea;
import cn.zwz.nav.entity.SupermarketShelves;
import cn.zwz.nav.service.ISupermarketAreaService;
import cn.zwz.nav.service.ISupermarketShelvesService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


@Slf4j
@RestController
@Api(tags = "超市货架管理接口")
@RequestMapping("/zwz/supermarketShelves")
@Transactional
public class SupermarketShelvesController {

    @Autowired
    private ISupermarketShelvesService iSupermarketShelvesService;

    @Autowired
    private ISupermarketAreaService iSupermarketAreaService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条超市货架")
    public Result<SupermarketShelves> get(@RequestParam String id){
        return new ResultUtil<SupermarketShelves>().setData(iSupermarketShelvesService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部超市货架个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iSupermarketShelvesService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部超市货架")
    public Result<List<SupermarketShelves>> getAll(){
        return new ResultUtil<List<SupermarketShelves>>().setData(iSupermarketShelvesService.list());
    }

    @RequestMapping(value = "/getAllOnWeb", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部超市货架")
    public Result<List<SupermarketShelves>> getAllOnWeb(@RequestParam String title, @RequestParam String area){
        QueryWrapper<SupermarketShelves> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(title)) {
            qw.like("title",title);
        }
        if(!ZwzNullUtils.isNull(area)) {
            qw.like("area",area);
        }
        qw.orderByAsc("sort_order");
        return new ResultUtil<List<SupermarketShelves>>().setData(iSupermarketShelvesService.list(qw));
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询超市货架")
    public Result<IPage<SupermarketShelves>> getByPage(@ModelAttribute SupermarketShelves supermarketShelves ,@ModelAttribute PageVo page){
        QueryWrapper<SupermarketShelves> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(supermarketShelves.getCode())) {
            qw.like("code",supermarketShelves.getCode());
        }
        if(!ZwzNullUtils.isNull(supermarketShelves.getTitle())) {
            qw.like("title",supermarketShelves.getTitle());
        }
        if(!ZwzNullUtils.isNull(supermarketShelves.getStatus())) {
            qw.eq("status",supermarketShelves.getStatus());
        }
        if(!ZwzNullUtils.isNull(supermarketShelves.getAreaId())) {
            qw.eq("area_id",supermarketShelves.getAreaId());
        }
        IPage<SupermarketShelves> data = iSupermarketShelvesService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<SupermarketShelves>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改超市货架")
    public Result<SupermarketShelves> saveOrUpdate(SupermarketShelves supermarketShelves){
        if(iSupermarketShelvesService.saveOrUpdate(supermarketShelves)){
            return new ResultUtil<SupermarketShelves>().setData(supermarketShelves);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增超市货架")
    public Result<SupermarketShelves> insert(SupermarketShelves supermarketShelves){
        // 处理编码问题
        String today = "HJ" + (Calendar.getInstance().get(Calendar.YEAR) % 100) + "" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "" + (Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        QueryWrapper<SupermarketShelves> ssQw = new QueryWrapper<>();
        ssQw.like("code",today);
        supermarketShelves.setCode(today + String.format("%05d", iSupermarketShelvesService.count(ssQw) + 1));
        // 处理排序值问题
        if(Objects.equals(0,supermarketShelves.getSortOrder().compareTo(BigDecimal.ZERO))) {
            supermarketShelves.setSortOrder(BigDecimal.valueOf(iSupermarketShelvesService.count() + 1));
        }
        // 判断区域
        SupermarketArea area = iSupermarketAreaService.getById(supermarketShelves.getAreaId());
        if(area == null) {
            return ResultUtil.error("超市区域不存在");
        }
        supermarketShelves.setArea(area.getTitle());
        iSupermarketShelvesService.saveOrUpdate(supermarketShelves);
        return new ResultUtil<SupermarketShelves>().setData(supermarketShelves);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑超市货架")
    public Result<SupermarketShelves> update(SupermarketShelves supermarketShelves){
        // 判断区域
        SupermarketArea area = iSupermarketAreaService.getById(supermarketShelves.getAreaId());
        if(area == null) {
            return ResultUtil.error("超市区域不存在");
        }
        supermarketShelves.setArea(area.getTitle());
        // 持久化
        iSupermarketShelvesService.saveOrUpdate(supermarketShelves);
        return new ResultUtil<SupermarketShelves>().setData(supermarketShelves);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除超市货架")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iSupermarketShelvesService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/initNotLogin", method = RequestMethod.GET)
    @ApiOperation(value = "初始化")
    public Result<Object> init(){
        List<SupermarketShelves> shelvesList = iSupermarketShelvesService.list();
        for (SupermarketShelves vo : shelvesList) {
            SupermarketArea area = iSupermarketAreaService.getById(vo.getAreaId());
            if(area != null) {
                vo.setArea(area.getTitle());
                iSupermarketShelvesService.saveOrUpdate(vo);
            }
        }
        return ResultUtil.success();
    }
}
