import request from '@/utils/request'

// 查询流程定义明细列表
export function listInfo(query) {
  return request({
    url: '/flow/info/list',
    method: 'get',
    params: query
  })
}

// 查询流程定义明细详细
export function getInfo(id) {
  return request({
    url: '/flow/info/' + id,
    method: 'get'
  })
}

// 新增流程定义明细
export function addInfo(data) {
  return request({
    url: '/flow/info',
    method: 'post',
    data: data
  })
}

// 删除流程定义明细
export function delInfo(id) {
  return request({
    url: '/flow/info/' + id,
    method: 'delete',
  })
}

// 流程定义部署
export function deploy(data) {
  return request({
    url: '/flow/info/deploy',
    method: 'put',
    data: data,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  })
}
// 请求后端文件流程定义文件数据
export function getResource(id,type) {
  return request({
    url: `/flow/info/resources/${id}/${type}`,
    method: 'get',
  })
}
// 请求后端文件流程图片数据
export function getResourceDiagram(id,type) {
  return request({
    url: `/flow/info/resources/${id}/${type}`,
    method: 'get',
    responseType: 'blob' //指定将响应数据转换成什么类型
  })
}
// 请求后端文件流程图片数据
export function getResourceDiagramSVG(id) {
  return request({
    url: `/flow/info/getResourceDiagramSVG/${id}`,
    method: 'get',
    responseType: 'blob' //指定将响应数据转换成什么类型
  })
}
