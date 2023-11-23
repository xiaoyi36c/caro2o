import request from '@/utils/request'

// 查询仓库信息列表
export function listBarn(query) {
  return request({
    url: '/barn/barn/list',
    method: 'get',
    params: query
  })
}

// 查询仓库信息详细
export function getBarn(id) {
  return request({
    url: '/barn/barn/' + id,
    method: 'get'
  })
}

// 新增仓库信息
export function addBarn(data) {
  return request({
    url: '/barn/barn',
    method: 'post',
    data: data
  })
}

// 修改仓库信息
export function updateBarn(data) {
  return request({
    url: '/barn/barn',
    method: 'put',
    data: data
  })
}

// 删除仓库信息
export function delBarn(id) {
  return request({
    url: '/barn/barn/' + id,
    method: 'delete'
  })
}
