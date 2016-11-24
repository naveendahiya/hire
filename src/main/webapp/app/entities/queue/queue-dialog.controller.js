(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('QueueDialogController', QueueDialogController);

    QueueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Queue'];

    function QueueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Queue) {
        var vm = this;

        vm.queue = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.queue.id !== null) {
                Queue.update(vm.queue, onSaveSuccess, onSaveError);
            } else {
                Queue.save(vm.queue, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:queueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateCreated = false;
        vm.datePickerOpenStatus.dateTimeout = false;
        vm.datePickerOpenStatus.dateCompleted = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
