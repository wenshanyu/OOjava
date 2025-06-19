package cn.zwz.nav.controller;

import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.nav.entity.CommodityType;
import cn.zwz.nav.entity.SupermarketArea;
import cn.zwz.nav.entity.SupermarketShelves;
import cn.zwz.nav.service.ICommodityTypeService;
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
@Api(tags = "商品类型管理接口")
@RequestMapping("/zwz/commodityType")
@Transactional
public class CommodityTypeController {

    @Autowired
    private ICommodityTypeService iCommodityTypeService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条商品类型")
    public Result<CommodityType> get(@RequestParam String id){
        return new ResultUtil<CommodityType>().setData(iCommodityTypeService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部商品类型个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iCommodityTypeService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部商品类型")
    public Result<List<CommodityType>> getAll(){
        return new ResultUtil<List<CommodityType>>().setData(iCommodityTypeService.list());
    }

    @RequestMapping(value = "/getAllOnWeb", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部商品类型")
    public Result<List<CommodityType>> getAllOnWeb(@RequestParam String title, @RequestParam String code){
        QueryWrapper<CommodityType> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(title)) {
            qw.like("title",title);
        }
        if(!ZwzNullUtils.isNull(code)) {
            qw.like("code",code);
        }
        qw.orderByAsc("sort_order");
        return new ResultUtil<List<CommodityType>>().setData(iCommodityTypeService.list(qw));
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询商品类型")
    public Result<IPage<CommodityType>> getByPage(@ModelAttribute CommodityType commodityType ,@ModelAttribute PageVo page){
        QueryWrapper<CommodityType> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(commodityType.getType())) {
            qw.eq("type",commodityType.getType());
        }
        if(!ZwzNullUtils.isNull(commodityType.getTitle())) {
            qw.like("title",commodityType.getTitle());
        }
        if(!ZwzNullUtils.isNull(commodityType.getCode())) {
            qw.like("code",commodityType.getCode());
        }
        if(!ZwzNullUtils.isNull(commodityType.getStatus())) {
            qw.eq("status",commodityType.getStatus());
        }
        IPage<CommodityType> data = iCommodityTypeService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<CommodityType>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改商品类型")
    public Result<CommodityType> saveOrUpdate(CommodityType commodityType){
        if(iCommodityTypeService.saveOrUpdate(commodityType)){
            return new ResultUtil<CommodityType>().setData(commodityType);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增商品类型")
    public Result<CommodityType> insert(CommodityType commodityType){
        // 处理编码问题
        String today = "LX" + (Calendar.getInstance().get(Calendar.YEAR) % 100) + "" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "" + (Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        QueryWrapper<CommodityType> ctQw = new QueryWrapper<>();
        ctQw.like("code",today);
        commodityType.setCode(today + String.format("%05d", iCommodityTypeService.count(ctQw) + 1));
        // 处理排序值问题
        if(Objects.equals(0,commodityType.getSortOrder().compareTo(BigDecimal.ZERO))) {
            commodityType.setSortOrder(BigDecimal.valueOf(iCommodityTypeService.count() + 1));
        }
        iCommodityTypeService.saveOrUpdate(commodityType);
        return new ResultUtil<CommodityType>().setData(commodityType);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑商品类型")
    public Result<CommodityType> update(CommodityType commodityType){
        iCommodityTypeService.saveOrUpdate(commodityType);
        return new ResultUtil<CommodityType>().setData(commodityType);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除商品类型")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iCommodityTypeService.removeById(id);
        }
        return ResultUtil.success();
    }
}
