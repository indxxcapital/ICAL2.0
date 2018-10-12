
iCal.controller('TodaysCAController', ['$scope','$window','icalFactory', function($scope,$window,icalFactory)
{
	$scope.tab = 1;

    $scope.setTab = function(newTab){
      $scope.tab = newTab;
    };

    $scope.isSet = function(tabNum){
      return $scope.tab === tabNum;
    };
    $scope.cList = [];
    $scope.cList = icalFactory.currencyList; 
    console.log('TodaysCAController' + $scope.cList);
    $scope.divData = [];
    $scope.stockData = [];
    $scope.spinData = [];
    
    $scope.search = function() 
    {
    	  
    }
    
    $scope.divGridOptions = 
    { 	
		data: 'divData', 
		enableHorizontalScrollbar: 1,
		columnDefs: [
		{
			field: 'id',
			displayName: 'ISIN',
			enableCellEdit: false,
			width: '80px',
    	},
		{
			field: 'indexTicker',
			displayName: 'Dividend Type',
			enableCellEdit: false,
			width: '120px'
        }, {
        	field: 'indexName',
        	displayName: 'EDI Currency',
        	enableCellEdit: false,
        	width: '120px'
        }, {
        	field: 'clientName',
        	displayName: 'EDI Amount',
        	enableCellEdit: false,
        	width: '120px'
        },{
            field: 'indexWeightType',
            displayName: 'FDS Currency',
            enableCellEdit: false,
            width: '120px'
         }, {
        	field: 'status',
        	displayName: 'FDS Amount',
        	enableCellEdit: false,
        	width: '100px'
    	}, {
    		field: 'currency',
    		displayName: 'Manual Currency',
    		enableCellEdit: false,
    		width: '100px'
        }, {
        	field: 'indexLiveDateStr',
        	displayName: 'Manual Amount',
        	enableCellEdit: false,
        	width: '120px'
        },{
        	name: 'actions', 
        	width: '80', 
        	displayName: 'Actions', 
        	enableCellEdit: false,
        	cellTemplate: 
        		'<button id="okBtn" type="button" ng-click="CAUpdate(row)" >Ok</button>'
//        		'<button id="deleteBtn" type="button" ng-click="closingFile(row)" >Closing File</button>'+
//        		'<button id="deleteBtn" type="button" ng-click="openingFile(row)" >Opening File</button>'
    	}]
	};   

    

}]);