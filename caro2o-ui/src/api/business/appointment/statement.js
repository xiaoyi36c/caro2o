import request from '@/utils/request'

// 查询结算单列表
export function listStatement(query) {
  return request({
    url: '/business/statement/list',
    method: 'get',
    params: query
  })
}

// 查询结算单
export function getStatement(id) {
  return request({
    url: '/business/statement/' + id,
    method: 'get'
  })
}

// 新增结算单
export function addStatement(data) {
  return request({
    url: '/business/statement',
    method: 'post',
    data: data
  })
}

// 修改结算单
export function updateStatement(data) {
  return request({
    url: '/business/statement',
    method: 'put',
    data: data
  })
}

// 删除结算单
export function delStatement(id) {
  return request({
    url: '/business/statement/' + id,
    method: 'delete'
  })
}
// 新增结算单明细
export function saveStatementItem(data) {
  return request({
    url: '/business/statement/item',
    method: 'post',
    data: data
  })
}
// 查询结算单明细
export function getStatementItems(statementId) {
  return request({
    url: '/business/statement/items/' + statementId,
    method: 'get'
  })
}
// 结算单支付
export function doPaid(statementId) {
  return request({
    url: '/business/statement/paid/' + statementId,
    method: 'put'
  })
}
