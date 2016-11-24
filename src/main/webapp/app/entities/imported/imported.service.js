(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('Imported', Imported);

    Imported.$inject = ['$resource', 'DateUtils'];

    function Imported ($resource, DateUtils) {
        var resourceUrl =  'api/importeds/:id';

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
