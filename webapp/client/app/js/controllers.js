'use strict';

/* Controllers */

var visualTestingControllers = angular.module('visualTestingControllers', []);

visualTestingControllers.controller('SuiteListCtrl', ['$scope', 'Suite',
  function($scope, Suite) {
    $scope.suites = Suite.query();
    $scope.orderProp = 'age';
  }]);

visualTestingControllers.controller('SuiteRunsCtrl', ['$scope', 'Run',
  function($scope, Run) {
    $scope.runs = Run.query();
  }]);
