iCal.directive('securityManager', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) 
		{
			var model = $parse(attrs.indexManager);
			var modelSetter = model.assign;
			
			element.bind('change', function()
			{
				scope.$apply(function()
				{
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
}]); 

iCal.service('SManagerService', ['$http','icalFactory', function ($http,icalFactory)
{
	var baseURL = icalFactory.baseUrl
	this.getAllSecurities = function ()
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/security/getSecurities';
	    return $http({ 
	    	method  : 'GET',
	    	url     : baseUrl,
	    	
	    }).then(function (response) {
            return response.data;
        });
    }
	
	this.getAllCurrencies = function ()
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/currency/getCurrencies';
	    return $http({ 
	    	method  : 'GET',
	    	url     : baseUrl,
	    	
	    }).then(function (response) {
            return response.data;
        });
    }
}]);

