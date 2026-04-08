class RequestHelper {
  // 请求
  request(options) {
    return new Promise((resolve, reject) => {
      // 构造请求头
      let header = options.header || {};
      // 添加鉴权
            header["Authorization"] = "Bearer sk-afb6a8de44c247e7924b11bf240a759e";
            // 默认内容类型
      if (!header["Content-Type"]) {
        header["Content-Type"] = "application/json";
      }

      uni.request({
        url: options.url,
        method: options.method || "GET",
        data: options.data,
        header: header,
        timeout: options.timeout || 30000,
        success: (res) => {
          // 响应拦截
          if (res.statusCode >= 200 && res.statusCode < 300) {
            resolve(res.data);
          } else {
            this.handleError(res);
            reject(res);
          }
        },
        fail: (err) => {
          console.log(err);
          uni.showToast({
            title: "请求无响应",
            icon: "none",
          });
          reject(err);
        },
      });
    });
  }

  // 错误处理
  handleError(response) {
    if (response.statusCode === 500 || response.statusCode === 503) {
      uni.showToast({
        title: `服务器错误${response.statusCode}`,
        icon: "none",
      });
    } else {
      uni.showToast({
        title: `请求失败: ${response.statusCode}`,
        icon: "none",
      });
    }
  }

  post({ data, options = {} }) {
          options.url = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";
      data.model = "qwen-turbo";
        options.data = data;
    options.method = "POST";
    return this.request(options);
  }
}

let requestHelper = new RequestHelper();

export default requestHelper;
