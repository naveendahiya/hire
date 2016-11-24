(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CareerPortalQuestionnaire', CareerPortalQuestionnaire);

    CareerPortalQuestionnaire.$inject = ['$resource'];

    function CareerPortalQuestionnaire ($resource) {
        var resourceUrl =  'api/career-portal-questionnaires/:id';

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
