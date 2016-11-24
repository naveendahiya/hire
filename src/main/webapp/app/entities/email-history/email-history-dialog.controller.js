(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EmailHistoryDialogController', EmailHistoryDialogController);

    EmailHistoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EmailHistory'];

    function EmailHistoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EmailHistory) {
        var vm = this;

        vm.emailHistory = entity;
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
            if (vm.emailHistory.id !== null) {
                EmailHistory.update(vm.emailHistory, onSaveSuccess, onSaveError);
            } else {
                EmailHistory.save(vm.emailHistory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:emailHistoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
