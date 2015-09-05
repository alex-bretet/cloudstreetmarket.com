cloudStreetMarketApp.factory("indicesGraphFactory", function (httpAuth) {
    return {
        getGraph: function (index) {
        	var xmlHTTP = new XMLHttpRequest();
            xmlHTTP.open('GET',"/api/charts/indices/"+index+".png",true);
            httpAuth.setHeaders(xmlHTTP);
            // Must include this line - specifies the response type we want
            xmlHTTP.responseType = 'arraybuffer';
            xmlHTTP.onload = function(e){
                var arr = new Uint8Array(this.response);
                var raw = String.fromCharCode.apply(null,arr);
                if($("#homeChart")[0]){
                	$("#homeChart")[0].src = "data:image/png;base64,"+btoa(raw);
                }
            };
            xmlHTTP.send();
        },
        getIndices: function (market) {
        	return httpAuth.get("/api/indices.json?market="+market+"&size=6");
        }
    }
});

cloudStreetMarketApp.controller('homeFinancialGraphController', function ($scope, indicesGraphFactory){
	
	$scope.init = function () {
		var indicesPromise = indicesGraphFactory.getIndices($scope.preferedMarket);
		indicesPromise.success(function(dataIndices, status, headers, config) {
			$scope.indicesForGraph = dataIndices.content;
			if($scope.indicesForGraph){
				indicesGraphFactory.getGraph($scope.currentIndex);
			}
	    })

		$('.form-control').on('change', function (){
			$scope.currentIndex = this.value;
			indicesGraphFactory.getGraph($scope.currentIndex);
		});
	}
	
	$scope.preferedMarket="EUROPE";
	$scope.currentIndex="^GDAXI";
	$scope.indices = null;
	$scope.init();
});
