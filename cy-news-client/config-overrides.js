const {override,fixBabelImports,addLessLoader} =require('customize-cra');

module.exports = override(
    // 针对antd 实现按需打包：根据import来打包 (使用babel-plugin-import)
    fixBabelImports('import',{
        libraryName:'antd-mobile',
        libraryDirectory:'es',
        style:true,//自动打包相关的样式 默认为 style:'css'
    }),
    // 使用less-loader对源码重的less的变量进行重新制定，设置antd自定义主题
    addLessLoader({
        //less-loader 5.0以上版本需要加 lessOptions包裹
        lessOptions: {
        javascriptEnabled: true,
        //使用modifyVars修改antd默认less颜色
        modifyVars:[
                /* {'@brand-primary':'#1DA57A'},
                {} */
            ]
        },
    })
);