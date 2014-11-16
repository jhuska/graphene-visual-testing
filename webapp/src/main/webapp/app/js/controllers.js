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
  	$scope.timestampToDate = timestampToDate;
  }]);

/* Help methods */

var timestampToDate = function(timestamp) {
	var date = new Date(timestamp);
	
	var hours = date.getHours();
	var minutes = "0" + date.getMinutes();
	var seconds = "0" + date.getSeconds();
	var time = hours + ':' + minutes.substr(minutes.length-2) + ':' + seconds.substr(seconds.length-2);

	var monthNames = [ "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December" ];

	return monthNames[date.getMonth()] + " " + date.getDate() + ", " + date.getFullYear() + ", " + time;
}