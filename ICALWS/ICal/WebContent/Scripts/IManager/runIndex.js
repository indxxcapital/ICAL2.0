iCal.controller('runIndexController', ['$scope','$window','IManagerService','$rootScope', 
	function($scope,$window,IManagerService,$rootScope)
{
	$scope.title = "Run Indices";
	$scope.inactiveApprove= true;
	$scope.inactiveRunIndex= false;
	$scope.selectedRows = [];
    $scope.index = {};
    $scope.index.weightType = '';
    
    $scope.load = function () 
    {
    	IManagerService.getAllRunIndex($scope.index).then(function (response) 
    	{
            $scope.Data = response;
            console.log($scope.Data);
        });
    }
    
    $scope.updateIndexList = function () 
    {
    	  $scope.load();
    }
    $scope.load();
    
    $scope.gridOptions = 
	{	data: 'Data', 
    		
        showSelectionCheckbox: true,   
		enableHorizontalScrollbar: 1,
		selectedItems:$scope.selectedRows,
		enableCellEditOnFocus: false,
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
        },{
           field: 'clientName',
           displayName: 'Client Name',
           enableCellEdit: false,
           width: '120px'
        }, {
            field: 'indexWeightType',
            displayName: 'Index Weight Type',
            enableCellEdit: false,
            width: '250px'
         },{
           field: 'status',
           displayName: 'Status',
           enableCellEdit: false,
           width: '100px'
        }, {
        	field: 'currency',
        	displayName: 'Currency',
        	enableCellEdit: false,
        	width: '100px'
        }, {
        	field: 'indexLiveDateStr',
        	displayName: 'Index Live Date',
        	enableCellEdit: false,
        	width: '150px'
        },{
        	field: 'indexRunDate',
        	displayName: 'Index Run Date',
        	enableCellEdit: false,
        	width: '150px'
        }, {
        	field: 'indexMarketValue',
        	displayName: 'Market Value',
        	enableCellEdit: false,
        	width: '120px'
        }, {
        	field: 'closeIndexValue',
        	displayName: 'Index Value',
        	enableCellEdit: false,
        	width: '120px'
        },{
        	name: 'actions', 
        	width: '300',
        	displayName: 'Actions', 
        	enableCellEdit: false,
        	cellTemplate: 
        		'<button id="viewBtn" type="button" ng-click="view(row)" >View</button>'+
        		'<button id="viewPreClosingBtn" type="button" ng-click="viewPreClosing(row)" >Pre Opening File</button>'
		}]
	}; 
    
    $scope.viewPreClosing = function(row) 
    {
		 if (window.confirm("Are you sure you want to view Pre Closing file for this Index?")) $scope.result = "Yes";  
		 else $scope.result = "No";  
		 if ($scope.result == "Yes")
		 { 
			 IManagerService.getIndexPreClosingFile(row.entity);
		 }
	};
	
    $scope.approve = function(row) 
    {
        if (window.confirm("Are you sure you want to approve this Index?")) $scope.result = "Yes";  
        else $scope.result = "No";  
        if ($scope.result == "Yes")
        { 
        	if($scope.selectedRows.length == 0)
        		alert("Please select indices to be approve");
        	else
        	{
        		IManagerService.updateIndexStatus($scope.selectedRows,'AI').then(function (response) 
				{
        			alert(response);
        			 $scope.load();
				});
    		}
        }
    };
  
    $scope.remove = function(row) 
    {
    	if($scope.selectedRows.length == 0)
     		alert("Please select indices to delete");
     	else
     	{
	    	 if (window.confirm("Are you sure you want to delete selected indices?")) $scope.result = "Yes";  
	         else $scope.result = "No";  
	         if ($scope.result == "Yes")
	         { 
         		IManagerService.deleteIndex($scope.selectedRows).then(function (response) 
 				{
         			alert(response);
         			 $scope.load();
 				});
	         }
     	}
    };
    
	$scope.view = function(row) 
	{
		var $popup = $window.open("securityPopUp.html");
		$popup.indexTicker =row.entity.indexTicker;
    }	
}]);