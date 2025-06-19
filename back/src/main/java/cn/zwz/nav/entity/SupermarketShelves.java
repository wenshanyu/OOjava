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
@Table(name = "a_supermarket_shelves")
@TableName("a_supermarket_shelves")
@ApiModel(value = "超市货架")
public class SupermarketShelves extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "货架编码")
    private String code;

    @ApiModelProperty(value = "货架名称")
    private String title;

    @ApiModelProperty(value = "区域ID")
    private String areaId;

    @ApiModelProperty(value = "区域名称")
    private String area;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "排序值")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "备注")
    private String remark;
}