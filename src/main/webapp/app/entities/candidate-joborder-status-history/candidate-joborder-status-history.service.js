(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CandidateJoborderStatusHistory', CandidateJoborderStatusHistory);

    CandidateJoborderStatusHistory.$inject = ['$resource', 'DateUtils'];

    function CandidateJoborderStatusHistory ($resource, DateUtils) {
        var resourceUrl =  'api/candidate-joborder-status-histories/:id';

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
