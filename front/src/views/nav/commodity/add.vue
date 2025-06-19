<template>
<div>
    <Card>
        <div slot="title">
            <div class="edit-head">
                <a @click="close" class="back-title">
                    <Icon type="ios-arrow-back" />返回
                </a>
                <div class="head-name">添加商品</div>
                <span></span>
                <a @click="close" class="window-close">
                    <Icon type="ios-close" size="31" class="ivu-icon-ios-close" />
                </a>
            </div>
        </div>
        <Form ref="form" :model="form" :label-width="100" :rules="formValidate" label-position="left">
            <Row :gutter="16">
                <Col span="12">
                <FormItem label="商品名称" prop="title">
                    <Input v-model="form.title" clearable show-word-limit maxlength="240" placeholder="请输入商品的名称..." style="width:100%" />
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="商品类型" prop="typeId">
                    <Select v-model="form.typeId" clearable placeholder="请选择商品的类型..." style="width:100%">
                        <Option v-for="(item,index) in typeList" :key="index" :value="item.id">{{item.title}}</Option>
                    </Select>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="采购价" prop="money1">
                    <InputNumber v-model="form.money1" min="0" max="5000000" placeholder="请输入商品的采购价..." style="width:100%"></InputNumber>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="销售价" prop="money2">
                    <InputNumber v-model="form.money2" min="0" max="5000000" placeholder="请输入商品的销售价..." style="width:100%"></InputNumber>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="所属货架" prop="shelvesId">
                    <Select v-model="form.shelvesId" clearable placeholder="请输入商品的货架..." style="width:100%">
                        <Option v-for="(item,index) in shelvesList" :key="index" :value="item.id">{{item.title}}</Option>
                    </Select>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="商品图片" prop="image">
                    <upload-pic-input v-model="form.image" placeholder="请上传商品的图片..." style="width:100%"></upload-pic-input>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="合格证明" prop="prove">
                    <upload-pic-input v-model="form.prove" placeholder="请上传商品的合格证明..." style="width:100%"></upload-pic-input>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="生产厂家" prop="productFactory">
                    <Input v-model="form.productFactory" clearable show-word-limit maxlength="240" placeholder="请输入商品的生产厂家..." style="width:100%" />
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="销售厂家" prop="sellFactory">
                    <Input v-model="form.sellFactory" clearable show-word-limit maxlength="240" placeholder="请输入商品的销售厂家..." style="width:100%" />
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="保质期" prop="guaranteeDate">
                    <Input v-model="form.guaranteeDate" clearable show-word-limit maxlength="240" placeholder="请输入商品的保质期..." style="width:100%" />
                </FormItem>
                </Col>
                <Col span="24">
                <FormItem label="备注" prop="remark">
                    <Input v-model="form.remark" clearable show-word-limit maxlength="240" type="textarea" :rows="3" placeholder="选填商品的备注..." style="width:100%" />
                </FormItem>
                </Col>
            </Row>
            <Form-item class="br">
                <Button @click="handleSubmit" :loading="submitLoading" type="primary">提交并保存</Button>
                <Button @click="handleReset">重置</Button>
                <Button type="dashed" @click="close">关闭</Button>
            </Form-item>
        </Form>
    </Card>
</div>
</template>

<script>
import {
    addCommodity,
    getCommodityTypeList,
    getSupermarketShelvesList
} from "./api.js";
import uploadPicInput from "@/views/template/upload-pic-input";
export default {
    name: "add",
    components: {
        uploadPicInput,
    },
    data() {
        return {
            submitLoading: false, // 表单提交状态
            form: { // 添加或编辑表单对象初始化数据
                code: "",
                title: "",
                bigType: "",
                typeId: "",
                type: "",
                money1: 0,
                money2: 0,
                area: "",
                shelvesId: "",
                shelves: "",
                image: "",
                prove: "",
                productFactory: "",
                sellFactory: "",
                guaranteeDate: "",
                remark: "",
            },
            // 表单验证规则
            formValidate: {},
            typeList: [],
            shelvesList: []
        };
    },
    methods: {
        init() {
            this.getCommodityTypeListFx();
            this.getSupermarketShelvesListFx();
        },
        getCommodityTypeListFx() {
            var that = this;
            that.typeList = [];
            getCommodityTypeList().then(res => {
                if (res.success) {
                    that.typeList = res.result;
                }
            })
        },
        getSupermarketShelvesListFx() {
            var that = this;
            that.shelvesList = [];
            getSupermarketShelvesList().then(res => {
                if (res.success) {
                    that.shelvesList = res.result;
                }
            })
        },
        handleReset() {
            this.$refs.form.resetFields();
        },
        handleSubmit() {
            addCommodity(this.form).then(res => {
                this.submitLoading = false;
                if (res.success) {
                    this.$Message.success("操作成功");
                    this.submited();
                }
            });
        },
        close() {
            this.$emit("close", true);
        },
        submited() {
            this.$emit("submited", true);
        }
    },
    mounted() {
        this.init();
    }
};
</script>

<style lang="less">
// 建议引入通用样式 具体路径自行修改 可删除下面样式代码
// @import "../../../styles/single-common.less";
.edit-head {
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: relative;

    .back-title {
        color: #515a6e;
        display: flex;
        align-items: center;
    }

    .head-name {
        display: inline-block;
        height: 20px;
        line-height: 20px;
        font-size: 16px;
        color: #17233d;
        font-weight: 500;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .window-close {
        z-index: 1;
        font-size: 12px;
        position: absolute;
        right: 0px;
        top: -5px;
        overflow: hidden;
        cursor: pointer;

        .ivu-icon-ios-close {
            color: #999;
            transition: color .2s ease;
        }
    }

    .window-close .ivu-icon-ios-close:hover {
        color: #444;
    }
}
</style>
