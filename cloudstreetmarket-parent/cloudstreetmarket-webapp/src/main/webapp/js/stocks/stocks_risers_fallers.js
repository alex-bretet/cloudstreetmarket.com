cloudStreetMarketApp.factory("stocksRisersAndFallersFactory", function ($http) {
    return {
        get: function (ps, pn, sf, sd) {
        	return $http.get("/api/products/stocks.json?size="+ps+"&page="+pn+"&sort="+sf+","+sd);
        }
    }
});

cloudStreetMarketApp.controller('stocksRisersController', function PaginationCtrl($scope, stocksRisersAndFallersFactory){
	  $scope.loadPage = function () {
		stocksRisersAndFallersFactory.get(10, 0, "dailyLatestChangePercent", "desc")
			.success(function(data, status, headers, config) {
				$scope.risers = data.content;
	        });
	  }

	  //Init.
	  initIndicesBM($scope);
});

cloudStreetMarketApp.controller('stocksFallersController', function ($scope, stocksRisersAndFallersFactory){
	  $scope.loadPage = function () {
		stocksRisersAndFallersFactory.get(10, 0, "dailyLatestChangePercent", "asc")
			.success(function(data, status, headers, config) {
				$scope.fallers = data.content;
	        });
	  }
	  
	  //Init.
	  initStocksRF ($scope);
});

function initStocksRF ($scope){
	$scope.risers = [];
	$scope.fallers = [];
	$scope.loadPage();
}