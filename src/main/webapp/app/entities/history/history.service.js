(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('History', History);

    History.$inject = ['$resource', 'DateUtils'];

    function History ($resource, DateUtils) {
        var resourceUrl =  'api/histories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.setDate = DateUtils.convertDateTimeFromServer(data.setDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
