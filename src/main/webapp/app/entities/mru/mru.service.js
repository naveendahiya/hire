(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('Mru', Mru);

    Mru.$inject = ['$resource', 'DateUtils'];

    function Mru ($resource, DateUtils) {
        var resourceUrl =  'api/mrus/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateCreated = DateUtils.convertDateTimeFromServer(data.dateCreated);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
