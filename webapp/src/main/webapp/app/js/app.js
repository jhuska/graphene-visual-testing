'use strict';

var PARTIALS = 'app/partials';

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
        templateUrl: PARTIALS + '/test-suite-list.html',
        controller: 'SuiteListCtrl'
      }).
      when('/suites/:suiteId', {
        templateUrl: PARTIALS + '/test-suite-runs-list.html',
        controller: 'SuiteRunsCtrl'
      }).
      when('/suites/:suiteId/runs/:runId', {
        templateUrl: PARTIALS + '/particular-run.html',
        controller: 'ParticularRunCtrl'
      }).
      otherwise({
        redirectTo: '/suites'
      });
  }]);