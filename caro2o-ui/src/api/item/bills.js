import request from '@/utils/request'

// 查询出入库单据明细列表
export function listBills(query) {
  return request({
    url: '/item/bills/list',
    method: 'get',
    params: query
  })
}

// 查询出入库单据明细详细
export function getBills(id) {
  return request({
    url: '/item/bills/' + id,
    method: 'get'
  })
}

// 新增出入库单据明细
export function addBills(data) {
  return request({
    url: '/item/bills',
    method: 'post',
    data: data
  })
}

// 修改出入库单据明细
export function updateBills(data) {
  return request({
    url: '/item/bills',
    method: 'put',
    data: data
  })
}

// 删除出入库单据明细
export function delBills(id) {
  return request({
    url: '/item/bills/' + id,
    method: 'delete'
  })
}
