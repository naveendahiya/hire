(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CalendarEvent', CalendarEvent);

    CalendarEvent.$inject = ['$resource', 'DateUtils'];

    function CalendarEvent ($resource, DateUtils) {
        var resourceUrl =  'api/calendar-events/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                        data.dateCreated = DateUtils.convertDateTimeFromServer(data.dateCreated);
                        data.dateModified = DateUtils.convertDateTimeFromServer(data.dateModified);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
