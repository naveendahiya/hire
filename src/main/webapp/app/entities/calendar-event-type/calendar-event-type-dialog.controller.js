(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CalendarEventTypeDialogController', CalendarEventTypeDialogController);

    CalendarEventTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CalendarEventType'];

    function CalendarEventTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CalendarEventType) {
        var vm = this;

        vm.calendarEventType = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.calendarEventType.id !== null) {
                CalendarEventType.update(vm.calendarEventType, onSaveSuccess, onSaveError);
            } else {
                CalendarEventType.save(vm.calendarEventType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:calendarEventTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
