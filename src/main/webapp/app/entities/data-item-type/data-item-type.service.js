(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('DataItemType', DataItemType);

    DataItemType.$inject = ['$resource'];

    function DataItemType ($resource) {
        var resourceUrl =  'api/data-item-types/:id';

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
