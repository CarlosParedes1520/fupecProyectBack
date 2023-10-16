package com.bolsadeidead.springboot.backend.fupec.auth;

/*
 * Configuracion firma publica y privada para el token
 * 
 * */
public class JwtConfig {
	// kistema criptografico de llave publica y privada
	public static final String RSA_PRIVADA= "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEowIBAAKCAQEAq5yvH5IVjI/v3P/W5rvRZmsmrnKzo4xVxV1OMmPy5xjxIYNV\r\n" + 
			"uRVo8q+yNeTF9apq4T7RuGKqsUeG2mGh2dkW/r1kpr37TcNRyL0jMivqbCk5TiL0\r\n" + 
			"Qn7RZ9c5LSLyxpgLc1iA/VLYo0JHh2V4SK32Nv94pRnJcppnZLuJ6tcjQn8nSVVq\r\n" + 
			"GYqqiDuOUF123YtY6QHjnbg5TXwe+VPRvcRHQeWh5OAhqBk6wvoSd+mlFaQs83wT\r\n" + 
			"BqCVf1pjPSVhkgmCBTMXpcQXupsiRdcdjW20PNLJf8rgScwVvcMpOsHxGKgEuHP7\r\n" + 
			"Q+c2xGgZrIxtDaB+WBsIyrB5nr4Y3EplH9WNtwIDAQABAoIBAQCQ9TK/2ie1qceU\r\n" + 
			"x86A69k/R/dOl7jBx0KUCfKJgf+l0V2tT0DTrBAQHvTLrUjB8IeDaArCUyWXxbFV\r\n" + 
			"Oe77EvLnjpc8eXGTCA9HHuxh7tJ/NnsfU0s7999udU/onoKv2bKiZfnfAWHY1Z2R\r\n" + 
			"8tYdnkRBxJ3l59hPo7j+T6N56OvxuA8LMpoBnr9GJK/1ph43ZXB2pI7t/rPe80GR\r\n" + 
			"zH6wYPVTMVSZkOutToCg9cl6j/U20v4at+8HNm4NMCLI5w+FmExIvkQ3kSbLT1cV\r\n" + 
			"1oNy9dfzhSAGphoB91diZoHzhexR4RPUTwRrw6hdNTEpLYpQBpWUq0pV+cU2ZqPH\r\n" + 
			"3N+HBtOhAoGBAOKiwtdrPAqTiWFd6ehJtxJVOcd8OINTX7w7Vvv82OfuMQVk4gwQ\r\n" + 
			"VDQyqOoR+hizYTZRLH5rNVGqnRJXlkcs8/Wb0vcY6YOyUoGYdxg14e0uU22HcjV+\r\n" + 
			"kt51sV2zL3+NfJ6gvdq2ayc73r/j1w07rLm6Qm95Ne/hVULUoKlWBBxpAoGBAMHY\r\n" + 
			"2eUUBd5yS8yU0xSA7RKWwU3/kP9lcsI5CNjWs8KalgH5gbMU9jYm2ZrJbbtkbyG+\r\n" + 
			"k0Y3rBgFUrRFbwanml9Rh4EleQA0ifzbAG/Yaf8UgRmjW5PY10OTEbqKCsW1nY8m\r\n" + 
			"ukuHVW/NEEfzUJwpcUu386D2FKWUB9ny+r9UxZUfAoGAalkX9wAR96CNycvI9ECL\r\n" + 
			"EKY/a7WFy1I3TVvHEI3wtrzV8gMJ88FB0hCnpNb0tdyO1G844sWqCn12IWJKWXHl\r\n" + 
			"tK3/TAY7pZnrD3cdA5ZdQvAHJBF37VRM1OkDZsWrlz4saT9id5w8Iff9KaxfgI0t\r\n" + 
			"GyYlAKwvPDZPzQF7tppvaWkCgYB6tVkJiClG7TdKZBNScWKhV8k6JMOWyJNim1Qc\r\n" + 
			"X50fXVgEG/ucb3KESQeK0mOiMiufQtJbqrZH5ma6MxHbNpGkMSoD+MKl/Jw08jVO\r\n" + 
			"l8JRdJtcrWo1Jxjtb9XTLbsv+I2qpqtbUGonAbqCsWWf1SQg+GtCw0PCpPhrYUKV\r\n" + 
			"h4YjpQKBgAv3sTtFYaEArQc+jdCulSsMyx32Z2hb+L1lIkg+FrnKQrCvOk0jqs9b\r\n" + 
			"4NgCBGe013jiIGq7pCMl5SU2n1CTTzFfY+ubhqCzzPzfDyoRVz9WHbq3FogxXUg5\r\n" + 
			"gq+lwlSZqNBNz81vh1eX2ftQk/sOpXytFmOkuxJZh81JFoIUxyLf\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq5yvH5IVjI/v3P/W5rvR\r\n" + 
			"ZmsmrnKzo4xVxV1OMmPy5xjxIYNVuRVo8q+yNeTF9apq4T7RuGKqsUeG2mGh2dkW\r\n" + 
			"/r1kpr37TcNRyL0jMivqbCk5TiL0Qn7RZ9c5LSLyxpgLc1iA/VLYo0JHh2V4SK32\r\n" + 
			"Nv94pRnJcppnZLuJ6tcjQn8nSVVqGYqqiDuOUF123YtY6QHjnbg5TXwe+VPRvcRH\r\n" + 
			"QeWh5OAhqBk6wvoSd+mlFaQs83wTBqCVf1pjPSVhkgmCBTMXpcQXupsiRdcdjW20\r\n" + 
			"PNLJf8rgScwVvcMpOsHxGKgEuHP7Q+c2xGgZrIxtDaB+WBsIyrB5nr4Y3EplH9WN\r\n" + 
			"twIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";
}
