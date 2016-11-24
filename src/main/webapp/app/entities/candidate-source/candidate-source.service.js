(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CandidateSource', CandidateSource);

    CandidateSource.$inject = ['$resource', 'DateUtils'];

    function CandidateSource ($resource, DateUtils) {
        var resourceUrl =  'api/candidate-sources/:id';

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
