iCal.controller('upcomingIndexController', ['$scope','$window','IManagerService','$rootScope', 
	function($scope,$window,IManagerService,$rootScope)
{
	$scope.title = "Upcoming Indices";
	$scope.inactiveApprove= false;
	$scope.inactiveRunIndex= true;
	$scope.selectedRows = [];
    $scope.index = {};
    $scope.index.weightType = '';
    
    $scope.load = function () 
    {
    	IManagerService.getAllUpcomingIndex($scope.index).then(function (response) 
    	{
            $scope.Data = response;
            console.log($scope.Data);
        });
    }
    
    $rootScope.$on("reLoad", function(){
        $scope.load();
     });
    
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
        }, {
        	field: 'indexLiveDateStr',
        	displayName: 'Index Live Date',
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
        	width: '80',
        	displayName: 'Actions', 
        	enableCellEdit: false,
        	cellTemplate: 
        		'<button id="deleteBtn" type="button" ng-click="view(row)" >View</button>'
		}]
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
        		IManagerService.updateIndexStatus($scope.selectedRows,'LI').then(function (response) 
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
    
    $scope.closingFile = function(row) 
    {
		 if (window.confirm("Are you sure you want to generate closing file for this Index?")) $scope.result = "Yes";  
		 else $scope.result = "No";  
		 if ($scope.result == "Yes")
		 { 
			 IManagerService.getIndexClosingFile(row.entity);
		 }
	};
	
	 $scope.openingFile = function(row) 
    {
		 if (window.confirm("Are you sure you want to generate opening file for this Index?")) $scope.result = "Yes";  
		 else $scope.result = "No";  
		 if ($scope.result == "Yes")
		 { 
			 IManagerService.getIndexOpeningFile(row.entity);
		 }
	};
	
	$scope.view = function(row) 
	{
		var $popup = $window.open("securityPopUp.html");
		$popup.indexTicker =row.entity.indexTicker;
    }	
	
	$scope.openRunIndex = function(row) 
	{
		if($scope.selectedRows.length == 0)
      		alert("Please select indices to run");
		else
		{
			var $popup = $window.open("runIndex.html", "popup", "width=500,height=200,left=400,top=150");
			$popup.selectedRows =$scope.selectedRows;
		}
    }	
}]);