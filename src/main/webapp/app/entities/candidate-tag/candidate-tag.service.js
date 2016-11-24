(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CandidateTag', CandidateTag);

    CandidateTag.$inject = ['$resource'];

    function CandidateTag ($resource) {
        var resourceUrl =  'api/candidate-tags/:id';

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
