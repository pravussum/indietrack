const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // devServer: {
  //   proxy: 'http://localhost:8099',
  // },
  chainWebpack: config => {
    config.module
        .rule('vue')
        .use('vue-loader')
        .tap(() => ({
          compilerOptions: {
              isCustomElement: tag => {
                console.log("isCustomElement");
                return tag.startsWith('ion-');
              }
          }
        }))
  }
})
