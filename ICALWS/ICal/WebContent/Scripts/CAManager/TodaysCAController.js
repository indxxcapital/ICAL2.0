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
			width: '180px'
    	},{
			field: 'BBGTicker',
			displayName: 'BBG Ticker',
			enableCellEdit: false,
			width: '150px'
    	},{
			field: 'eventCode',
			displayName: 'Event Type',
			enableCellEdit: false,
			width: '150px'
        },{
             field: 'grossAmount',
             displayName: 'Gross Amount',
             editableCellTemplate: '<input type="number"  value="0.0"  min="0.0" step=".01" ng-class="\'colt\' + col.index"  ng-model="COL_FIELD" style="height: 20px;width: 130px" />',
             enableCellEdit: true,
             width: '150px',
             visible:false
        },{    	  
  			field: 'grossAmount', 
  			displayName:'Gross Amount', 
  			cellTemplate:
  				'<div class="ngCellText" ><div ng-show="row.entity.allowEdit" class="ngCellText" ><input type="number"  value="0.0"  min="0.0" step=".01" ng-class="\'colt\' + col.index" ng-model="COL_FIELD" style="height: 20px;width: 130px;margin-top: 0px"></div>'+
  				'<div ng-show="!row.entity.allowEdit">{{COL_FIELD}}</div></div>',
			width: '170px'    	  
        },{
        	field: 'currency',
        	displayName: 'Currency',
        	editableCellTemplate: '<select ng-model="COL_FIELD" ng-class="\'colt\' + col.index" style="height: 20px;width: 150px" >'
    		+ '<option value="">-Select Currency-</option>'
    		+ '<option data-ng-repeat="currency in cList"  >{{currency.currencySymbol}}</option>'
    		+ '</select>',
    		enableCellEdit: true,
    		width: '170px',
    		visible:false
      },{
	      	field: 'currency',
	    	displayName: 'Currency',
	    	editableCellTemplate: '<div class="ngCellText" ><div ng-show="row.entity.allowEdit" class="ngCellText" >'+
	    		'<select ng-model="COL_FIELD"  style="height: 20px;width: 150px;margin-top: 0px" >'
				+ '<option value="">-Select Currency-</option>'
				+ '<option data-ng-repeat="currency in cList"  >{{currency.currencySymbol}}</option>'
				+ '</select></div>'
				+ '<div ng-show="!row.entity.allowEdit">{{COL_FIELD}}</div></div>',
			width: '190px',
			enableCellEdit: true
      },{
  			field: 'hasDifference',
  			name: 'status', 
  			width: '120px', 
  			displayName: 'Status', 
  			enableCellEdit: false,
  			cellTemplate: 
//  				'<div>{{COL_FIELD == 2 ? "P" : COL_FIELD == 1 ? "C" : ""}} <img width=\"50px\" ng-src=\"images/download.jpg\" lazy-src></div>'
  				'<div>{{COL_FIELD == 2 ? "P" : COL_FIELD == 1 ? "C" : ""}} <img width=\"20px\" ng-src=\"{{row.entity.iconName}}\" lazy-src></div>'
      },{
    	  	field: 'flag',
    	  	name: 'actions', 
    	  	width: '120px', 
    	  	displayName: 'Actions', 
    	  	enableCellEdit: false,
    	  	cellTemplate: 
    	  		'<button id="inactiveBtn1" type="button" ng-click="Edit(row)">edit</button>'+
    	  		'<button id="inactiveBtn" type="button" ng-click="Inactive(row)">{{COL_FIELD == 0 ? "In Active" : "Active"}}</button>'
    		 //activity.seen == 0 ? 'Mark Read' : 'Mark Unread'
      }],
      rowTemplate:'<div ng-style="rowStyle(row)" ng-class="{highlighted: row.getProperty(\'hasDifference\') ==0}"><div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell ">' +
      '<div class="ngVerticalBar" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div>' +
      '<div ng-cell></div>' +
      '</div></div>'	
	};   
        
    $scope.splitGridOptions = 
    { 	
		data: 'splitData', 
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
			width: '180px'
    	},{
			field: 'BBGTicker',
			displayName: 'BBG Ticker',
			enableCellEdit: false,
			width: '180px'
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
             width: '180px',
             visible:false
        },{    	  
  			field: 'ratio', 
  			displayName:'Ratio', 
  			cellTemplate:
  				'<div class="ngCellText" ><div ng-show="row.entity.allowEdit" class="ngCellText" ><input type="number"  value="0.0"  min="0.0" step=".01" ng-class="\'colt\' + col.index" ng-model="COL_FIELD" style="height: 20px;width: 130px;margin-top: 0px"></div>'+
  				'<div ng-show="!row.entity.allowEdit">{{COL_FIELD}}</div></div>',
			width: '170px'    	  
        },{
  			field: 'hasDifference',
  			name: 'status', 
  			width: '120px', 
  			displayName: 'Status', 
  			enableCellEdit: false,
  			cellTemplate: 
  				'<div>{{COL_FIELD == 2 ? "P" : COL_FIELD == 1 ? "C" : ""}} <img width=\"20px\" ng-src=\"{{row.entity.iconName}}\" lazy-src></div>'
      },{
        	field: 'flag',
        	name: 'isActive', 
        	width: '120px', 
        	displayName: 'Actions', 
        	enableCellEdit: false,
        	cellTemplate: 
        		'<button id="inactiveBtn1" type="button" ng-click="Edit(row)">edit</button>'+
        		'<button id="inactiveBtn" type="button" ng-click="Inactive(row)" >{{COL_FIELD == 0 ? "Active" : "In Active"}}</button>'
        }],
        rowTemplate:
        	'<div ng-style="rowStyle(row)" ng-class="{highlighted: row.getProperty(\'hasDifference\') ==0}"><div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell ">' +
        	'<div class="ngVerticalBar" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div>' +
        	'<div ng-cell></div>' +
        	'</div></div>'
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
             width: '180px',
             visible:false
        },{    	  
  			field: 'oldValue', 
  			displayName:'Old Value', 
  			cellTemplate:
  				'<div class="ngCellText" ><div ng-show="row.entity.allowEdit" class="ngCellText" ><input type="text"  ng-class="\'colt\' + col.index" ng-model="COL_FIELD" style="height: 20px;width: 130px;margin-top: 0px"></div>'+
  				'<div ng-show="!row.entity.allowEdit">{{COL_FIELD}}</div></div>',
			width: '170px'    	  
        },{
            field: 'newValue',
            displayName: 'New Value',
            editableCellTemplate: '<input type="text" ng-class="\'colt\' + col.index"  ng-model="COL_FIELD" style="height: 20px;width: 150px" />',
            enableCellEdit: true,
            width: '180px',
            visible:false
         },{    	  
  			field: 'newValue', 
  			displayName:'New Value', 
  			cellTemplate:
  				'<div class="ngCellText" ><div ng-show="row.entity.allowEdit" class="ngCellText" ><input type="text"  ng-class="\'colt\' + col.index" ng-model="COL_FIELD" style="height: 20px;width: 130px;margin-top: 0px"></div>'+
  				'<div ng-show="!row.entity.allowEdit">{{COL_FIELD}}</div></div>',
			width: '170px'    	  
        },{
  			field: 'hasDifference',
  			name: 'status', 
  			width: '120px', 
  			displayName: 'Status', 
  			enableCellEdit: false,
  			cellTemplate: 
  				'<div>{{COL_FIELD == 2 ? "P" : COL_FIELD == 1 ? "C" : ""}} <img width=\"20px\" ng-src=\"{{row.entity.iconName}}\" lazy-src></div>'
         },{
        	 field: 'flag',
        	 name: 'actions', 
        	 width: '120px', 
        	 displayName: 'Actions', 
        	 enableCellEdit: false,
        	 cellTemplate: 
        		 '<button id="inactiveBtn1" type="button" ng-click="Edit(row)">edit</button>'+
        		 '<button id="inactiveBtn" type="button" ng-click="Inactive(row)" >{{COL_FIELD == 0 ? "Active" : "In Active"}}</button>'//activity.seen == 0 ? 'Mark Read' : 'Mark Unread'
    	}],
        rowTemplate:'<div ng-style="rowStyle(row)" ng-class="{highlighted: row.getProperty(\'hasDifference\') ==0}"><div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell ">' +
        '<div class="ngVerticalBar" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div>' +
        '<div ng-cell></div>' +
        '</div></div>'
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
    
    $scope.Edit = function(row) 
    {
         { 
        	 if(row.entity.allowEdit == false)
        		 row.entity.allowEdit = true;
        	 else
        		 row.entity.allowEdit = false;
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