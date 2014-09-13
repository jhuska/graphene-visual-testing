'use strict';

/* App Module */

var visualTestingApp = angular.module('visualTestingApp', [
  'ngRoute',
  'visualTestingControllers',
  'visualTestingServices'
]);

visualTestingApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/suites', {
        templateUrl: 'partials/test-suite-list.html',
        controller: 'SuiteListCtrl'
      }).
      when('/suites/:suiteId', {
        templateUrl: 'partials/test-suite-runs-list.html',
        controller: 'SuiteRunsCtrl'
      }).
      when('/suites/:suiteId/runs/:runId', {
        templateUrl: 'partials/particular-run.html',
        controller: 'ParticularRunCtrl'
      }).
      otherwise({
        redirectTo: '/suites'
      });
  }]);