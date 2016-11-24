(function() {
    'use strict';
    angular
        .module('hireApp')
        .factory('CalendarEventType', CalendarEventType);

    CalendarEventType.$inject = ['$resource'];

    function CalendarEventType ($resource) {
        var resourceUrl =  'api/calendar-event-types/:id';

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
