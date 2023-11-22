import request from '@/utils/request'

// 查询服务项列表
export function listServiceitem(query) {
  return request({
    url: '/business/serviceitem/list',
    method: 'get',
    params: query
  })
}

// 查询服务项详细
export function getServiceitem(id) {
  return request({
    url: '/business/serviceitem/' + id,
    method: 'get'
  })
}

// 新增服务项
export function addServiceitem(data) {
  return request({
    url: '/business/serviceitem',
    method: 'post',
    data: data
  })
}

// 修改服务项
export function updateServiceitem(data) {
  return request({
    url: '/business/serviceitem',
    method: 'put',
    data: data
  })
}

// 上架服务项
export function updateSaleStatus(id) {
  return request({
    url: '/business/serviceitem/' + id,
    method: 'put'
  })
}
//获取审核信息
export function getAuditInfo(id) {
  return request({
    url: '/business/serviceitem/auditInfo/' + id,
    method:'get'
  })
}
