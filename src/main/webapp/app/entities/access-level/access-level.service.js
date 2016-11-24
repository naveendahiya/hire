(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('AccessLevel', AccessLevel);

    AccessLevel.$inject = ['$resource'];

    function AccessLevel ($resource) {
        var resourceUrl =  'api/access-levels/:id';

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
