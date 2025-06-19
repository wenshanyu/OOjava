package cn.zwz.nav.entity;

import cn.zwz.basics.baseClass.ZwzBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;


@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_commodity")
@TableName("a_commodity")
@ApiModel(value = "商品档案")
public class Commodity extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品编码")
    private String code;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "商品大类")
    private String bigType;

    @ApiModelProperty(value = "商品类型ID")
    private String typeId;

    @ApiModelProperty(value = "商品类型")
    private String type;

    @ApiModelProperty(value = "采购价")
    private BigDecimal money1;

    @ApiModelProperty(value = "销售价")
    private BigDecimal money2;

    @ApiModelProperty(value = "所属区域")
    private String area;

    @ApiModelProperty(value = "所属货架Id")
    private String shelvesId;

    @ApiModelProperty(value = "所属货架")
    private String shelves;

    @ApiModelProperty(value = "商品图片")
    private String image;

    @ApiModelProperty(value = "合格证明")
    private String prove;

    @ApiModelProperty(value = "生产厂家")
    private String productFactory;

    @ApiModelProperty(value = "销售厂家")
    private String sellFactory;

    @ApiModelProperty(value = "保质期")
    private String guaranteeDate;

    @ApiModelProperty(value = "备注")
    private String remark;
}