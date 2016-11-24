(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CandidateJoborder', CandidateJoborder);

    CandidateJoborder.$inject = ['$resource', 'DateUtils'];

    function CandidateJoborder ($resource, DateUtils) {
        var resourceUrl =  'api/candidate-joborders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateSubmitted = DateUtils.convertDateTimeFromServer(data.dateSubmitted);
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
