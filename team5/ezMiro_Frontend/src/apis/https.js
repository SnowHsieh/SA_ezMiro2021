import axios from 'axios'

const instance = axios.create({
  baseURL: 'http://25.61.152.159:8080/'
})

export default function (method, url, data) {
  method = method.toLowerCase()
  if (method === 'post') {
    return instance.post(url, data)
  } else if (method === 'get') {
    return instance.get(url, { params: data })
  } else if (method === 'delete') {
    return instance.delete(url, { params: data })
  } else if (method === 'put') {
    return instance.put(url, data)
  } else {
    console.log('unknown method: ' + method)
    return false
  }
}
