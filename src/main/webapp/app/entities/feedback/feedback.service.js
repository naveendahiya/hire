(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('Feedback', Feedback);

    Feedback.$inject = ['$resource', 'DateUtils'];

    function Feedback ($resource, DateUtils) {
        var resourceUrl =  'api/feedbacks/:id';

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
