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

cloudStreetMarketApp.factory("transactionFactory", function (httpAuth) {
    return {
        get: function (url) {
        	return httpAuth.get(url);
        }
    }
});

cloudStreetMarketApp.controller('stockDetailController', function ($scope, httpAuth, stockDetailFactory, transactionFactory, $routeParams){
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
									$scope.exchange = data.exchange;
								});
						}
						if(value.rel=="industry"){
							stockDetailFactory.getUrl(value.href)
								.success(function(data, status, headers, config) {
									$scope.industry = data.industry;
								});
						}
						if(value.rel=="transactions"){
							$scope.transactionUrl = value.href;
						}
						if(value.rel=="stock-quote"){
							stockDetailFactory.getUrl(value.href)
							.success(function(data, status, headers, config) {
								
							});
						}
					}
				});
	        });
	  }

	  $scope.buyButton = function () {
		  $scope.sell = false;
		  $scope.buy = !$scope.buy;
		  $scope.quantity = 0;
	  };
	  
	  $scope.sellButton = function () {
		  $scope.buy = false;
		  $scope.sell = !$scope.sell;
		  $scope.quantity = 0;
	  };
	  
	  $scope.ticker = $routeParams.ticker;
	  $scope.buy = false;
	  $scope.sell = false;
	  $scope.quantity = 0;
	  $scope.stock = null;
	  $scope.exchange = null;
	  $scope.industry = null;
	  $scope.transactionUrl = null;
	  $scope.init();

	  $('.btn-number').click(function(e){
		    e.preventDefault();
		    fieldName = $(this).attr('data-field');
		    type      = $(this).attr('data-type');
		    var input = $("input[name='"+fieldName+"']");
		    var currentVal = parseInt(input.val());
		    if (!isNaN(currentVal)) {
		        if(type == 'minus') {
		            if(currentVal > input.attr('min')) {
		                input.val(currentVal - 1).change();
		            } 
		            if(parseInt(input.val()) == input.attr('min')) {
		                $(this).attr('disabled', true);
		            }
		        } else if(type == 'plus') {
		            if(currentVal < input.attr('max')) {
		                input.val(currentVal + 1).change();
		            }
		            if(parseInt(input.val()) == input.attr('max')) {
		                $(this).attr('disabled', true);
		            }
		        }
		    } else {
		        input.val(0);
		    }
		});
	  
		$('.input-number').focusin(function(){
		   $(this).data('oldValue', $(this).val());
		});
		
		$('.input-number').change(function() {
		    minValue =  parseInt($(this).attr('min'));
		    maxValue =  parseInt($(this).attr('max'));
		    $scope.quantity = parseInt($(this).val());
		    name = $(this).attr('name');
		    if($scope.quantity >= minValue) {
		        $(".btn-number[data-type='minus'][data-field='"+name+"']").removeAttr('disabled')
		    } else {
		        $(this).val($(this).data('oldValue'));
		    }
		    if($scope.quantity <= maxValue) {
		        $(".btn-number[data-type='plus'][data-field='"+name+"']").removeAttr('disabled')
		    } else {
		        $(this).val($(this).data('oldValue'));
		    }
		});
		
		$(".input-number").keydown(function (e) {
		        // Allow: backspace, delete, tab, escape, enter and .
		        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 190]) !== -1 ||
		             // Allow: Ctrl+A
		            (e.keyCode == 65 && e.ctrlKey === true) || 
		             // Allow: home, end, left, right
		            (e.keyCode >= 35 && e.keyCode <= 39)) {
		                 // let it happen, don't do anything
		                 return;
		        }
		        // Ensure that it is a number and stop the keypress
		        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
		            e.preventDefault();
		        }
		});
});
