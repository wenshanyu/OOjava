import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getSupermarketAreaOne = (params) => {
    return getRequest('/supermarketArea/getOne', params)
}
export const getSupermarketAreaList = (params) => {
    return getRequest('/supermarketArea/getByPage', params)
}
export const getSupermarketAreaCount = (params) => {
    return getRequest('/supermarketArea/count', params)
}
export const addSupermarketArea = (params) => {
    return postRequest('/supermarketArea/insert', params)
}
export const editSupermarketArea = (params) => {
    return postRequest('/supermarketArea/update', params)
}
export const addOrEditSupermarketArea = (params) => {
    return postRequest('/supermarketArea/insertOrUpdate', params)
}
export const deleteSupermarketArea = (params) => {
    return postRequest('/supermarketArea/delByIds', params)
}