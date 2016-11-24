(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('EmailHistory', EmailHistory);

    EmailHistory.$inject = ['$resource', 'DateUtils'];

    function EmailHistory ($resource, DateUtils) {
        var resourceUrl =  'api/email-histories/:id';

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
