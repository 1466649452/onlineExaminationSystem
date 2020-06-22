function ajax(options) {

	/**
	 * url:类型：字符串。请求的地址，必填
	 * method:类型：字符串。默认为get方法，选填
	 * async:类型：boolean默认为为false异步方法，选填。true为同步，false为异步
	 * header:类型：json。可选项
	 * accessToken自动获取
	 * timeout：类型：整型。设置超时的时间，单位为毫秒，默认时间60秒
	 */
	var xhr = null;
	var methodlist = ["post", "get", "put", "delete"]

	//创建对象
	if (window.XMLHttpRequest) {
		xhr = new XMLHttpRequest()
	} else {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}

	//url检查
	if (options.url == null) {
		throw new Error("url 为必填项！请检查!")
	}
	//data校验
	if (options.data == null) {
		options.data = {}
	}

	//超时时间校验
	if (options.timeout == null) {
		options.timeout = 60000
	}
	//"method"的校验
	//请求方式默认为get
	if (options.method == null) {
		options.method = "get";
	}
	//非法值
	if (!methodlist.includes(options.method.toLowerCase())) {
		throw new Error("method 输入错误！请检查!")
	}
	//async默认为异步
	if (options.async == null) {
		options.async = true;
	}
	//非法值校验
	if (typeof(options.async) != "boolean") {
		throw new Error("async 应该为boolean类型！请检查!")
	}
	//cookie的名字为统一规定
	var cookies = getCookie("accessToken");
	
	//xhr.withCredentials = true;
	// 连接
	var mm = options.method.toLowerCase();
	if (mm == "get" || mm == "delete") {
		options.data = formsParams(options.data);
		xhr.open(options.method, options.url + "?" + options.data, options.async);
		if (options.header == null) {
			xhr.setRequestHeader('content-type', 'application/json');
		} else {
			xhr.setRequestHeader('content-type', options.header['content-type'])
		}
		if (cookies != null && cookies != "") {
			xhr.setRequestHeader("accessToken", cookies)
		}
		xhr.send(null);
	} else if (mm == "post" || mm == "put") {
		xhr.open(options.method, options.url, options.async);
		if (options.header == null) {
			xhr.setRequestHeader('content-type', 'application/json;charset=utf-8');
		} else {
			xhr.setRequestHeader('content-type', options.header['content-type'])
		}
		if (cookies != null && cookies != "") {
			xhr.setRequestHeader("accessToken", cookies)
		}
		xhr.send(JSON.stringify(options.data));
	}
	//设置超时检测
	let timeCounting = setTimeout(function() {
		xhr.abort()
	}, options.timeout)

	xhr.onreadystatechange = function() {
		//readstate为4表明响应已经完成
		if (xhr.readyState == 4) {
			clearTimeout(timeCounting);
			options.success(JSON.parse(xhr.response));
		}
	}
	function formsParams(data) {
		let arr = [];
		for (let prop in data) {
			arr.push(prop + "=" + data[prop]);
		}
		return arr.join("&");
	}
	function getCookie(cookie_name) {
		
		var allcookies = document.cookie;
		//索引长度，开始索引的位置
		var cookie_pos = allcookies.indexOf(cookie_name);
		// 如果找到了索引，就代表cookie存在,否则不存在
		if (cookie_pos != -1) {
			// 把cookie_pos放在值的开始，只要给值加1即可
			//计算取cookie值得开始索引，加的1为“=”
			cookie_pos = cookie_pos + cookie_name.length + 1;
			//计算取cookie值得结束索引
			var cookie_end = allcookies.indexOf(";", cookie_pos);
			if (cookie_end == -1) {
				cookie_end = allcookies.length;
			}
			//得到想要的cookie的值
			var value = unescape(allcookies.substring(cookie_pos, cookie_end));
		}
		return value;
	}

	window.onload(function () {
``
	})
}
