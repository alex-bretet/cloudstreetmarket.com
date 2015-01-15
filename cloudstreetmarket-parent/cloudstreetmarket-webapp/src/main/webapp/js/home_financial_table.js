cloudStreetMarketApp.factory("financialMarketsFactory", function () {
	var data=[];
    return {
        fetchData: function () {
        	return data;
        },
        pull: function () {
        	
        	$.each( dailyIndicesActivity, function(index, el ) {
        		if(el.latestChange >=0){
        			dailyIndicesActivity[index].style='text-success';
        		}
        		else{
        			dailyIndicesActivity[index].style='text-error';
        		}
        	});
        	
        	data = dailyIndicesActivity;
        }
    }
});

	
cloudStreetMarketApp.controller('homeFinancialTableController', function ($scope, financialMarketsFactory){
	   financialMarketsFactory.pull();
	   $scope.financialMarkets = financialMarketsFactory.fetchData();
});