iCal.controller('TodaysCAController', ['$scope','$window','icalFactory','CAManagerService','CommonService',
	function($scope,$window,icalFactory,CAManagerService,CommonService)
{
	console.log('Inside TodaysCAController');
	$scope.corporateAction = {};
	
	$scope.tab = 1;
	
    $scope.setTab = function(newTab)
    {
      $scope.tab = newTab;
    };

    $scope.isSet = function(tabNum)
    {
      return $scope.tab === tabNum;
    };
    
    $scope.cList = icalFactory.currencyList; 
   
    $scope.caData = [];
    $scope.divData = [];
    $scope.splitData = [];
    $scope.idChangeData = [];
        
    $scope.loadDividendCA = function () 
    {
    	$scope.corporateAction.eventCodeDiv = 'ORD_DIV\',\'SPL_DIV';
    	$scope.corporateAction.eventCodeSplit = 'STOCK_SPLT\',\'DVD_STOCK';
    	$scope.corporateAction.eventCodeIdChange = 'CHG_ID\',\'CHG_NAME\',\'CHG_TKR';
    	CAManagerService.getAllCA($scope.corporateAction).then(function (response) 
    	{
            $scope.divData = response.DIV;
            $scope.splitData = response.SPLIT;
            $scope.idChangeData = response.IDCHANGE;
//            if($scope.divData == undefined || $scope.divData.length == 0)
//            	alert('No Corporate Action found for the selected date');
        });
    }
    
    $scope.search = function() 
    {
    	$scope.divData = [];
    	$scope.splitData = [];
    	$scope.idChangeData = [];

    	$scope.loadDividendCA();
    }
    
    $scope.$on('ngGridEventEndCellEdit', function(evt)
    {
    });
    
    $scope.divGridOptions = 
    { 	
		data: 'divData', 
		enableHorizontalScrollbar: 1,
		enableCellSelection: true,
		columnDefs: [
		{
			field: 'id',
			displayName: 'Id',
			enableCellEdit: false,
			width: '0px',
			visible:false
    	},{
			field: 'ISIN',
			displayName: 'ISIN',
			enableCellEdit: false,
			width: '180px',
    	},{
			field: 'BBGTicker',
			displayName: 'BBG Ticker',
			enableCellEdit: false,
			width: '180px',
    	},{
			field: 'eventCode',
			displayName: 'Event Type',
			enableCellEdit: false,
			width: '150px'
        },{
             field: 'grossAmount',
             displayName: 'Gross Amount',
             editableCellTemplate: '<input type="number" ng-class="\'colt\' + col.index"  ng-model="COL_FIELD" style="height: 20px;width: 150px" />',
             enableCellEdit: true,
             width: '180px'
        },{
        	field: 'currency',
        	displayName: 'Currency',
        	editableCellTemplate: '<select ng-model="COL_FIELD" ng-class="\'colt\' + col.index" style="height: 20px;width: 150px" >'
    		+ '<option value="">-Select Currency-</option>'
    		+ '<option data-ng-repeat="currency in cList"  >{{currency.currencySymbol}}</option>'
    		+ '</select>',
    		enableCellEdit: true,
    		width: '180px'
      },{
    	  field: 'flag',
    	  name: 'Status', 
    	  width: '120px', 
    	  displayName: 'Actions', 
    	  enableCellEdit: false,
    	  cellTemplate: 
    		  '<button id="inactiveBtn" type="button" ng-click="Inactive(row)" >{{COL_FIELD == 0 ? "Active" : "In Active"}}</button>'//activity.seen == 0 ? 'Mark Read' : 'Mark Unread'
      },{
    	  field: 'flag',
    	  name: 'actions', 
    	  width: '120px', 
    	  displayName: 'Actions', 
    	  enableCellEdit: false,
    	  cellTemplate: 
    		  '<button id="inactiveBtn" type="button" ng-click="Inactive(row)" >{{COL_FIELD == 0 ? "Active" : "In Active"}}</button>'//activity.seen == 0 ? 'Mark Read' : 'Mark Unread'
      }]
	};   
        
    $scope.splitGridOptions = 
    { 	
		data: 'splitData', 
		enableCellEdit: false,
		enableCellEditOnFocus: true,
		cellEditableCondition: function($scope) {

		      // put your enable-edit code here, using values from $scope.row.entity and/or $scope.col.colDef as you desire
		      return $scope.row.entity.isActive; // in this example, we'll only allow active rows to be edited

		    },
		enableHorizontalScrollbar: 1,
		enableCellSelection: true,
		columnDefs: [
		{
			field: 'id',
			displayName: 'Id',
			enableCellEdit: false,
			width: '0px',
			visible:false
    	},{
			field: 'ISIN',
			displayName: 'ISIN',
			enableCellEdit: false,
			width: '180px',
    	},{
			field: 'BBGTicker',
			displayName: 'BBG Ticker',
			enableCellEdit: false,
			width: '180px',
    	},{
			field: 'eventCode',
			displayName: 'Event Type',
			enableCellEdit: false,
			width: '150px'
        },{
             field: 'ratio',
             displayName: 'Ratio',
             editableCellTemplate: '<input type="number" ng-class="\'colt\' + col.index"  ng-model="COL_FIELD" style="height: 20px;width: 120px" />',
             enableCellEdit: true,
             width: '180px'
        },{
        	field: 'flag',
        	name: 'isActive', 
        	width: '120px', 
        	displayName: 'Actions', 
        	enableCellEdit: false,
        	cellTemplate: 
        		'<button id="inactiveBtn" type="button" ng-click="Inactive(row)" >{{COL_FIELD == 0 ? "Active" : "In Active"}}</button>'//activity.seen == 0 ? 'Mark Read' : 'Mark Unread'
        		+'<button ng-click="row.entity.isActive = !row.entity.isActive" ng-model="row.entity.isActive">{{ row.entity.isActive ? "Unlocked" : "Locked" }}</button>'
        }]
	}; 
    
    $scope.idChangeGridOptions = 
    { 	
		data: 'idChangeData', 
		enableHorizontalScrollbar: 1,
		enableCellSelection: true,
		columnDefs: [
		{
			field: 'id',
			displayName: 'Id',
			enableCellEdit: false,
			width: '0px',
			visible:false
    	},{
			field: 'ISIN',
			displayName: 'ISIN',
			enableCellEdit: false,
			width: '180px',
    	},{
			field: 'BBGTicker',
			displayName: 'BBG Ticker',
			enableCellEdit: false,
			width: '180px',
    	},{
			field: 'eventCode',
			displayName: 'Event Type',
			enableCellEdit: false,
			width: '120px'
        },{
             field: 'oldValue',
             displayName: 'Old Value',
             editableCellTemplate: '<input type="text" ng-class="\'colt\' + col.index"  ng-model="COL_FIELD" style="height: 20px;width: 150px" />',
             enableCellEdit: true,
             width: '180px'
        },{
            field: 'newValue',
            displayName: 'New Value',
            editableCellTemplate: '<input type="text" ng-class="\'colt\' + col.index"  ng-model="COL_FIELD" style="height: 20px;width: 150px" />',
            enableCellEdit: true,
            width: '180px'
         },{
        	  field: 'flag',
        	  name: 'actions', 
        	  width: '120px', 
        	  displayName: 'Actions', 
        	  enableCellEdit: false,
        	  cellTemplate: 
        		  '<button id="inactiveBtn" type="button" ng-click="Inactive(row)" >{{COL_FIELD == 0 ? "Active" : "In Active"}}</button>'//activity.seen == 0 ? 'Mark Read' : 'Mark Unread'
    	}]
	}; 
    
    $scope.Inactive = function(row) 
    {
    	var actionState;
    	if(row.entity.flag == '0')
    		actionState = 'Active';
    	else
    		actionState = 'InActive';
    	
    	 if (window.confirm("Are you sure you want to make this Corporate Action " + actionState + "?")) $scope.result = "Yes";  
         else $scope.result = "No";  
         if ($scope.result == "Yes")
         { 
        	 if(row.entity.flag == '0')
        		 row.entity.flag = '1';
        	 else
        		 row.entity.flag = '0';
         }
    };
    
    $scope.updateCA = function() 
    {
    	$scope.caData = $scope.caData.concat($scope.splitData);
    	$scope.caData = $scope.caData.concat($scope.divData);
    	$scope.caData = $scope.caData.concat($scope.idChangeData);
    	CAManagerService.updateCorporateActions($scope.caData);
    };
 
    $scope.CACMDownload = function() 
    {
    	CAManagerService.CACMDownload($scope.corporateAction);
    };        
}]);