(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CareerPortalQuestionnaireAnswer', CareerPortalQuestionnaireAnswer);

    CareerPortalQuestionnaireAnswer.$inject = ['$resource'];

    function CareerPortalQuestionnaireAnswer ($resource) {
        var resourceUrl =  'api/career-portal-questionnaire-answers/:id';

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
