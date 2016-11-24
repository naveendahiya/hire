(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('EeoVeteranType', EeoVeteranType);

    EeoVeteranType.$inject = ['$resource'];

    function EeoVeteranType ($resource) {
        var resourceUrl =  'api/eeo-veteran-types/:id';

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
