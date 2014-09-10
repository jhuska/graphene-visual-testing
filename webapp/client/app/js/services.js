'use strict';

/* Services */

var visualTestingServices = angular.module('visualTestingServices', ['ngResource']);

visualTestingServices.factory('Job', ['$resource',
  function($resource){
    return $resource('jobs/jobs.json', {}, {
      query: {method:'GET', isArray:true}
    });
  }]);
