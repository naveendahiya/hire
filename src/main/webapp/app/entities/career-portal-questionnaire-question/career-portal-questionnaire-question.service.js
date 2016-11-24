(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CareerPortalQuestionnaireQuestion', CareerPortalQuestionnaireQuestion);

    CareerPortalQuestionnaireQuestion.$inject = ['$resource'];

    function CareerPortalQuestionnaireQuestion ($resource) {
        var resourceUrl =  'api/career-portal-questionnaire-questions/:id';

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
