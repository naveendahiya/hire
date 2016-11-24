(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ExtraFieldDialogController', ExtraFieldDialogController);

    ExtraFieldDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ExtraField'];

    function ExtraFieldDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ExtraField) {
        var vm = this;

        vm.extraField = entity;
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
            if (vm.extraField.id !== null) {
                ExtraField.update(vm.extraField, onSaveSuccess, onSaveError);
            } else {
                ExtraField.save(vm.extraField, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:extraFieldUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
