<template>
<div>
    <Card>
        <div slot="title">
            <div class="edit-head">
                <a @click="close" class="back-title">
                    <Icon type="ios-arrow-back" />返回
                </a>
                <div class="head-name">编辑超市货架</div>
                <span></span>
                <a @click="close" class="window-close">
                    <Icon type="ios-close" size="31" class="ivu-icon-ios-close" />
                </a>
            </div>
        </div>
        <Form ref="form" :model="form" :label-width="100" :rules="formValidate" label-position="left">
            <FormItem label="货架编码" prop="code">
                <Input v-model="form.code" readonly style="width:570px" />
            </FormItem>
            <FormItem label="货架名称" prop="title">
                <Input v-model="form.title" clearable show-word-limit maxlength="240" placeholder="请输入货架的名称..." style="width:570px" />
            </FormItem>
            <FormItem label="所属区域" prop="areaId">
                <Select v-model="form.areaId" clearable placeholder="请选择货架的区域..." style="width:570px">
                    <Option v-for="(item,index) in areaList" :key="index" :value="item.id">{{item.title}}</Option>
                </Select>
            </FormItem>
            <FormItem label="状态" prop="status">
                <Select v-model="form.status" clearable placeholder="请选择货架的状态..." style="width:570px">
                    <Option value="正常">正常</Option>
                    <Option value="封存">封存</Option>
                </Select>
            </FormItem>
            <FormItem label="排序值" prop="sortOrder">
                <InputNumber v-model="form.sortOrder" min="0" max="5000000" style="width:570px"></InputNumber>
            </FormItem>
            <FormItem label="备注" prop="remark">
                <Input v-model="form.remark" clearable show-word-limit maxlength="240" type="textarea" :rows="4" placeholder="选填货架的备注..." style="width:570px" />
            </FormItem>
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
    editSupermarketShelves,
    getSupermarketAreaList
} from "./api.js";
export default {
    name: "edit",
    components: {},
    props: {
        data: Object
    },
    data() {
        return {
            submitLoading: false, // 表单提交状态
            form: { // 添加或编辑表单对象初始化数据
                code: "",
                title: "",
                status: "",
                sortOrder: 0,
                remark: "",
            },
            // 表单验证规则
            formValidate: {},
            areaList: []
        };
    },
    methods: {
        init() {
            this.getSupermarketAreaListFx();
            this.handleReset();
            this.form = this.data;
        },
        getSupermarketAreaListFx() {
            var that = this;
            getSupermarketAreaList().then(res => {
                if (res.success) {
                    that.areaList = res.result;
                }
            })
        },
        handleReset() {
            this.$refs.form.resetFields();
        },
        handleSubmit() {
            this.submitLoading = true;
            editSupermarketShelves(this.form).then(res => {
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
