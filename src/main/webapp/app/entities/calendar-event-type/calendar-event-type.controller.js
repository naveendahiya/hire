(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CalendarEventTypeController', CalendarEventTypeController);

    CalendarEventTypeController.$inject = ['$scope', '$state', 'CalendarEventType'];

    function CalendarEventTypeController ($scope, $state, CalendarEventType) {
        var vm = this;

        vm.calendarEventTypes = [];

        loadAll();

        function loadAll() {
            CalendarEventType.query(function(result) {
                vm.calendarEventTypes = result;
            });
        }
    }
})();
