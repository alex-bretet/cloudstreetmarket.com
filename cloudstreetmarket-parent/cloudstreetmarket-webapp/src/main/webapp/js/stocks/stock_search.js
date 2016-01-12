cloudStreetMarketApp.service("dynStockSearchService", function ($timeout) {
    return {
    	triggerAnim: function (id, field, oldValue, newValue) {
	   		 if(oldValue[field] > newValue[field]){
	   			 $("#td_"+oldValue.domId+"_"+field).addClass("bg-upd-less active");
	   		 }
	   		 else if(oldValue[field] < newValue[field]){
	   			 $("#td_"+oldValue.domId+"_"+field).addClass("bg-upd-more active");
	   		 }
        },
    	clearAllAnim: function () {
			$(".bg-upd-more").removeClass("bg-upd-more");
			$(".bg-upd-less").removeClass("bg-upd-less");
       },
       fadeOutAnim: function () {
			 $timeout(function() {
					$(".bg-upd-more.active").removeClass("active");
					$(".bg-upd-less.active").removeClass("active");
		     }, 1.5);
	   }
    }
});

cloudStreetMarketApp.factory("stockTableFactory", function (httpAuth) {
    return {
        get: function (ps, pn, cn, sw, sf, sd) {
        	return httpAuth.get("/api/products/stocks.json?sw="+sw+"&cn="+cn+"&size="+ps+"&page="+pn+"&sort="+sf+","+sd);
        }
    }
});

cloudStreetMarketApp.controller('stockSearchController', function PaginationCtrl($scope, $scope, $interval, $timeout, httpAuth, stockTableFactory, dynStockSearchService){
	  $scope.loadPage = function () {
		  stockTableFactory.get($scope.pageSize, $scope.currentPage, $scope.stockSearch, $scope.startWith, $scope.sortedField, $scope.sortDirection)
			.success(function(data, status, headers, config) {
				updatePaginationStockS ($scope, data);
	        });
	  }

	  /*
	   * Pagination
	   */
	  $scope.setPage = function (pageNo) {
	    $scope.currentPage = pageNo-1;
	    $scope.loadPage();
	  };
	  initPaginationStockS ($scope, $scope, $interval, httpAuth, dynStockSearchService);
	  
	  /*
	   * Request spec.
	   */
	  $scope.setStartWith = function (event, startWith) {
		  event.preventDefault();
		  $scope.startWith = (startWith!==$scope.startWith) ? startWith : ""; 
		  $scope.loadPage();
	  };
	  
	  $scope.setContains = function () {
		  $scope.containsSubmitted = $scope.stockSearch;
		  $scope.loadPage();
	  };
	  
	  $scope.currencyPopulated = function (item) { 
		    return item.currency !== 'undefined' && item.currency !== null; 
	  };

	  $scope.startWith = "";
	  $scope.stockSearch ="";
	  $scope.letters = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
	  
	  /*
	   * Sorting
	   */
	  $scope.setSort = function(field) {
		  updateSortParamStockS ($scope, field)
	  }
	  
	  $scope.loadPage();
});

/**
 * Static functions
 */
function updatePaginationStockS ($scope, data){
	$scope.tickers = [];
	$scope.stocks = data.content;
	$scope.stocks.forEach(function(entry) {
		$scope.tickers.push(entry.id);
		entry['domId'] = entry.id.replace(/\W/g, '');
	});
	
    $scope.currentPage = data.page.number;
    $scope.paginationCurrentPage = data.page.number+1;
    $scope.paginationTotalItems =  data.page.totalElements;//number total of objects

}

function updateSortParamStockS ($scope, field){
	  if( $scope.sortedField == field){
		  $scope.sortDirection = ($scope.sortDirection === "asc")? "desc" : "asc";
	  }
	  else{
		  $scope.sortDirection = "asc";
	  }
	  $scope.sortedField = field;
	  $scope.loadPage()
}

function initPaginationStockS ($scope, $scope, $interval, httpAuth, dynStockSearchService){
	$scope.sortedField = "name";
	$scope.sortDirection = "asc";
	
	$scope.maxSize = 6; //number of visible buttons for pages
	$scope.currentPage = 0;
	$scope.paginationTotalItems = 5;
	$scope.paginationCurrentPage = 1;
	$scope.pageSize = 15;
	$scope.stocks = [];

	if(httpAuth.isUserAuthenticated()){

		window.socket = new SockJS('/ws/channels/private');
		window.stompClient = Stomp.over(window.socket);
		var queueId = httpAuth.generatedQueueId();
		
		window.socket.onclose = function() {
			window.stompClient.disconnect();
		};
		window.stompClient.connect({}, function(frame) {

			var intervalPromise = $interval(function() {
				window.stompClient.send('/app/queue/CSM_QUEUE_'+queueId, {}, JSON.stringify($scope.tickers)); 
	        }, 5000);
			
			$scope.$on(
	                "$destroy",
	                function( event ) {
	                	$interval.cancel( intervalPromise );
	                	window.stompClient.disconnect();
	                }
	        );
			
			window.stompClient.subscribe('/queue/CSM_QUEUE_'+queueId, function(message){
				 var freshStocks = JSON.parse(message.body);
				 $scope.stocks.forEach(function(existingStock) {

					 freshStocks.forEach(function(newEntry) {
						 if(existingStock.id == newEntry.id){
							 if(existingStock.dailyLatestValue != newEntry.dailyLatestValue){
								 dynStockSearchService.triggerAnim(existingStock.id, "dailyLatestValue", existingStock, newEntry);
								 existingStock.dailyLatestValue = newEntry.dailyLatestValue;
							 }
							 if(existingStock.dailyLatestChange != newEntry.dailyLatestChange){
								 existingStock.dailyLatestChange = newEntry.dailyLatestChange;
							 }
							 if(existingStock.dailyLatestChangePercent != newEntry.dailyLatestChangePercent){
								 existingStock.dailyLatestChangePercent = newEntry.dailyLatestChangePercent;
							 }
							 if(existingStock.previousClose != newEntry.previousClose){
								 existingStock.previousClose = newEntry.previousClose;
							 }
							 if(existingStock.open != newEntry.open){
								 existingStock.open = newEntry.open;
							 }
							 if(existingStock.high != newEntry.high){
								 dynStockSearchService.triggerAnim(existingStock.id, "high", existingStock, newEntry);
								 existingStock.high = newEntry.high;
							 }
							 if(existingStock.low != newEntry.low){
								 dynStockSearchService.triggerAnim(existingStock.id, "low", existingStock, newEntry);
								 existingStock.low = newEntry.low;
							 }
						 }
					 });
				 });
				 
	        	 $scope.$apply();
	        	 dynStockSearchService.fadeOutAnim();

	         });
	    });
		
	};
}

