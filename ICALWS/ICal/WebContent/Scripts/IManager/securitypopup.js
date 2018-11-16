iCal.directive('dir1', function () {
    return {
        link: function(scope, element, attrs) {
            scope.$watch(attrs.dir1, function(value) {
                if(value === true) {
                    element[0].focus();
                    element[0].select();
                }
            });
        }
    };
});
iCal.controller('indexSecurityController', function ($scope, $window, IManagerService,icalFactory,$http) 
{
	var baseURL = icalFactory.baseUrl;
	$scope.getAllCurrencies = function ()
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/currency/getCurrencies';
	    return $http({ 
	    	method  : 'GET',
	    	url     : baseUrl,
	    	
	    }).then(function (response) {
	    	console.log(response.data);
	    	icalFactory.currencyList = response.data;
	    	$scope.CData = response.data;
            return response.data;
        });
    }
	$scope.getAllClient = function ()
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/client/getAllClients';
	    return $http({ 
	    	method  : 'GET',
	    	url     : baseUrl,
	    	
	    }).then(function (response) {
	    	console.log(response.data);
	    	icalFactory.clientList = response.data;
	    	$scope.ClientData = response.data;
            return response.data;
        });
    }
	
	icalFactory.configValues()
	.then(function (response)
	{
		$scope.getAllCurrencies();
		$scope.getAllClient();
//		console.log("configValues::" + icalFactory.configData);
    });
	
	$scope.index = {};
	$scope.indexCode = $window.indexTicker;
	$scope.index = $window.indexData;
//	console.log($scope.indexCode);
//	$scope.index.indexName = $window.indexData.indexName;
	console.log($scope.index);
//	console.log($scope.index.indexName);
	$scope.CData = icalFactory.currencyList;
	console.log($scope.CData);
	$scope.ClientData = icalFactory.clientList;
	console.log($scope.ClientData);
	
	$scope.loadIndexSecurities = function() 
	{
		IManagerService.getAllIndexSecurities($scope.indexCode).then(function (response) 
		{
			$scope.SData = response;
			console.log($scope.SData);
    	});
    }	

	$scope.loadIndexSecurities();
    $scope.gridOptionsS = 
	{
		data: 'SData',
		columnDefs: 
		[
			{
				field: 'indexCode',
				displayName: 'Index Code',
           		enableCellEdit: false,
           		width: '120px'
			},{
				field: 'fullName',
				displayName: 'Security Name',
           		enableCellEdit: false,
           		width: '120px'
			},{
				field: 'BBGTicker',
				displayName: 'BBG Ticker',
           		enableCellEdit: false,
           		width: '120px'
			},{
				field: 'ISIN',
				displayName: 'ISIN',
           		enableCellEdit: false,
           		width: '120px'
			},{
				field: 'weight',
				displayName: 'Weight',
           		enableCellEdit: false,
           		width: '120px'
			},{
				field: 'shares',
				displayName: 'Shares',
           		enableCellEdit: false,
           		width: '120px'
			},{
				field: 'price',
				displayName: 'Price',
           		enableCellEdit: false,
           		width: '120px'
			},{
				field: 'currency',
				displayName: 'Currency',
           		enableCellEdit: false,
           		width: '120px'
			},{
				field: 'currencyFactor',
				displayName: 'Currency Factor',
           		enableCellEdit: false,
           		width: '120px'
			}
		]
	}; 
    
    $scope.update = function(row) 
    {
    	alert($scope.index);
    };
    $scope.remove = function(row) 
    {
        if (window.confirm("Are you sure you want to Delete this record?")) $scope.result = "Yes";  
        else $scope.result = "No";  
        if ($scope.result == "Yes")
        { 
        	IManagerService.deleteSecurityFromIndex(row.entity).then(function (response) 
	    	{
        		alert(response);
        		$scope.load();
	        });
        }
    };
});
