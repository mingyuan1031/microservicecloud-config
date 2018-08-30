'use strict'
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path')

module.exports = {
  dev: {

    // Paths
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    proxyTable: { 
    	
    	"*":{ 
         target:"http://192.168.1.165:8080",		
			   changeOrigin:true,  
			   
			  }, 
		 
		
		/*"*":{

			target:"http://192.168.1.4:8080",			
			changeOrigin:true,			
	 
		},*/
/*		
"/myapi":{

			target:"http://192.168.1.118:8080",			
			changeOrigin:true,			
			pathRewrite:{			 
			'^/myapi':'/' 			
			}
			
	},*/
	/*
		"/zjlapi":{

			target:"http://192.168.1.165:8080",			
			changeOrigin:true,			
			pathRewrite:{			 
			'^/zjlapi':'/' 			
			}
			
	},
	
	"/dsbapi":{

			target:"http://192.168.1.199:8080",			
			changeOrigin:true,			
			pathRewrite:{			 
			'^/dsbapi':'/' 			
			}
			
	},
	"/bxzapi":{

			target:"http://192.168.1.158:8080",			
			changeOrigin:true,			
			pathRewrite:{			 
			'^/dsbapi':'/' 			
			}
			
	},
	"/laodaapi":{

			target:"http://192.168.1.161:8080",			
			changeOrigin:true,			
			pathRewrite:{			 
			'^/dsbapi':'/' 			
			}
			 
		}*/
		    	
    },

    // Various Dev Server settings
    host: 'localhost', // can be overwritten by process.env.HOST
    port: 3031, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    autoOpenBrowser: false,
    errorOverlay: true,
    notifyOnErrors: true,
    poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-

    
    /**
     * Source Maps
     */

    // https://webpack.js.org/configuration/devtool/#development
    devtool: 'cheap-module-eval-source-map',

    // If you have problems debugging vue-files in devtools,
    // set this to false - it *may* help
    // https://vue-loader.vuejs.org/en/options.html#cachebusting
    cacheBusting: true,

    cssSourceMap: true
  },

  build: {
    // 
    index: path.resolve(__dirname, '../dist/pages/quickshare.jsp'), //在dlist文件夹新建pages文件夹，index.html文件包含在page中
 
    // 
    assetsRoot: path.resolve(__dirname, '../dist'), //新建一个dlist文件夹
    assetsSubDirectory: '', //css 图片等资源文件的放置位置在dlist文件夹下 为空不会创建文件夹
    assetsPublicPath: '/',  //index引入资源文件的路径

    /**
     * Source Maps
     */

    productionSourceMap: true,
    // https://webpack.js.org/configuration/devtool/#production
    devtool: '#source-map',

    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],

    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report
  }
}
