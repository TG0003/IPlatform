/*
 * @Author: why
 * @Date: 2022-01-08 21:28:21
 * @LastEditTime: 2022-07-06 00:46:13
 * @LastEditors: _t
 * @Description:
 */
const { resolve } = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
  entry: './src/index.js',
  output: {
    filename: 'js/bundle.js',
    path: resolve(__dirname, 'dist'),
  },
  module: {
    rules: [
      {
        test: /\.vue/,
        loader: 'vue-loader',
      },
      {
        test: /\.css/,
        use: [
          'style-loader',
          'css-loader',
        ],
      },
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: [
          {
            loader: 'babel-loader',
            options: {
              // 预设：指示babel做怎么样的兼容性处理
              presets: [
                [
                  '@babel/preset-env',
                  {
                    // 按需加载
                    useBuiltIns: 'usage',
                    // 指定core-js版本
                    corejs: {
                      version: 3,
                    },
                    // 指定兼容性做到哪个版本浏览器
                    targets: {
                      chrome: '60',
                      firefox: '60',
                      ie: '9',
                      safari: '10',
                      edge: '17',
                    },
                  },
                ],
              ],
              cacheDirectory: true,
            },
          },
        ],
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({ template: './src/index.html', minify: { collapseWhitespace: true, removeComments: true } }),
    new VueLoaderPlugin(),
  ],
  mode: 'development',
  devServer: {
    static: {
      directory: resolve(__dirname, 'dist'),
    },
    compress: true,
    port: 2000,
    open: true,
    hot: true,
    proxy: {
      // 一旦devServer(5000)服务器接受到 /api/xxx 的请求，就会把请求转发到另外一个服务器(3000)
      '/': {
        target: 'http://localhost:8080'
      },
    },
  },
  devtool: 'eval-source-map',
};
