import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getCommodityTypeOne = (params) => {
    return getRequest('/commodityType/getOne', params)
}
export const getCommodityTypeList = (params) => {
    return getRequest('/commodityType/getByPage', params)
}
export const getCommodityTypeCount = (params) => {
    return getRequest('/commodityType/count', params)
}
export const addCommodityType = (params) => {
    return postRequest('/commodityType/insert', params)
}
export const editCommodityType = (params) => {
    return postRequest('/commodityType/update', params)
}
export const addOrEditCommodityType = (params) => {
    return postRequest('/commodityType/insertOrUpdate', params)
}
export const deleteCommodityType = (params) => {
    return postRequest('/commodityType/delByIds', params)
}