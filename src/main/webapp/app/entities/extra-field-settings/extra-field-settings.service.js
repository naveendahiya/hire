(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('ExtraFieldSettings', ExtraFieldSettings);

    ExtraFieldSettings.$inject = ['$resource', 'DateUtils'];

    function ExtraFieldSettings ($resource, DateUtils) {
        var resourceUrl =  'api/extra-field-settings/:id';

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
