(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('SavedListEntry', SavedListEntry);

    SavedListEntry.$inject = ['$resource', 'DateUtils'];

    function SavedListEntry ($resource, DateUtils) {
        var resourceUrl =  'api/saved-list-entries/:id';

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
