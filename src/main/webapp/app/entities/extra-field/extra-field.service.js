(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('ExtraField', ExtraField);

    ExtraField.$inject = ['$resource'];

    function ExtraField ($resource) {
        var resourceUrl =  'api/extra-fields/:id';

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
