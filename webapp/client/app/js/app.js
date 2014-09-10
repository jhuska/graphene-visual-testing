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
      when('/jobs', {
        templateUrl: 'partials/job-list.html',
        controller: 'JobListCtrl'
      }).
      when('/jobs/:jobId', {
        templateUrl: 'partials/job-runs.html',
        controller: 'JobRunsCtrl'
      }).
      when('/jobs/:jobId/runs/:runId', {
        templateUrl: 'partials/particular-run.html',
        controller: 'ParticularRunCtrl'
      }).
      otherwise({
        redirectTo: '/jobs'
      });
  }]);