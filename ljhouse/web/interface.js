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
          }
        ],
        pages:10
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