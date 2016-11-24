(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CandidateJoborderStatus', CandidateJoborderStatus);

    CandidateJoborderStatus.$inject = ['$resource'];

    function CandidateJoborderStatus ($resource) {
        var resourceUrl =  'api/candidate-joborder-statuses/:id';

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
