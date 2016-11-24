(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CalendarEventDetailController', CalendarEventDetailController);

    CalendarEventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CalendarEvent'];

    function CalendarEventDetailController($scope, $rootScope, $stateParams, previousState, entity, CalendarEvent) {
        var vm = this;

        vm.calendarEvent = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:calendarEventUpdate', function(event, result) {
            vm.calendarEvent = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
