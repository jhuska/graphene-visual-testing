'use strict';

/* Services */

var visualTestingServices = angular.module('visualTestingServices', ['ngResource']);

visualTestingServices.factory('Suite', ['$resource',
  function($resource){
    return $resource('rest/suites', {}, {
      query: {method:'GET', isArray: true}
    });
  }]);

visualTestingServices.factory('Run', ['$resource',
  function($resource){
    return $resource('rest/suites/:testSuiteID', {testSuiteID:'@testSuiteID'}, {
      query: {method:'GET', isArray: true}
    });
  }]);
