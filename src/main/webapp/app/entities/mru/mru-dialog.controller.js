(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('MruDialogController', MruDialogController);

    MruDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Mru'];

    function MruDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Mru) {
        var vm = this;

        vm.mru = entity;
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
            if (vm.mru.id !== null) {
                Mru.update(vm.mru, onSaveSuccess, onSaveError);
            } else {
                Mru.save(vm.mru, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:mruUpdate', result);
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
