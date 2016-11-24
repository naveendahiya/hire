(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CompanyDepartment', CompanyDepartment);

    CompanyDepartment.$inject = ['$resource', 'DateUtils'];

    function CompanyDepartment ($resource, DateUtils) {
        var resourceUrl =  'api/company-departments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateCreated = DateUtils.convertDateTimeFromServer(data.dateCreated);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
