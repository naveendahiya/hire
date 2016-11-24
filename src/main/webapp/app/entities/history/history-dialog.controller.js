(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('HistoryDialogController', HistoryDialogController);

    HistoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'History'];

    function HistoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, History) {
        var vm = this;

        vm.history = entity;
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
            if (vm.history.id !== null) {
                History.update(vm.history, onSaveSuccess, onSaveError);
            } else {
                History.save(vm.history, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:historyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.setDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
