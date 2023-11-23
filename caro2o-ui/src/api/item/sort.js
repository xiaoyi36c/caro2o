import request from '@/utils/request'

// 查询物品分类信息列表
export function listSort(query) {
  return request({
    url: '/sort/sort/list',
    method: 'get',
    params: query
  })
}

// 查询物品分类信息详细
export function getSort(id) {
  return request({
    url: '/sort/sort/' + id,
    method: 'get'
  })
}

// 新增物品分类信息
export function addSort(data) {
  return request({
    url: '/sort/sort',
    method: 'post',
    data: data
  })
}

// 修改物品分类信息
export function updateSort(data) {
  return request({
    url: '/sort/sort',
    method: 'put',
    data: data
  })
}

// 删除物品分类信息
export function delSort(id) {
  return request({
    url: '/sort/sort/' + id,
    method: 'delete'
  })
}
