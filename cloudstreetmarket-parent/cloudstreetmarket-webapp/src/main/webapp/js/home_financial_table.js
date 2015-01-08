cloudStreetMarketApp.factory("financialMarketsFactory", function () {
	var data=[];
    return {
        fetchData: function () {
        	return data;
        },
        pull: function () {
        	
        	$.each( dailyMarketsActivity, function(index, el ) {
        		if(el.latestChange >=0){
        			dailyMarketsActivity[index].style='text-success';
        		}
        		else{
        			dailyMarketsActivity[index].style='text-error';
        		}
        	});
        	
        	data = dailyMarketsActivity;
        }
    }
});

	
cloudStreetMarketApp.controller('homeFinancialTableController', function ($scope, financialMarketsFactory){
	   financialMarketsFactory.pull();
	   $scope.financialMarkets = financialMarketsFactory.fetchData();
});