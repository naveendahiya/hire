(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CareerPortalQuestionnaireHistory', CareerPortalQuestionnaireHistory);

    CareerPortalQuestionnaireHistory.$inject = ['$resource', 'DateUtils'];

    function CareerPortalQuestionnaireHistory ($resource, DateUtils) {
        var resourceUrl =  'api/career-portal-questionnaire-histories/:id';

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
