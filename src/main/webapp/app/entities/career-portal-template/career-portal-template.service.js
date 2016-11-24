(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CareerPortalTemplate', CareerPortalTemplate);

    CareerPortalTemplate.$inject = ['$resource'];

    function CareerPortalTemplate ($resource) {
        var resourceUrl =  'api/career-portal-templates/:id';

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
