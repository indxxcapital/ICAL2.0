iCal.controller('securityManagerController', ['$scope','$window','IManagerService', function($scope,$window,IManagerService)
{
	$scope.title = "All Securities";
	
    $scope.loadSecurities = function () 
    {
    	IManagerService.getAllSecurities().then(function (response) 
    	{
            $scope.SData = response;
        });
    }
		 
    $scope.loadSecurities();
	
    $scope.gridOptions = 
	{
		data: 'SData', 
		enableHorizontalScrollbar: 1,
		columnDefs: 
		[
			{
				field: 'fullName',
				displayName: 'Security Name',
           		enableCellEdit: false,
           		width: '250px'
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
				field: 'country',
				displayName: 'Country',
           		enableCellEdit: false,
           		width: '120px'
			},{
				field: 'industry',
				displayName: 'Industry',
           		enableCellEdit: false,
           		width: '250px'
			},{
				field: 'sector',
				displayName: 'Sector',
           		enableCellEdit: false,
           		width: '200px'
			}
		]
	};  
}]);


