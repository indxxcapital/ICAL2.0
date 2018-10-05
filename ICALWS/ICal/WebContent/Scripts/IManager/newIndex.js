iCal.directive('focusMe', function () {
    return {
        link: function(scope, element, attrs) {
            scope.$watch(attrs.focusMe, function(value) {
                if(value === true) {
                    element[0].focus();
                    element[0].select();
                }
            });
        }
    };
});

iCal.controller('indexManagerController', ['$scope','$window','$location','IManagerService', 
	function($scope,$window,$location,IManagerService)
{
	$scope.title = "New Indices";
	$scope.inactiveApprove= true;
	$scope.inactiveRunIndex= false;
	$scope.selectedRows = [];
	$scope.index = {};
	$scope.index.weightType = '';
	
	console.log('Host: :::;' + $location.host());
	console.log('PORT: :::;' + $location.port());
	
	
    $scope.load = function () 
    {
    	IManagerService.getAllNewIndex($scope.index).then(function (response) 
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
		enableHorizontalScrollbar: 1,
		enableCellEditOnFocus: false,
		showSelectionCheckbox: true,
		selectedItems:$scope.selectedRows,
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
    	},{
    		field: 'indexName',
    		displayName: 'Index Name',
    		enableCellEdit: false,
    		width: '120px'
        },{
           field: 'clientName',
           displayName: 'Client Name',
           enableCellEdit: false,
           width: '120px'
        },{
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
        },{
        	field: 'indexLiveDateStr',
        	displayName: 'Index Live Date',
        	enableCellEdit: false,
        	width: '150px'
        },{
	    	name: 'actions', 
        	width: '80px',
        	displayName: 'Actions', 
        	enableCellEdit: false,
        	cellTemplate: 
        		'<button id="deleteBtn" type="button" ng-click="view(row)" >View</button>'
		}]
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
    
    $scope.initialapprove = function(row) 
    {
        if (window.confirm("Are you sure you want to approve this Index?")) $scope.result = "Yes";  
        else $scope.result = "No";  
        if ($scope.result == "Yes")
        { 
        	IManagerService.updateIndexStatus(row.entity.indexTicker,'UI').then(function (response) 
	    	{
        		alert(response);
        		$scope.load();
	        });
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
        		IManagerService.updateIndexStatus($scope.selectedRows,'UI').then(function (response) 
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