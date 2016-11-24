(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CalendarEventTypeDetailController', CalendarEventTypeDetailController);

    CalendarEventTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CalendarEventType'];

    function CalendarEventTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, CalendarEventType) {
        var vm = this;

        vm.calendarEventType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:calendarEventTypeUpdate', function(event, result) {
            vm.calendarEventType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
