iCal.controller('CAMyCtrl', ['$scope', function($scope)
{
    $scope.myData = [{name: "Moroni", age: 50, edit: false},
        {name: "Tiancum", age: 43, edit: false},
        {name: "Jacob", age: 27, edit: true},
        {name: "Nephi", age: 29, edit: true},
        {name: "Enos", age: 34, edit: false}];
    
    
$scope.gridOptions = 
	{
		data: 'myData',
		columnDefs: 
		[
			{
				field: 'name',
				displayName:'Name'
			},{
				field: 'age', 
				displayName:'Age', 
				cellTemplate:
					'<div class="ngCellText"><div ng-show="!row.entity.edit">{{COL_FIELD}}</div>' + 
                    '<div ng-show="row.entity.edit" class="ngCellText"><input type="text" ng-model="row.entity.age" style="height: 20px"></div></div>'
			},{
				field: 'age', 
				displayName:'Age', 
				cellTemplate:
					
                    '<div ng-show="row.entity.edit" class="ngCellText" style="height: 30px"><input type="text" ng-model="row.entity.age" style="height: 30px"></div>'
			},{
				field: 'age', 
				displayName:'Age', 
				cellTemplate:
					
                    '<div  class="ngCellText" style="height: 30px"><input type="text" ng-disabled="{{row.entity.edit}}" ng-model="row.entity.age" style="height: 30px"></div>'
			},{
		    	  name: 'actions', 
		    	  width: '120px', 
		    	  displayName: 'Actions', 
		    	  enableCellEdit: false,
		    	  cellTemplate: 
		    		 '<button id="inactiveBtn1" type="button" ng-click="Edit(row)">edit</button>'
		      }
		]
	};

	$scope.Edit = function(row) 
	{
		 if(row.entity.edit == false)
			 row.entity.edit = true;
		 else
			 row.entity.edit = false;
	};



}]);