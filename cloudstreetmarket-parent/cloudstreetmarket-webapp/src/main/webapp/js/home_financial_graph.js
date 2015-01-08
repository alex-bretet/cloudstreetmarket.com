cloudStreetMarketApp.factory("financialDataFactory", function () {
    return {
        getData: function (market) {
        	//we read the variable populated in index.jsp for the moment
        	return financial_data;
        },
        getMax: function (market) {
        	//we read the variable populated in index.jsp for the moment
        	return tmpYmax;
        },
        getMin: function (market) {
        	//we read the variable populated in index.jsp for the moment
        	return tmpYmin;
        }
    }
});

cloudStreetMarketApp.controller('homeFinancialGraphController', function ($scope, financialDataFactory){
	readSelectValue();
	drawGraph();
	
	$('.form-control').on('change', function (elem) {
		$('#landingGraphContainer').html('');
		readSelectValue()
		drawGraph();
	});
	
	function readSelectValue(){
		$scope.currentMarket = $('.form-control').val();
	}
	
	function drawGraph(){
	    Morris.Line({
	        element: 'landingGraphContainer',
	        hideHover: 'auto',
	        data: financialDataFactory.getData($scope.currentMarket),
	        ymax: financialDataFactory.getMax($scope.currentMarket),
	        ymin: financialDataFactory.getMin($scope.currentMarket),
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
	}
});
