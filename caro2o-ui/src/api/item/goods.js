import request from '@/utils/request'

// 查询物品信息列表
export function listGoods(query) {
  return request({
    url: '/item/goods/list',
    method: 'get',
    params: query
  })
}

// 查询物品信息详细
export function getGoods(id) {
  return request({
    url: '/item/goods/' + id,
    method: 'get'
  })
}

// 新增物品信息
export function addGoods(data) {
  return request({
    url: '/item/goods',
    method: 'post',
    data: data
  })
}

// 修改物品信息
export function updateGoods(data) {
  return request({
    url: '/item/goods',
    method: 'put',
    data: data
  })
}

// 删除物品信息

export function delGoods(id) {
  return request({
    url: '/item/goods/' + id,
    method: 'delete'
  })
}

// 出入库明细
export function subsidiary(id) {
  return request({
    url: '/item/goods/subsidiary/' + id,
    method: 'get'
  })
}
