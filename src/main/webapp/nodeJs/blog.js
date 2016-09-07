//URL 상세 정보 추출하기
//var http = require('http');
//var url = require('url'); // URL 분석기 준비

var express = require('express');
var search = require('request');
var bodyParser = require('body-parser');
var url = require('url'); // URL 분석기 준비
var app = express();
var xml2js = require('xml2js');
var parser = new xml2js.Parser(); // Creating XML to JSON parser object

//express 모듈에 보조 장치 장착한다.
app.use(bodyParser.json()); // JSON 형식으로 넘어온 데이터 처리 
app.use(bodyParser.urlencoded({extended:true}));
app.use(express.static('www'));
app.use(function (req, res, next) {

	// Website you wish to allow to connect
	res.setHeader('Access-Control-Allow-Origin', '*');

	// Request methods you wish to allow
	res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');

	// Request headers you wish to allow
	res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');

	// Set to true if you need the website to include cookies in the requests sent
	// to the API (e.g. in case you use sessions)
	res.setHeader('Access-Control-Allow-Credentials', true);

	// Pass to next layer of middleware
	next();
});

app.get('/naverPost.do', function(req, res){
	
	var urlInfo = url.parse(req.url, true);
	var keyword = urlInfo.query.keyword;
	var page = urlInfo.query.start;

	var options = {
			url :  'https://openapi.naver.com/v1/search/blog.xml',
			headers : {	'User-Agent': 'curl/7.43.0', 
				'Content-Type' : 'application/xml', 
				'charset' : 'UTF-8',
				'cache-control' : 'no-cache',
				'X-Naver-Client-Id' : '5VKp7qdAUXuURPz9imbk', 
				'X-Naver-Client-Secret' : 'uPU8U2Lyx1'},
				method: 'GET',
				qs: {'query': keyword, 'sort':'sim', 'display':'100', 'start':page}
	};

	//요청 시작 받은값은 body
	search(options, function (error, response, body) {
		if (!error && response.statusCode == 200) {
			parser.parseString(body, function (err, result) {
				res.setHeader('Content-Type', 'application/json');
				res.send(result);
				res.end();
			});
		}
	});
});

app.listen(8899, function () {
	console.log('port 8899 server');
});