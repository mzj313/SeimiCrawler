;(function ($, window, document, undefined) {
	initQu();

	function initQu() {
		ajaxRequest.getQuList().then(function(obj){
			//console.log(obj);
			$.each(obj.list, function(index, e){
				console.log(e);
				$('#div1 #quComb').append("<option value='" + e.id + "'>" + e.name + "</option>");
				$('#div2 #quComb').append("<option value='" + e.id + "'>" + e.name + "</option>");
			});
		},function(err){
			//console.error(err);
		});
	}

    $(".div #quComb").change(function() {
		var quid = $(this).val();
		var divid = $(this).parents('.div')[0].id;
		var shequComb = $(this).parents('.div').find('select[name="shequComb"]')[0];
		console.log(shequComb);
		initShequ(shequComb,quid);
	});

	function initShequ(shequComb,quid){
		var newsParam={quid:''};
		newsParam.quid = quid;
		ajaxRequest.getShequList(newsParam).then(function(obj){
			$(shequComb).empty();
			$.each(obj.list, function(index, e){
				$(shequComb).append("<option value='" + e.id + "'>" + e.name + "</option>");
			});
		},function(err){
			console.error(err);
		});
	}

	$("#btnOk").click(function() {
		draw();
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
			//legend: {
            //    data:['aaa','bbb']
            //},
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


})(jQuery, window, document);