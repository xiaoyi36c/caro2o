import request from '@/utils/request'

// 查询出入库单据列表
export function listBill(query) {
  return request({
    url: '/item/bill/list',
    method: 'get',
    params: query
  })
}

// 查询出入库单据详细
export function getBill(id) {
  return request({
    url: '/item/bill/' + id,
    method: 'get'
  })
}

// 新增出入库单据
export function addBill(data) {
  return request({
    url: '/item/bill',
    method: 'post',
    data: data
  })
}

// 修改出入库单据
export function updateBill(data) {
  return request({
    url: '/item/bill',
    method: 'put',
    data: data
  })
}

// 删除出入库单据
export function delBill(id) {
  return request({
    url: '/item/bill/' + id,
    method: 'delete'
  })
}

// 作废出入库单据
export function voidBill(id) {
  return request({
    url: '/item/bill/void/' + id,
    method: 'put'
  })
}
