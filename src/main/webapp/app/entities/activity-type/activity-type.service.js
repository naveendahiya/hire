(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('ActivityType', ActivityType);

    ActivityType.$inject = ['$resource'];

    function ActivityType ($resource) {
        var resourceUrl =  'api/activity-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
