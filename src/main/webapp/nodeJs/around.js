/* static file 응답하기  */
var mysql = require('mysql');
var dateFormat = require('dateformat');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

//express 모듈에 보조 장치 장착한다.
app.use(bodyParser.json()); // JSON 형식으로 넘오온 데이터 처리 
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

app.get('/around/count.do', function (request, response) {
	pool.query(
	  'select tid tid, count(*) count from (SELECT * FROM (select * from (select * from lcts where not ( tid = 15 and not now() between estart and eend)) a) l WHERE SQRT(POW(l.MAP_x - ?, 2) + POW(l.MAP_y - ?, 2)) <= (5 /111.2 ) and l.map_x != ? and l.MAP_Y != ? and lccd != 0) a group by tid order by tid desc',
	  [request.query.mapX,request.query.mapY,request.query.mapX,request.query.mapY], 
	  function(err, rows, fields) { 
		  if (err) throw err;
		  response.writeHead(200, {
			'Content-Type' : 'application/json;charset=UTF-8' 
		  });
		  var data = new Array();
		  for (var i = 0; i < rows.length; i++) {
			  var rec = new Object();
			  rec.tid = rows[i].tid;
			  rec.count = rows[i].count;
			  data.push(rec);
		  }
		  data = JSON.stringify(data)
		  response.write(data);
		  response.end();
	});
});

app.listen(9999, function () {
  console.log('around on port 9999!');
});
