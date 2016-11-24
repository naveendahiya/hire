(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('Candidate', Candidate);

    Candidate.$inject = ['$resource', 'DateUtils'];

    function Candidate ($resource, DateUtils) {
        var resourceUrl =  'api/candidates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateAvailable = DateUtils.convertDateTimeFromServer(data.dateAvailable);
                        data.dateCreated = DateUtils.convertDateTimeFromServer(data.dateCreated);
                        data.dateModified = DateUtils.convertDateTimeFromServer(data.dateModified);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
