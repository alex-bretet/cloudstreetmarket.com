cloudStreetMarketApp.controller('leaderBoardFooterController',  function ($scope, $rootScope, httpAuth, genericAPIFactory) {

	$scope.init = function () {
		genericAPIFactory.get("/api/users/leaderboard.json")
		.success(function(data, status, headers, config) {
			$scope.leaders = data.content;
		});
    }
	
	$scope.leaders= [];
	$scope.init();
});
