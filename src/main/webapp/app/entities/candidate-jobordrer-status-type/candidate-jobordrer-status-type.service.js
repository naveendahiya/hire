(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CandidateJobordrerStatusType', CandidateJobordrerStatusType);

    CandidateJobordrerStatusType.$inject = ['$resource'];

    function CandidateJobordrerStatusType ($resource) {
        var resourceUrl =  'api/candidate-jobordrer-status-types/:id';

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
