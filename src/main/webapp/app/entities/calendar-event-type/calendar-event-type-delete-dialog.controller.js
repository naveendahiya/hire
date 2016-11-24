(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CalendarEventTypeDeleteController',CalendarEventTypeDeleteController);

    CalendarEventTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CalendarEventType'];

    function CalendarEventTypeDeleteController($uibModalInstance, entity, CalendarEventType) {
        var vm = this;

        vm.calendarEventType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CalendarEventType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
