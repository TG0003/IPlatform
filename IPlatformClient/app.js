/*
 * @Author: _t
 * @Date: 2022-07-06 15:21:59
 * @LastEditTime: 2022-07-06 16:00:21
 * @LastEditors: _t
 * @Description: 
 */
const express = require('express');
var proxy = require('http-proxy-middleware');
const app = express();
app.use(express.static('dist', { maxAge: 1000 * 3600 }));
app.use('/', proxy.createProxyMiddleware({
    target: 'http://localhost:8080',
	/*onProxyReq:(proxyReq, req, res)=>{
	  proxyReq.setHeader('remote_addr', req.headers.remote_addr);
	}*/
}));
app.listen(2000);