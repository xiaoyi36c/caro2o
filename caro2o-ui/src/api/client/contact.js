import request from '@/utils/request'

// 查询客户联系人列表
export function listContact(query) {
  return request({
    url: '/client/contact/list',
    method: 'get',
    params: query
  })
}

// 查询客户联系人详细
export function getContact(id) {
  return request({
    url: '/client/contact/' + id,
    method: 'get'
  })
}
// 根据客户id查询联系方式
export function getPhone(id) {
  return request({
    url: '/client/contact/phone/' + id,
    method: 'get'
  })
}

// 新增客户联系人
export function addContact(data) {
  return request({
    url: '/client/contact',
    method: 'post',
    data: data
  })
}

// 修改客户联系人
export function updateContact(data) {
  return request({
    url: '/client/contact',
    method: 'put',
    data: data
  })
}

// 删除客户联系人
export function delContact(id) {
  return request({
    url: '/client/contact/' + id,
    method: 'delete'
  })
}
