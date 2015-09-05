cloudStreetMarketApp.factory("stockDetailFactory", function (httpAuth) {
    return {
        get: function (ticker) {
        	return httpAuth.get("/api/products/stocks/"+ticker+".json");
        },
        getUrl: function (url) {
        	return httpAuth.get(url);
        },
        setGraph: function (url) {
        	var xmlHTTP = new XMLHttpRequest();
            xmlHTTP.open('GET',url,true);
            httpAuth.setHeaders(xmlHTTP);
            // Must include this line - specifies the response type we want
            xmlHTTP.responseType = 'arraybuffer';
            xmlHTTP.onload = function(e){
                var arr = new Uint8Array(this.response);
                var raw = String.fromCharCode.apply(null,arr);
                document.getElementById("detailChart").src = "data:image/png;base64,"+btoa(raw);
            };
            xmlHTTP.send();
        },
    }
});

cloudStreetMarketApp.controller('stockDetailController', function ($scope, httpAuth, errorHandler, stockDetailFactory, genericAPIFactory, accountManagementFactory, $routeParams){
	
	  $scope.data= {
			  quantity: 0,
			  type: null,
			  quoteId: 0,
			  userId: null
	  };

	  $scope.init = function () {
		  
		stockDetailFactory.get($routeParams.ticker)
			.success(function(data, status, headers, config) {
				$scope.stock = data;
				$(data.links).each(function(index, value) { 
					if(value.rel){
						if(value.rel=="chart"){
							stockDetailFactory.setGraph(value.href+"?type=HISTO&average=m20");
						}
						if(value.rel=="exchange"){
							stockDetailFactory.getUrl(value.href)
								.success(function(data, status, headers, config) {
									$scope.exchange = data;
								});
						}
						if(value.rel=="industry"){
							stockDetailFactory.getUrl(value.href)
								.success(function(data, status, headers, config) {
									$scope.industry = data;
								});
						}
						if(value.rel=="stock-quote"){
							stockDetailFactory.getUrl(value.href)
							.success(function(data, status, headers, config) {
								$scope.quote = data;
								
								if(httpAuth.isUserAuthenticated()){
									
									if($scope.quote.ask){
										genericAPIFactory.get("/api/users/"+httpAuth.getLoggedInUser()+".json")
										.success(function(data, status, headers, config) {
											$scope.user = data;
											
											$scope.maxQuantity = Math.floor($scope.user.balance / $scope.quote.ask);
											
											if($scope.user.currency != $scope.stock.currency){
												  accountManagementFactory.getCurrencyX($scope.stock.currency+$scope.user.currency+"=X")
													.success(function(data, status, headers, config) {
														$scope.currencyExchange = data.ask;
														$scope.maxQuantity = Math.floor($scope.user.balance / ($scope.currencyExchange * $scope.quote.ask));
												  });
											}

										});
									};
									
									genericAPIFactory.get($scope.transactionUrl +".json?user="+httpAuth.getLoggedInUser()+"&ticker="+$scope.stock.id)
									.success(function(data, status, headers, config) {
										
										var totalOwned = 0;
										
										$(data.content).each(function(index, value) {
											if(value.type.type=="BUY"){
												totalOwned = totalOwned + parseInt(value.quantity);
											}
											else if(value.type.type=="SELL"){
												totalOwned = totalOwned - parseInt(value.quantity);
											}
										});

										$scope.totalOwned = totalOwned;
									});
									
								}

							});
						}
					}
				});
	    });
		
	  //Init
	  }

	  $scope.buyButton = function (buyin) {
		  if(!buyin){
			  $scope.sell = false;
			  $scope.buy = !$scope.buy;
			  $scope.quantity = 0;
		  }
	  };
	  
	  $scope.sellButton = function (sellin) {
		  if(!sellin){
			  $scope.buy = false;
			  $scope.sell = !$scope.sell;
			  $scope.quantity = 0;
		  }
	  };
	  
	  $scope.cancel = function () {
		  $scope.buy = false;
		  $scope.sell = false;
		  $scope.quantity = 0;
	  };

	  $scope.createTransaction = function (number, operation) {
		  $scope.data.quantity = number;
		  $scope.data.type = operation;
		  $scope.data.quoteId = $scope.quote.id;
		  $scope.data.userId = httpAuth.getLoggedInUser();
		  
		  var baseTransactionUrl = $scope.transactionUrl;
		  
		  if ($scope.transactionUrl.indexOf("?") >-1 ){
			  baseTransactionUrl = $scope.transactionUrl.substr(0,url.indexOf("?"));
		  }
		  
		  genericAPIFactory.post(baseTransactionUrl, $scope.data)
			.success(function(data, status, headers, config) {
				$scope.transactionComplete = true;
				if(status = 201){
					$scope.transactionSuccess = true;
					
					if(data.quoteId != $scope.data.quoteId){
						$(data.links).each(function(index, value) {
							if(value.rel=="stock-quote"){
								stockDetailFactory.getUrl(value.href)
								.success(function(quoteData, status, headers, config) {
									$scope.quote = quoteData;
								});
							}
						});
					};
					
					$scope.data = data;
				}
				else{
					$scope.transactionSuccess = false;
				}
				
		  }).error(function(data, status, headers, config) {
			  $scope.transactionComplete = true;
			  $scope.transactionSuccess = false;
			  $scope.serverErrorMessage = errorHandler.render(data);
		  });  
	  };
	  
	  $scope.user = 0;
	  $scope.currencyExchange = 1;
	  $scope.ticker = $routeParams.ticker;
	  
	  $scope.buy = false;
	  $scope.sell = false;
	  
	  $scope.stock = null;
	  $scope.quote = null;
	  $scope.transactions = null;
	  $scope.totalOwned = 0;
	  
	  $scope.exchange = null;
	  $scope.industry = null;
	  $scope.transactionUrl = null;
	  
	  $scope.quantity = 0;
	  $scope.minQuantity = 0;
	  $scope.maxQuantity = 99999;
	  
	  $scope.transactionComplete = false;
	  $scope.transactionSuccess = false;
	  $scope.serverErrorMessage;
	  
	  $scope.transactionUrl = "/api/actions/transactions";
	  
	  $scope.init();
	  
	  $scope.assertAbsoluteValue = function () {
		  $scope.quantity = Math.abs($scope.quantity).toFixed(0);
	  }
	  
	  $scope.parseInt = function (value) {
		  return parseInt(value);
	  }
});
