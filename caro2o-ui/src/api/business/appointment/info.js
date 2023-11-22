import request from '@/utils/request'

// 查询养修信息预约列表
export function listAppointment(query) {
  return request({
    url: '/business/appointment/list',
    method: 'get',
    params: query
  })
}

// 查询养修信息预约详细
export function getAppointment(id) {
  return request({
    url: '/business/appointment/' + id,
    method: 'get'
  })
}

// 新增养修信息预约
export function addAppointment(data) {
  return request({
    url: '/business/appointment',
    method: 'post',
    data: data
  })
}

// 修改养修信息预约
export function updateAppointment(data) {
  return request({
    url: '/business/appointment',
    method: 'put',
    data: data
  })
}

// 修改到店状态
export function doArrival(id) {
  return request({
    url: '/business/appointment/arrival/' + id,
    method: 'put'
  })
}
// 修改取消预约状态
export function doCancel(id) {
  return request({
    url: '/business/appointment/cancel/' + id,
    method: 'put'
  })
}
// 创建结算单
export function doCreateStatement(id) {
  return request({
    url: '/business/statement/doCreateStatement/' +id,
    method: 'get',
  })
}
