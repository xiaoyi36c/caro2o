import request from '@/utils/request'

// 查询我的代办列表
export function listInfo(query) {
  return request({
    url: '/audit/info/todoList',
    method: 'get',
    params: query
  })
}
// 查询我的已办列表
export function listDoneInfo(query) {
  return request({
    url: '/audit/info/doneList',
    method: 'get',
    params: query
  })
}
// 审批
export function audit(data) {
  return request({
    url: '/audit/info/audit',
    method: 'put',
    data: data
  })
}
// 审批历史
export function getHistory(id) {
  return request({
    url: '/audit/info/auditHistory/' + id,
    method: 'get',
  })
}
// 进度
export function getProgress(id) {
  return request({
    url: `/audit/info/getProgress/${id}`,
    method: 'get',
    responseType: 'blob' //指定将响应数据转换成什么类型
  })
}
