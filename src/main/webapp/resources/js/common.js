var reizenUrl = "http://reizen.com:8080/";
var nodeUrl = "http://reizen.com";
//var reizenUrl = "http://localhost:8080/";
//var nodeUrl = "http://localhost";
//var reizenUrl = "http://192.168.43.6:8080/";
//var nodeUrl = "http://192.168.43.6";
//var reizenUrl = "http://192.168.0.42:8080/";
//var nodeUrl = "http://192.168.0.42";
//var reizenUrl = "http://52.78.165.93:8080/"
//var nodeUrl = "http://52.78.165.93"

$(function() {
	$('header').load('/scheduler/header.html');
	$('#modaltrigger').on('click',function(){
		$('ul > li > i').css('display','none');
	});
	$(window).resize(function(){
		var height = window.innerHeight ||
        document.documentElement.clientHeight ||
        document.body.clientHeight ||
        document.body.offsetHeight;
		$('html, body').css('height', height);
	});

});


resMap = function(){
	this.map = new Object();
};   
resMap.prototype = {   
		put : function(key, value){   
			this.map[key] = value;
		},   
		get : function(key){   
			return this.map[key];
		},
		containsKey : function(key){    
			return key in this.map;
		},
		containsValue : function(value){    
			for(var prop in this.map){
				if(this.map[prop] == value) return true;
			}
			return false;
		},
		isEmpty : function(key){    
			return (this.size() == 0);
		},
		clear : function(){   
			for(var prop in this.map){
				delete this.map[prop];
			}
		},
		remove : function(key){    
			delete this.map[key];
		},
		keys : function(){   
			var keys = new Array();   
			for(var prop in this.map){   
				keys.push(prop);
			}   
			return keys;
		},
		values : function(){   
			var values = new Array();   
			for(var prop in this.map){   
				values.push(this.map[prop]);
			}   
			return values;
		},
		size : function(){
			var count = 0;
			for (var prop in this.map) {
				count++;
			}
			return count;
		}
};


function sleep(num){	//[1/1000초]
	var now = new Date();
	var stop = now.getTime() + num;
	while(true){
		now = new Date();
		if(now.getTime() > stop)return;
	}
}


