iCal.controller('indexSecurityController', function ($scope, $window, IManagerService) 
{
	$scope.indexCode = $window.indexTicker;
	console.log($scope.indexCode);
	
	 
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
//		showSelectionCheckbox: true, 
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
				field: 'CurrencyFactor',
				displayName: 'Currency Factor',
           		enableCellEdit: false,
           		width: '120px'
			}
		]
	}; 
    
    $scope.remove = function(row) 
    {
//        alert("INSIDE remote " + row.entity.indexTicker);
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
