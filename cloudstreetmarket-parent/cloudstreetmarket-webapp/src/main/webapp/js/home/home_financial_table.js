cloudStreetMarketApp.factory("indicesTableFactory", function (httpAuth) {
    return {
        get: function (market) {
        	return httpAuth.get("/api/indices/"+market+".json?ps=6");
        }
    }
});

cloudStreetMarketApp.controller('homeFinancialTableController', function ($scope, indicesTableFactory){
	
	$scope.init = function () {
		indicesTableFactory.get($scope.preferedMarket).success(function(data, status, headers, config) {
    		dailyIndicesActivity = data.content;
    		if(dailyIndicesActivity){
            	$.each( dailyIndicesActivity, function(index, el ) {
            		if(el.latestChange >=0){
            			dailyIndicesActivity[index].style='text-success';
            		}
            		else{
            			dailyIndicesActivity[index].style='text-error';
            		}
            	});
    		}
        	$scope.indicesForTable = dailyIndicesActivity;
        });
	}
	
	//will be pull from user preference
	$scope.preferedMarket="EUROPE";
	
	$scope.indicesForTable = [];
	$scope.init();
	
});