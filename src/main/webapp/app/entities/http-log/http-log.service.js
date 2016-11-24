(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('HttpLog', HttpLog);

    HttpLog.$inject = ['$resource', 'DateUtils'];

    function HttpLog ($resource, DateUtils) {
        var resourceUrl =  'api/http-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
