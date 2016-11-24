(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('EeoEthnicType', EeoEthnicType);

    EeoEthnicType.$inject = ['$resource'];

    function EeoEthnicType ($resource) {
        var resourceUrl =  'api/eeo-ethnic-types/:id';

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
