(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ExtraFieldSettingsDialogController', ExtraFieldSettingsDialogController);

    ExtraFieldSettingsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ExtraFieldSettings'];

    function ExtraFieldSettingsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ExtraFieldSettings) {
        var vm = this;

        vm.extraFieldSettings = entity;
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
            if (vm.extraFieldSettings.id !== null) {
                ExtraFieldSettings.update(vm.extraFieldSettings, onSaveSuccess, onSaveError);
            } else {
                ExtraFieldSettings.save(vm.extraFieldSettings, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:extraFieldSettingsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateCreated = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
