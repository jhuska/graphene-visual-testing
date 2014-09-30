'use strict';

/* Controllers */

var visualTestingControllers = angular.module('visualTestingControllers', []);

visualTestingControllers.controller('SuiteListCtrl', ['$scope', 'Suite',
  function($scope, Suite) {
    $scope.suites = Suite.query();
  }]);

visualTestingControllers.controller('SuiteRunsCtrl', ['$scope', '$routeParams', 'Run',
  function($scope, $routeParams, Run) {
  	$scope.runs = Run.query({testSuiteID: $routeParams.testSuiteID});
  }]);
