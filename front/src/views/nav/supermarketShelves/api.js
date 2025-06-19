import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getSupermarketShelvesOne = (params) => {
    return getRequest('/supermarketShelves/getOne', params)
}
export const getSupermarketShelvesList = (params) => {
    return getRequest('/supermarketShelves/getByPage', params)
}
export const getSupermarketShelvesCount = (params) => {
    return getRequest('/supermarketShelves/count', params)
}
export const addSupermarketShelves = (params) => {
    return postRequest('/supermarketShelves/insert', params)
}
export const editSupermarketShelves = (params) => {
    return postRequest('/supermarketShelves/update', params)
}
export const addOrEditSupermarketShelves = (params) => {
    return postRequest('/supermarketShelves/insertOrUpdate', params)
}
export const deleteSupermarketShelves = (params) => {
    return postRequest('/supermarketShelves/delByIds', params)
}
export const getSupermarketAreaList = (params) => {
    return getRequest('/supermarketArea/getAll', params)
}