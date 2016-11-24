(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ExtensionStatisticsDialogController', ExtensionStatisticsDialogController);

    ExtensionStatisticsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ExtensionStatistics'];

    function ExtensionStatisticsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ExtensionStatistics) {
        var vm = this;

        vm.extensionStatistics = entity;
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
            if (vm.extensionStatistics.id !== null) {
                ExtensionStatistics.update(vm.extensionStatistics, onSaveSuccess, onSaveError);
            } else {
                ExtensionStatistics.save(vm.extensionStatistics, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:extensionStatisticsUpdate', result);
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
