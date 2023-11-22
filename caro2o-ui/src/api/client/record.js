import request from '@/utils/request'

// 查询拜访信息列表
export function listRecord(query) {
  return request({
    url: '/client/record/list',
    method: 'get',
    params: query
  })
}

// 查询拜访信息详细
export function getRecord(id) {
  return request({
    url: '/client/record/' + id,
    method: 'get'
  })
}
// 查询客户和联系人
export function getClientLinkman() {
  return request({
    url: '/client/record/ClientLinkman',
    method: 'get'
  })
}

// 新增拜访信息
export function addRecord(data) {
  return request({
    url: '/client/record',
    method: 'post',
    data: data
  })
}

// 修改拜访信息
export function updateRecord(data) {
  return request({
    url: '/client/record',
    method: 'put',
    data: data
  })
}

// 删除拜访信息
export function delRecord(id) {
  return request({
    url: '/client/record/' + id,
    method: 'delete'
  })
}
