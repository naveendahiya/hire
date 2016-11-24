(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CalendarEventController', CalendarEventController);

    CalendarEventController.$inject = ['$scope', '$state', 'CalendarEvent'];

    function CalendarEventController ($scope, $state, CalendarEvent) {
        var vm = this;

        vm.calendarEvents = [];

        loadAll();

        function loadAll() {
            CalendarEvent.query(function(result) {
                vm.calendarEvents = result;
            });
        }
    }
})();
