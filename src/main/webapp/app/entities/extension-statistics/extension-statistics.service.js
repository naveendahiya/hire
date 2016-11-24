(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('ExtensionStatistics', ExtensionStatistics);

    ExtensionStatistics.$inject = ['$resource', 'DateUtils'];

    function ExtensionStatistics ($resource, DateUtils) {
        var resourceUrl =  'api/extension-statistics/:id';

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
