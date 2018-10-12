iCal.controller('CAController', ['$scope','$window','IManagerService', function($scope,$window,IManagerService)
{
	$scope.title = "Corporate Actions";
	$scope.inactiveApprove= false;
	$scope.inactiveRunIndex= false;
	$scope.selectedRows = [];
	$scope.index = {};
	$scope.index.weightType = ''; 
	 
    $scope.load = function () 
    {
    	IManagerService.getAllNewIndex($scope.index).then(function (response) 
    	{
            $scope.Data = response;
            $scope.Data1 = response;
        });
    }
  	 
    $scope.load();
    
    $scope.updateIndexList = function () 
    {
    	  $scope.load();
    }
    $scope.gridOptions1 = 
    { 	data: 'Data1',

    		columnDefs: [
    			{
    				field: 'id',
    				displayName: 'Id',
    				enableCellEdit: false,
    				width: '0px',
    				visible:false
    	    	},{
    				field: 'indexTicker',
    				displayName: 'Index Ticker',
    				enableCellEdit: false,
    				width: '120px'
    	        }
    			]
    }
    
    $scope.gridOptions = 
    { 	data: 'Data', 
		showSelectionCheckbox: true, 
		enableHorizontalScrollbar: 1,
		enableCellEditOnFocus: false,
		selectedItems:$scope.selectedRows,
		columnDefs: [
		{
			field: 'id',
			displayName: 'Id',
			enableCellEdit: false,
			width: '0px',
			visible:false
    	},
		{
			field: 'indexTicker',
			displayName: 'Index Ticker',
			enableCellEdit: false,
			width: '120px'
        }, {
        	field: 'indexName',
        	displayName: 'Index Name',
        	enableCellEdit: false,
        	width: '120px'
        }, {
        	field: 'clientName',
        	displayName: 'Client Name',
        	enableCellEdit: false,
        	width: '120px'
        },{
        	name: 'actions', 
        	width: '880', 
        	displayName: 'Actions', 
        	enableCellEdit: false,
        	cellTemplate: '<div  ng-grid="gridOptions1" class="gridStyle" ></div>'
        	
    	}]
	};   

}]);