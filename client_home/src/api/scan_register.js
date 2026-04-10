/**
 * 扫码注册 API
 */
export const scanRegister = {
	/**
	 * 验证二维码
	 * @param {string} qr_token - 二维码Token
	 */
	verify(qr_token) {
		return uni.request({
			url: `${uni.getStorageSync('host')}/api/scan_register/verify`,
			method: 'GET',
			data: { qr_token },
			header: {
				'Content-Type': 'application/json',
				'token': uni.getStorageSync('token')
			}
		});
	},
	
	/**
	 * 扫码注册
	 * @param {Object} data - 注册数据
	 */
	register(data) {
		return uni.request({
			url: `${uni.getStorageSync('host')}/api/scan_register/register`,
			method: 'POST',
			data: data,
			header: {
				'Content-Type': 'application/json',
				'token': uni.getStorageSync('token')
			}
		});
	}
}
