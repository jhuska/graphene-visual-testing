'use strict';

/* Services */

var visualTestingServices = angular.module('visualTestingServices', ['ngResource']);

visualTestingServices.factory('Suite', ['$resource',
  function($resource){
    return $resource('testSuites/testSuites.json', {}, {
      query: {method:'GET', isArray:true}
    });
  }]);

visualTestingServices.factory('Run', ['$resource',
  function($resource){
    return $resource('testSuites/testSuiteRuns.json', {}, {
      query: {method:'GET', isArray:true}
    });
  }]);
