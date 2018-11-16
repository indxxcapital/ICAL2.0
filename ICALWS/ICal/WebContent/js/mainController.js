iCal.controller('mainController', ['$scope','$window','icalFactory','$http', 
	function($scope,$window,icalFactory,$http)
{
	$scope.makeActive= false;
	var baseURL = icalFactory.baseUrl;
	$scope.getAllCurrencies = function ()
	{
		if(icalFactory.currencyList == undefined )
		{
			var baseUrl = baseURL + '/ICal2Rest/rest/currency/getCurrencies';
			return $http({ 
				method  : 'GET',
				url     : baseUrl,
			}).then(function (response) {
				console.log(response.data);
				icalFactory.currencyList = response.data;
				return response.data;
			});
		}
    }
	$scope.getAllClient = function ()
	{
		
		if(icalFactory.clientList == undefined )
		{
			var baseUrl = baseURL + '/ICal2Rest/rest/client/getAllClients';
		    return $http({ 
		    	method  : 'GET',
		    	url     : baseUrl,
		    	
		    }).then(function (response) {
		    	console.log(response.data);
		    	icalFactory.clientList = response.data;
	            return response.data;
	        });
		}
    }
	
//	if( icalFactory.configData == '' )
	{
		icalFactory.configValues()
		.then(function (response)
		{
			$scope.getAllCurrencies().then(function (response)
			{
//				console.log(response.data);
				$scope.getAllClient().then(function (response)
				{
//					console.log(response.data);
					console.log('Done');	
					$scope.makeActive= true;
			    });
		    });
	    });
	}
}]);