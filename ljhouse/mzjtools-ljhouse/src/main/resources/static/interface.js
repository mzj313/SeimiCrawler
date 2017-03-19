;(function ($, window, document, undefined) {
  var ajaxRequest = {};

  ajaxRequest.getQuList = function() {
    var dtd = $.Deferred();
    dtd.resolve(
      {
        "list":[
          {
			"id":1,
            "name":"西城"
          },
		  {
			"id":2,
            "name":"东城"
          },
          {
      		"id": 3,
      		"name": "海淀"
      	},
      	{
      		"id": 4,
      		"name": "朝阳"
      	},
      	{
      		"id": 5,
      		"name": "石景山"
      	},
      	{
      		"id": 6,
      		"name": "丰台"
      	},
      	{
      		"id": 7,
      		"name": "通州"
      	},
      	{
      		"id": 8,
      		"name": "门头沟"
      	},
      	{
      		"id": 9,
      		"name": "顺义"
      	},
      	{
      		"id": 10,
      		"name": "大兴"
      	},
      	{
      		"id": 11,
      		"name": "房山"
      	},
      	{
      		"id": 12,
      		"name": "昌平"
      	},
      	{
      		"id": 13,
      		"name": "亦庄开发区"
      	},
      	{
      		"id": 14,
      		"name": "燕郊"
      	}]
      }
    );
    return dtd.promise(); // 返回promise对象
  }
  
  ajaxRequest.getShequList = function() {
    var dtd = $.Deferred();
    dtd.resolve(
      {
        "list":[
          {
			"name":"西直门",
            "quname":"西城",
            "quid":1
          },
		  {
			"name":"安贞",
            "quname":"东城",
            "quid":2
          }
        ],
        pages:10
      }
    );
    return dtd.promise(); // 返回promise对象
  }
  
  //
  ajaxRequest.getJsonData = function(obj){
      var dtd = $.Deferred(); // 新建一个Deferred对象
      $.ajaxSetup( {
          url: obj.url , // 默认URL
          error: function(jqXHR, textStatus, errorMsg){ // 出错时默认的处理函数
              dtd.reject(jqXHR.status);
          }
      } );

    $.getJSON(obj.url, function(json, textStatus) {
        if(textStatus == 'success'){
          dtd.resolve(json);
        }
    });
    return dtd.promise(); // 返回promise对象
  };
  window.ajaxRequest = ajaxRequest;
})(jQuery, window, document);    