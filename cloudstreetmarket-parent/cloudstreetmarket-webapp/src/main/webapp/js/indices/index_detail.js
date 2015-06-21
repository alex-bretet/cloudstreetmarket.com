cloudStreetMarketApp.factory("indexDetailFactory", function (httpAuth) {
    return {
        get: function (ticker) {
        	return httpAuth.get("/api/indices/"+ticker+".json");
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

cloudStreetMarketApp.controller('indexDetailController', function ($scope, httpAuth, indexDetailFactory, $routeParams){
	  
	  $scope.init = function () {
		  indexDetailFactory.get($routeParams.ticker)
			.success(function(data, status, headers, config) {
				$scope.index = data;
				$(data.links).each(function(index, value) { 
					if(value.rel){
						if(value.rel=="chart"){
							indexDetailFactory.setGraph(value.href+".png?type=HISTO&average=m20");
						}
						if(value.rel=="exchange"){
							indexDetailFactory.getUrl(value.href)
								.success(function(data, status, headers, config) {
									$scope.exchange = data.exchange;
								});
						}
					}
				});
	        });
	  }

	  $scope.ticker = $routeParams.ticker;
	  $scope.index = null;
	  $scope.exchange = null;
	  $scope.init();
});
