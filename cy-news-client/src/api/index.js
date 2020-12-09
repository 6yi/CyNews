import axios from 'axios'
import {Toast} from 'antd-mobile'
const instance = axios.create({
  baseURL:'http://59.110.173.180:8089/',
  timeout: 3000.
})

// 添加请求拦截器
instance.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  const userToken = localStorage.getItem('userToken')
  if (userToken) {
    config.headers.authorization = userToken
  }
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

// 添加响应拦截器
instance.interceptors.response.use(function (response) {
  // 对响应数据做点什么
  
  if(response.data.code != 200) {
    Toast.fail(response.data.data,1)
    return Promise.reject(response);
  } else {
    
    return response;
  }
  
}, function (error) {
  // 对响应错误做点什么
  console.log(error);
  /* Toast.info(error.data,1) */
  return Promise.reject(error);
})


export default instance