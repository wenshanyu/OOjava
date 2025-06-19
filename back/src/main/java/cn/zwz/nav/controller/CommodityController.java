package cn.zwz.nav.controller;

import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.nav.entity.Commodity;
import cn.zwz.nav.entity.CommodityType;
import cn.zwz.nav.entity.SupermarketArea;
import cn.zwz.nav.entity.SupermarketShelves;
import cn.zwz.nav.service.ICommodityService;
import cn.hutool.core.util.StrUtil;
import cn.zwz.nav.service.ICommodityTypeService;
import cn.zwz.nav.service.ISupermarketShelvesService;
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
@Api(tags = "商品档案管理接口")
@RequestMapping("/zwz/commodity")
@Transactional
public class CommodityController {

    @Autowired
    private ICommodityService iCommodityService;

    @Autowired
    private ICommodityTypeService iCommodityTypeService;

    @Autowired
    private ISupermarketShelvesService iSupermarketShelvesService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条商品档案")
    public Result<Commodity> get(@RequestParam String id){
        return new ResultUtil<Commodity>().setData(iCommodityService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部商品档案个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iCommodityService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部商品档案")
    public Result<List<Commodity>> getAll(){
        return new ResultUtil<List<Commodity>>().setData(iCommodityService.list());
    }

    @RequestMapping(value = "/getAllOnWeb", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部商品档案")
    public Result<List<Commodity>> getAllOnWeb(@RequestParam String title, @RequestParam String code){
        QueryWrapper<Commodity> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(title)) {
            qw.like("title",title);
        }
        if(!ZwzNullUtils.isNull(code)) {
            qw.like("code",code);
        }
        return new ResultUtil<List<Commodity>>().setData(iCommodityService.list(qw));
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询商品档案")
    public Result<IPage<Commodity>> getByPage(@ModelAttribute Commodity commodity ,@ModelAttribute PageVo page){
        QueryWrapper<Commodity> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(commodity.getCode())) {
            qw.like("code",commodity.getCode());
        }
        if(!ZwzNullUtils.isNull(commodity.getTitle())) {
            qw.like("title",commodity.getTitle());
        }
        if(!ZwzNullUtils.isNull(commodity.getTypeId())) {
            qw.eq("type_id",commodity.getTypeId());
        }
        if(!ZwzNullUtils.isNull(commodity.getShelvesId())) {
            qw.like("shelves_id",commodity.getShelvesId());
        }
        IPage<Commodity> data = iCommodityService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<Commodity>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改商品档案")
    public Result<Commodity> saveOrUpdate(Commodity commodity){
        if(iCommodityService.saveOrUpdate(commodity)){
            return new ResultUtil<Commodity>().setData(commodity);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增商品档案")
    public Result<Commodity> insert(Commodity commodity){
        // 处理编码问题
        String today = "SP" + (Calendar.getInstance().get(Calendar.YEAR) % 100) + "" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "" + (Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        QueryWrapper<Commodity> ctQw = new QueryWrapper<>();
        ctQw.like("code",today);
        commodity.setCode(today + String.format("%05d", iCommodityService.count(ctQw) + 1));
        // 商品货架
        SupermarketShelves shelves = iSupermarketShelvesService.getById(commodity.getShelvesId());
        if(shelves == null) {
            return ResultUtil.error("商品货架不存在");
        }
        commodity.setShelves(shelves.getTitle());
        commodity.setArea(shelves.getArea());
        // 商品类型
        CommodityType commodityType = iCommodityTypeService.getById(commodity.getTypeId());
        if(commodityType == null) {
            return ResultUtil.error("商品类型不存在");
        }
        commodity.setType(commodityType.getTitle());
        commodity.setBigType(commodityType.getType());
        // 持久化
        iCommodityService.saveOrUpdate(commodity);
        return new ResultUtil<Commodity>().setData(commodity);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑商品档案")
    public Result<Commodity> update(Commodity commodity){
        // 商品货架
        SupermarketShelves shelves = iSupermarketShelvesService.getById(commodity.getShelvesId());
        if(shelves == null) {
            return ResultUtil.error("商品货架不存在");
        }
        commodity.setShelves(shelves.getTitle());
        commodity.setArea(shelves.getArea());
        // 商品类型
        CommodityType commodityType = iCommodityTypeService.getById(commodity.getTypeId());
        if(commodityType == null) {
            return ResultUtil.error("商品类型不存在");
        }
        commodity.setType(commodityType.getTitle());
        commodity.setBigType(commodityType.getType());
        iCommodityService.saveOrUpdate(commodity);
        return new ResultUtil<Commodity>().setData(commodity);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除商品档案")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iCommodityService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/initNotLogin", method = RequestMethod.GET)
    @ApiOperation(value = "初始化")
    public Result<Object> init(){
        List<Commodity> commodityList = iCommodityService.list();
        for (Commodity vo : commodityList) {
            CommodityType type = iCommodityTypeService.getById(vo.getTypeId());
            if(type != null) {
                vo.setType(type.getTitle());
                vo.setBigType(type.getType());
            }
            SupermarketShelves shelves = iSupermarketShelvesService.getById(vo.getShelvesId());
            if(shelves != null) {
                vo.setShelves(shelves.getTitle());
                vo.setArea(shelves.getArea());
            }
            iCommodityService.saveOrUpdate(vo);
        }
        return ResultUtil.success();
    }
}
