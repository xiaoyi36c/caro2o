import request from '@/utils/request'

// 查询客户联系人列表
export function listLinkmane(query) {
  return request({
    url: '/client/linkmane/list',
    method: 'get',
    params: query
  })
}

// 查询客户联系人详细
export function getLinkmane(id) {
  return request({
    url: '/client/linkmane/' + id,
    method: 'get'
  })
}

// 新增客户联系人
export function addLinkmane(data) {
  return request({
    url: '/client/linkmane',
    method: 'post',
    data: data
  })
}

// 修改客户联系人
export function updateLinkmane(data) {
  return request({
    url: '/client/linkmane',
    method: 'put',
    data: data
  })
}

// 删除客户联系人
export function delLinkmane(id) {
  return request({
    url: '/client/linkmane/' + id,
    method: 'delete'
  })
}
