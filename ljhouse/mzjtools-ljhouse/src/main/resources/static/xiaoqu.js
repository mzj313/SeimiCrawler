;(function ($, window, document, undefined) {
	
	var base = "http://localhost:8280";
	
	initQu();

	function initQu() {
		ajaxRequest.getQuList().then(function(data){
//		$.getJSON('http://localhost:8080/lj/qu', function(data) {
			$.each(data.list, function(index, e){
				$('#div1 #quComb').append("<option value='" + e.id + "'>" + e.name + "</option>");
				$('#div2 #quComb').append("<option value='" + e.id + "'>" + e.name + "</option>");
				$('#div1 #quComb').val(3);
				$('#div2 #quComb').val(3);
			});
		},function(err){
			 console.error(err);
		});
		
		var shequCombs = $('.div').find('select[name="shequComb"]');
		$.each(shequCombs, function(index, e){
			initShequ(e);
		});
		
		var xiaoquCombs = $('.div').find('select[name="xiaoquComb"]');
		$.each(xiaoquCombs, function(index, e){
			initXiaoqu(e);
		});
	}

    $(".div #quComb").change(function() {
		var quid = $(this).val();
		var divid = $(this).parents('.div')[0].id;
		var shequComb = $(this).parents('.div').find('select[name="shequComb"]')[0];
		initShequ(shequComb,quid);
	});

	function initShequ(shequComb,quid){
		if(!quid) {
			var quComb = $(shequComb).parents('.div').find('select[name="quComb"]')[0];
			quid = $(quComb).val();
		}
		var param={quid:''};
		param.quid = quid;
//		ajaxRequest.getShequList(param).then(function(data){
		$.ajaxSettings.async = false; 
		$.getJSON(base+'/lj/shequ', param, function(data) {
			$(shequComb).empty();
			$.each(data.list, function(index, e){
				$(shequComb).append("<option value='" + e.name + "'>" + e.name + "</option>");
			});
		},function(err){
			console.error(err);
		});
		$.ajaxSettings.async = true;
//	    $(shequComb).val(1);
	}
	
	$(".div #shequComb").change(function() {
		var shequid = $(this).val();
		var divid = $(this).parents('.div')[0].id;
		var xiaoquComb = $(this).parents('.div').find('select[name="xiaoquComb"]')[0];
		initXiaoqu(xiaoquComb,shequid);
	});
	
	function initXiaoqu(xiaoquComb,shequid,quid){
		if(!shequid) {
			var shequComb = $(xiaoquComb).parents('.div').find('select[name="shequComb"]')[0];
			shequid = $(shequComb).val();
		}
		if(!quid) {
			var quComb = $(xiaoquComb).parents('.div').find('select[name="quComb"]')[0];
//			quid = $(quComb).val();
			quid = $(quComb).find('option:selected').text();
		}	
		var param={quid:'',shequid:''};
		param.quid = quid;
		param.shequid = shequid;
		console.log(param);
//		ajaxRequest.getShequList(param).then(function(data){
		$.getJSON(base+'/lj/xiaoqu', param, function(data) {
			$(xiaoquComb).empty();
			$.each(data.list, function(index, e){
				$(xiaoquComb).append("<option value='" + e.name + "'>" + e.name + "</option>");
			});
		},function(err){
			console.error(err);
		});
	}

	$("#btnOk").click(function() {
//		draw();
		drawLines();
	});

	function draw() {
		// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '按社区统计均价'
            },
            tooltip: {},
			legend: {
                data:['aaa','bbb']
            },
			toolbox: {
				feature: {
					saveAsImage: {}
				}
			},
            xAxis: {
			    type : 'category',
                boundaryGap : true,
                data: ["2013","2014","2015","2016","2017"]
            },
            yAxis: {
			    type : 'value'
			},
            series: [
			{
                name: '成交均价1',
                type: 'line',
                data: [ 2.5, 3.5, 4.4, 5, 7]
            },
			{
                name: '成交均价2',
                type: 'line',
                data: [ 2, 3, 4.4, 6, 8]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        console.log(option);
        myChart.setOption(option);
	}

	

  // 异步加载数据
  function loadData() {
	  $.post('/Ajax_Test/helloBar').done(function(data) {
	   var str = eval_r('(' + data + ')');
	   myChart.hideLoading();
	   // 填入数据
	   myChart.setOption({
		xAxis : {
		 data : str.name
		},
		series : [ {
		 // 根据名字对应到相应的系列
		 name : '人物信息',
		 type : 'bar',
		 data : str.id
		} ]
	   });
	  });
  }
  
	function drawLines() {
		var myChart = echarts.init(document.getElementById('main'));
		var xiaoquCombs = $('.div').find('select[name="xiaoquComb"]');
		var arr = [];
		$.each(xiaoquCombs, function(index, e){
			if(e.selectedIndex >= 0) {
				arr[index]=e[e.selectedIndex].innerHTML;
			}
		});
		
		var param = {xiaoqu : arr.join(',')};
		$.getJSON(base+'/lj/xiaoquPrice', param, function(data) {
			myChart.clear();
			myChart.showLoading({
				text : '正在努力的读取数据中...'
			});
			console.log(param,data);
			if (data) {
				myChart.setOption(data, true);
				myChart.hideLoading();
			} else {
				console.log('提示', data);
			}
		});
	}

})(jQuery, window, document);