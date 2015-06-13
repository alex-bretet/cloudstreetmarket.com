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

cloudStreetMarketApp.controller('stockDetailController', function ($scope, httpAuth, stockDetailFactory, $routeParams){
	  $scope.init = function () {
		  stockDetailFactory.get($routeParams.ticker)
			.success(function(data, status, headers, config) {
				$scope.stock = data.product;
				$(data.links).each(function(index, value) { 
					if(value.rel){
						if(value.rel=="chart"){
							stockDetailFactory.setGraph(value.href+".png?type=HISTO&average=m20");
						}
						if(value.rel=="exchange"){
							stockDetailFactory.getUrl(value.href)
								.success(function(data, status, headers, config) {
									$scope.exchange = data.exchange;
								});
						}
						if(value.rel=="industry"){
							stockDetailFactory.getUrl(value.href)
								.success(function(data, status, headers, config) {
									$scope.industry = data.industry;
								});
						}
					}
				});
	        });
	  }

	  $scope.ticker = $routeParams.ticker;
	  $scope.stock = null;
	  $scope.exchange = null;
	  $scope.industry = null;
	  $scope.init();
});
