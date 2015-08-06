cloudStreetMarketApp.controller('walletController', function ($scope, httpAuth, genericAPIFactory){
	
	  $scope.init = function () {
		  $scope.walletItems = {};
		  
		  genericAPIFactory.get("/api/users/"+httpAuth.getLoggedInUser()+".json")
			.success(function(data, status, headers, config) {
				$scope.userData = data;
		  });
		  
		  genericAPIFactory.get($scope.walletSummaryUrl)
			.success(function(data) {
				$(data).each(function(index, value) { 
					$scope.walletItems[value.symbol] = value;
					$scope.totalValuation = $scope.totalValuation + value.valuation;
					$scope.totalCost = $scope.totalCost + value.bookCost;
					$scope.totalProfit = $scope.totalProfit + value.profit;
				});
	        });
	  }
	  
	  $scope.walletSummaryUrl = "/api/accounts/wallets/"+httpAuth.getLoggedInUser();
	  $scope.totalValuation = 0;
	  $scope.totalCost = 0;
	  $scope.totalProfit = 0;
	  $scope.userData = null;
	  $scope.init();
});