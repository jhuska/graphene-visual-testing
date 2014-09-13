'use strict';

/* Controllers */

var visualTestingControllers = angular.module('visualTestingControllers', []);

visualTestingControllers.controller('JobListCtrl', ['$scope', 'Job',
  function($scope, Job) {
    $scope.jobs = Job.query();
    $scope.orderProp = 'age';
  }]);

visualTestingControllers.controller('JobRunsCtrl', ['$scope', 'Run',
  function($scope, Run) {
    $scope.runs = Run.query();
  }]);
