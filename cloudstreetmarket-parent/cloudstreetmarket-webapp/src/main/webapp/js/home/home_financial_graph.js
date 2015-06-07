cloudStreetMarketApp.factory("indicesGraphFactory", function (httpAuth) {
    return {
        getHistoIndex: function (market, index) {
        	return httpAuth.get("/api/indices/"+index+"/histo.json");
        },
        getIndices: function (market) {
        	return httpAuth.get("/api/indices.json?market="+market+"&size=6");
        }
    }
});

cloudStreetMarketApp.controller('homeFinancialGraphController', function ($scope, indicesGraphFactory){
	
	$scope.init = function () {

		var indicesPromise = indicesGraphFactory.getIndices($scope.preferedMarket);
		indicesPromise.success(function(dataIndices, status, headers, config) {
			$scope.indicesForGraph = dataIndices.content;
			if($scope.indicesForGraph){
				$scope.drawGraph();
			}
	    })

		$('.form-control').on('change', function () {
			$('#landingGraphContainer svg').remove();
			$scope.currentIndex = this.value;
			$scope.drawGraph();
		});
	}
	
	$scope.drawGraph = function () {

		var market = jQuery.map($scope.indicesForGraph, function(obj) {
		    if(obj.code == $scope.currentIndex)
		         return obj;
		})[0].market;
		
		var indexData = indicesGraphFactory.getHistoIndex(market, $scope.currentIndex);
		indexData.success(function(data, status, headers, config) {
			
			var financial_data = [];

			$(data.values).each(function( index , elem) {
				financial_data.push({"period": Object.keys(elem)[0], "index": elem[Object.keys(elem)[0]]});
			});

		    Morris.Line({
		        element: 'landingGraphContainer',
		        hideHover: 'auto',
		        data: financial_data,
		        ymax: data.maxValue,
		        ymin: data.minValue,
		        pointSize: 3,
		        hideHover:'always',
		        xkey: 'period',
		        xLabels: 'time',
		        ykeys: ['index'],
		        postUnits: '',
		        parseTime: false,
		        labels: ['Index'],
		        resize: true,
		        smooth: false,
		        lineColors: ['#A52A2A']
		      });
	    });
	}
	
	//will be pull from user preference
	$scope.preferedMarket="EUROPE";
	
	$scope.currentIndex="^GDAXI";
	$scope.indices = null;
	$scope.init();

});
