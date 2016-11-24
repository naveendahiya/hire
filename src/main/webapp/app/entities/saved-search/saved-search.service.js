(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('SavedSearch', SavedSearch);

    SavedSearch.$inject = ['$resource', 'DateUtils'];

    function SavedSearch ($resource, DateUtils) {
        var resourceUrl =  'api/saved-searches/:id';

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
