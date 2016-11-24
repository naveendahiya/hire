(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('HttpLogTypes', HttpLogTypes);

    HttpLogTypes.$inject = ['$resource'];

    function HttpLogTypes ($resource) {
        var resourceUrl =  'api/http-log-types/:id';

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
