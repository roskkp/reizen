/* static file 응답하기  */
var mysql = require('mysql');
var dateFormat = require('dateformat');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

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


var pool  = mysql.createPool({
  connectionLimit : 10,
  host     : '192.168.0.28',
  port	   : 3306,
  user     : 'reizen',
  password : '1234',
  database : 'reizen'
});

pool.on('connection', function() {
	console.log('커넥션 객체가 생성되었음.');
});

app.get('/category/middle.do', function (request, response) {
	pool.query(
	  'select c03name, cate03, @rownum:=@rownum+1 rn from cate_m m, cate_s s, (select @rownum:=0) sub where cate01 = ? and m.cate02 != "A0204" and s.cate02 = m.cate02',
	  [request.query.cate01], 
	  function(err, rows, fields) { 
		  if (err) throw err;
		  response.writeHead(200, {
			'Content-Type' : 'application/json;charset=UTF-8' 
		  });
		  var data = new Array();
		  for (var i = 0; i < rows.length; i++) {
			  var cate = new Object();
			  cate.name = rows[i].c03name; 
			  cate.cate = rows[i].cate03;
			  cate.index = rows[i].rn;
			  data.push(cate);
		  }

		  var list = new Object();
		  list.data = data;
		  list = JSON.stringify(list)
		  response.write(list);
		  response.end();
	});
});

app.get('/category/small.do', function (request, response) {
	pool.query(
	  'select c03name, cate03, @rownum:=@rownum+1 rn from cate_s s , (select @rownum:=0) sub where cate02 = ? and s.cate02 != "A0204"',
	  [request.query.cate02], 
	  function(err, rows, fields) { 
		  if (err) throw err;
		  response.writeHead(200, {
			'Content-Type' : 'application/json;charset=UTF-8' 
		  });
		  var data = new Array();
		  for (var i = 0; i < rows.length; i++) {
			  var cate = new Object();
			  name = rows[i].c03name.substr(0,5);
			  if(rows[i].c03name.charAt(6) != ''){
				  name = name+'...'
			  }
			  cate.name = name;
			  cate.fname = rows[i].c03name; 
			  cate.cate = rows[i].cate03;
			  cate.index = rows[i].rn;
			  data.push(cate);
		  }
		  data = JSON.stringify(data)
		  response.write(data);
		  response.end();
	});
});

app.listen(8888, function () {
	  console.log('category on port 8888!');
});
