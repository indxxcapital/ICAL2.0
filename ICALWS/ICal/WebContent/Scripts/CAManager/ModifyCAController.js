iCal.controller('CAMyCtrl', ['$scope', function($scope)
{
  $scope.myData = [{name: "Moroni", dob: '1985-01-01'},
                 {name: "Tiancum", dob: '1956-11-21'},
                 {name: "Jacob", dob: '1980-03-08'},
                 {name: "Nephi", dob: '1974-08-08'},
                 {name: "Enos", dob: '1991-07-17'}];
                 
  $scope.gridOptions = {
    data: 'myData',
    rowTemplate: '<div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}" style="overflow: visible"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell></div></div>',
    columnDefs: [     
      {
        field: 'dob',
        cellFilter: 'date',
        editableCellTemplate: '<input ng-class="\'colt\' + col.index" datepicker-popup is-open="true" ng-model="COL_FIELD" style="height: 20px;width: 120px" />',
        enableCellEdit: true,
        width: '180px'
        //height: '12px'
      },
      {
          field: 'name',
          
          editableCellTemplate: '<input ng-class="\'colt\' + col.index"  ng-model="COL_FIELD" style="height: 20px;width: 120px" />',
          enableCellEdit: true,
          width: '180px'
          //height: '12px'
       },
       {
           field: 'name',
           
           editableCellTemplate: '<select ng-model="COL_FIELD" ng-class="\'colt\' + col.index" style="height: 20px;width: 120px" >'
			+ '<option value="Jacob">Jacob</option>'
			+ '<option value="Moroni">Moroni</option>'
			+ '<option value="Nephi">Nephi</option>'
			+ '<option value="Enos">Enos</option>'
			+ '</select>',
           enableCellEdit: true,
           width: '180px'
           //height: '12px'
        }
    ]
  }
}]);