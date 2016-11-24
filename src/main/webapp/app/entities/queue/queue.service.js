(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('Queue', Queue);

    Queue.$inject = ['$resource', 'DateUtils'];

    function Queue ($resource, DateUtils) {
        var resourceUrl =  'api/queues/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateCreated = DateUtils.convertDateTimeFromServer(data.dateCreated);
                        data.dateTimeout = DateUtils.convertDateTimeFromServer(data.dateTimeout);
                        data.dateCompleted = DateUtils.convertDateTimeFromServer(data.dateCompleted);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
