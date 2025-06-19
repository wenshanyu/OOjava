package cn.zwz.nav.controller;

import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.nav.entity.SupermarketArea;
import cn.zwz.nav.entity.SupermarketShelves;
import cn.zwz.nav.service.ISupermarketAreaService;
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
@Api(tags = "超市区域管理接口")
@RequestMapping("/zwz/supermarketArea")
@Transactional
public class SupermarketAreaController {

    @Autowired
    private ISupermarketAreaService iSupermarketAreaService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条超市区域")
    public Result<SupermarketArea> get(@RequestParam String id){
        return new ResultUtil<SupermarketArea>().setData(iSupermarketAreaService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部超市区域个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iSupermarketAreaService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部超市区域")
    public Result<List<SupermarketArea>> getAll(){
        return new ResultUtil<List<SupermarketArea>>().setData(iSupermarketAreaService.list());
    }

    @RequestMapping(value = "/getAllOnWeb", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部超市区域")
    public Result<List<SupermarketArea>> getAllOnWeb(@RequestParam String title, @RequestParam String code){
        QueryWrapper<SupermarketArea> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(title)) {
            qw.like("title",title);
        }
        if(!ZwzNullUtils.isNull(code)) {
            qw.like("code",code);
        }
        qw.orderByAsc("sort_order");
        return new ResultUtil<List<SupermarketArea>>().setData(iSupermarketAreaService.list(qw));
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询超市区域")
    public Result<IPage<SupermarketArea>> getByPage(@ModelAttribute SupermarketArea supermarketArea ,@ModelAttribute PageVo page){
        QueryWrapper<SupermarketArea> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(supermarketArea.getCode())) {
            qw.like("code",supermarketArea.getCode());
        }
        if(!ZwzNullUtils.isNull(supermarketArea.getTitle())) {
            qw.like("title",supermarketArea.getTitle());
        }
        if(!ZwzNullUtils.isNull(supermarketArea.getStatus())) {
            qw.eq("status",supermarketArea.getStatus());
        }
        IPage<SupermarketArea> data = iSupermarketAreaService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<SupermarketArea>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改超市区域")
    public Result<SupermarketArea> saveOrUpdate(SupermarketArea supermarketArea){
        if(iSupermarketAreaService.saveOrUpdate(supermarketArea)){
            return new ResultUtil<SupermarketArea>().setData(supermarketArea);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增超市区域")
    public Result<SupermarketArea> insert(SupermarketArea supermarketArea){
        // 处理编码问题
        String today = "QY" + (Calendar.getInstance().get(Calendar.YEAR) % 100) + "" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "" + (Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        QueryWrapper<SupermarketArea> saQw = new QueryWrapper<>();
        saQw.like("code",today);
        supermarketArea.setCode(today + String.format("%05d", iSupermarketAreaService.count(saQw) + 1));
        // 处理排序值问题
        if(Objects.equals(0,supermarketArea.getSortOrder().compareTo(BigDecimal.ZERO))) {
            supermarketArea.setSortOrder(BigDecimal.valueOf(iSupermarketAreaService.count() + 1));
        }
        iSupermarketAreaService.saveOrUpdate(supermarketArea);
        return new ResultUtil<SupermarketArea>().setData(supermarketArea);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑超市区域")
    public Result<SupermarketArea> update(SupermarketArea supermarketArea){
        iSupermarketAreaService.saveOrUpdate(supermarketArea);
        return new ResultUtil<SupermarketArea>().setData(supermarketArea);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除超市区域")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iSupermarketAreaService.removeById(id);
        }
        return ResultUtil.success();
    }
}
