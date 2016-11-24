(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CareerPortalTemplateSite', CareerPortalTemplateSite);

    CareerPortalTemplateSite.$inject = ['$resource'];

    function CareerPortalTemplateSite ($resource) {
        var resourceUrl =  'api/career-portal-template-sites/:id';

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
